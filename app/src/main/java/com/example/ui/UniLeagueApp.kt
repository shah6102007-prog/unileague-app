package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserRole
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

@Composable
fun UniLeagueApp(
  viewModel: UniLeagueViewModel = viewModel()
) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val studentTab by viewModel.studentTab.collectAsState()
  val isDarkMode by viewModel.isDarkMode.collectAsState()
  val language by viewModel.language.collectAsState()
  val isArabic = language == "ar"

  val currentUser by viewModel.currentUser.collectAsState()
  val skills by viewModel.skills.collectAsState()
  val challenges by viewModel.challenges.collectAsState()
  val attempts by viewModel.attempts.collectAsState()
  val achievements by viewModel.achievements.collectAsState()
  val competitions by viewModel.competitions.collectAsState()
  val teams by viewModel.teams.collectAsState()
  val departmentRankings by viewModel.departmentRankings.collectAsState()
  val notifications by viewModel.notifications.collectAsState()
  val userAccounts by viewModel.userAccounts.collectAsState()
  val aiRecommendation by viewModel.aiRecommendation.collectAsState()

  val showOnboarding by viewModel.showOnboarding.collectAsState()
  val showNotifications by viewModel.showNotifications.collectAsState()
  val showSettings by viewModel.showSettings.collectAsState()
  val showEditProfile by viewModel.showEditProfile.collectAsState()
  val showAddSkill by viewModel.showAddSkill.collectAsState()
  val showCreateTeam by viewModel.showCreateTeam.collectAsState()
  val showCreateChallenge by viewModel.showCreateChallenge.collectAsState()
  val showAddUser by viewModel.showAddUser.collectAsState()

  val challengeSession by viewModel.challengeSession.collectAsState()
  val lastAttempt by viewModel.lastAttempt.collectAsState()

  val leaderboardScope by viewModel.leaderboardScope.collectAsState()
  val leaderboardSearchQuery by viewModel.leaderboardSearchQuery.collectAsState()
  val challengeSkillFilter by viewModel.challengeSkillFilter.collectAsState()
  val challengeDifficultyFilter by viewModel.challengeDifficultyFilter.collectAsState()

  val unreadNotificationsCount = notifications.count { !it.isRead }
  val activeRole = currentUser?.role ?: UserRole.STUDENT

  CompositionLocalProvider(
    LocalLayoutDirection provides if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr
  ) {
    MyApplicationTheme(darkTheme = isDarkMode) {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
      ) {
        when (currentScreen) {
          AppScreen.SPLASH -> {
            SplashScreen(
              onStartClick = { viewModel.navigateTo(AppScreen.AUTH) },
              onDemoStudentClick = { viewModel.loginAs(UserRole.STUDENT) }
            )
          }

          AppScreen.AUTH -> {
            AuthScreen(
              onLoginSuccess = { role -> viewModel.loginAs(role) },
              onRegisterSuccess = { name, email, uni, coll, dept, major, level, role ->
                viewModel.registerUser(name, email, uni, coll, dept, major, level, role)
              }
            )
          }

          AppScreen.CHALLENGE_ACTIVE -> {
            if (challengeSession != null) {
              ChallengeActiveScreen(
                session = challengeSession!!,
                onSelectOption = { optIndex -> viewModel.selectChallengeOption(optIndex) },
                onNext = { viewModel.nextQuestion() },
                onPrev = { viewModel.prevQuestion() },
                onSubmit = { viewModel.submitCurrentChallenge() },
                onCancel = { viewModel.cancelChallenge() }
              )
            } else {
              viewModel.navigateTo(AppScreen.MAIN_STUDENT)
            }
          }

          AppScreen.CHALLENGE_RESULT -> {
            if (lastAttempt != null && currentUser != null) {
              ChallengeResultScreen(
                attempt = lastAttempt!!,
                currentUser = currentUser!!,
                onBackToDashboard = {
                  viewModel.setStudentTab(StudentTab.DASHBOARD)
                  viewModel.navigateTo(AppScreen.MAIN_STUDENT)
                },
                onViewLeaderboard = {
                  viewModel.setStudentTab(StudentTab.LEADERBOARD)
                  viewModel.navigateTo(AppScreen.MAIN_STUDENT)
                }
              )
            } else {
              viewModel.navigateTo(AppScreen.MAIN_STUDENT)
            }
          }

          AppScreen.MAIN_STUDENT -> {
            val user = currentUser ?: viewModel.repository.studentDemoUser
            Scaffold(
              topBar = {
                UniLeagueTopBar(
                  currentRole = UserRole.STUDENT,
                  unreadNotificationsCount = unreadNotificationsCount,
                  isDarkMode = isDarkMode,
                  isArabic = isArabic,
                  onNotificationsClick = { viewModel.toggleNotifications(true) },
                  onSettingsClick = { viewModel.toggleSettings(true) },
                  onSwitchRole = { newRole ->
                    viewModel.loginAs(newRole)
                  }
                )
              },
              bottomBar = {
                StudentBottomNavigationBar(
                  currentTab = studentTab,
                  onTabSelected = { viewModel.setStudentTab(it) },
                  isArabic = isArabic
                )
              }
            ) { padding ->
              Box(modifier = Modifier.padding(padding)) {
                when (studentTab) {
                  StudentTab.DASHBOARD -> {
                    StudentDashboardScreen(
                      user = user,
                      challenges = challenges,
                      competitions = competitions,
                      achievements = achievements,
                      aiRecommendation = aiRecommendation,
                      onStartChallenge = { challenge -> viewModel.startChallenge(challenge) },
                      onNavigateTab = { viewModel.setStudentTab(it) }
                    )
                  }
                  StudentTab.CHALLENGES -> {
                    ChallengesScreen(
                      challenges = challenges,
                      selectedSkillFilter = challengeSkillFilter,
                      selectedDifficultyFilter = challengeDifficultyFilter,
                      onFilterChange = { skill, diff -> viewModel.setChallengeFilters(skill, diff) },
                      onStartChallenge = { challenge -> viewModel.startChallenge(challenge) }
                    )
                  }
                  StudentTab.COMPETITIONS -> {
                    CompetitionsAndTeamsScreen(
                      competitions = competitions,
                      teams = teams,
                      onJoinCompetition = { compId -> viewModel.repository.joinCompetition(compId) },
                      onJoinTeam = { teamId -> viewModel.repository.joinTeam(teamId) },
                      onCreateTeamClick = { viewModel.toggleCreateTeam(true) }
                    )
                  }
                  StudentTab.LEADERBOARD -> {
                    LeaderboardScreen(
                      currentUser = user,
                      departmentRankings = departmentRankings,
                      selectedScope = leaderboardScope,
                      searchQuery = leaderboardSearchQuery,
                      onScopeChange = { viewModel.setLeaderboardScope(it) },
                      onSearchChange = { viewModel.setLeaderboardSearch(it) }
                    )
                  }
                  StudentTab.PROFILE -> {
                    ProfileAndSkillsScreen(
                      user = user,
                      skills = skills,
                      achievements = achievements,
                      attempts = attempts,
                      onEditProfileClick = { viewModel.toggleEditProfile(true) },
                      onAddSkillClick = { viewModel.toggleAddSkill(true) }
                    )
                  }
                }
              }
            }
          }

          AppScreen.MAIN_TEACHER -> {
            val user = currentUser ?: viewModel.repository.teacherDemoUser
            Scaffold(
              topBar = {
                UniLeagueTopBar(
                  currentRole = UserRole.TEACHER,
                  unreadNotificationsCount = unreadNotificationsCount,
                  isDarkMode = isDarkMode,
                  isArabic = isArabic,
                  onNotificationsClick = { viewModel.toggleNotifications(true) },
                  onSettingsClick = { viewModel.toggleSettings(true) },
                  onSwitchRole = { newRole ->
                    viewModel.loginAs(newRole)
                  }
                )
              }
            ) { padding ->
              TeacherDashboardScreen(
                user = user,
                challenges = challenges,
                onCreateChallengeClick = { viewModel.toggleCreateChallenge(true) },
                onDeleteChallenge = { id -> viewModel.repository.deleteChallenge(id) },
                modifier = Modifier.padding(padding)
              )
            }
          }

          AppScreen.MAIN_ADMIN -> {
            val user = currentUser ?: viewModel.repository.adminDemoUser
            Scaffold(
              topBar = {
                UniLeagueTopBar(
                  currentRole = UserRole.ADMIN,
                  unreadNotificationsCount = unreadNotificationsCount,
                  isDarkMode = isDarkMode,
                  isArabic = isArabic,
                  onNotificationsClick = { viewModel.toggleNotifications(true) },
                  onSettingsClick = { viewModel.toggleSettings(true) },
                  onSwitchRole = { newRole ->
                    viewModel.loginAs(newRole)
                  }
                )
              }
            ) { padding ->
              AdminDashboardScreen(
                user = user,
                userAccounts = userAccounts,
                onUpdateUserStatus = { id, st -> viewModel.repository.adminUpdateUserStatus(id, st) },
                onChangeUserRole = { id, rl -> viewModel.repository.adminChangeUserRole(id, rl) },
                onAddUserClick = { viewModel.toggleAddUser(true) },
                modifier = Modifier.padding(padding)
              )
            }
          }
        }

        // Global Modals & Dialogs
        if (showOnboarding) {
          OnboardingDialog(
            onDismiss = { viewModel.dismissOnboarding() }
          )
        }

        if (showNotifications) {
          NotificationsSheet(
            notifications = notifications,
            onDismiss = { viewModel.toggleNotifications(false) },
            onClearAll = { viewModel.repository.clearNotifications() }
          )
        }

        if (showSettings) {
          SettingsDialog(
            isDarkMode = isDarkMode,
            language = language,
            onToggleDarkMode = { viewModel.toggleDarkMode() },
            onToggleLanguage = { viewModel.toggleLanguage() },
            onLogout = { viewModel.logout() },
            onDismiss = { viewModel.toggleSettings(false) }
          )
        }

        if (showEditProfile && currentUser != null) {
          EditProfileDialog(
            user = currentUser!!,
            onDismiss = { viewModel.toggleEditProfile(false) },
            onSave = { name, uni, coll, dept, major, level ->
              viewModel.repository.updateCurrentUserProfile(name, uni, coll, dept, major, level)
            }
          )
        }

        if (showAddSkill) {
          AddSkillDialog(
            onDismiss = { viewModel.toggleAddSkill(false) },
            onAddSkill = { nameAr, nameEn, cat ->
              viewModel.repository.addCustomSkill(nameAr, nameEn, cat)
            }
          )
        }

        if (showCreateTeam && currentUser != null) {
          CreateTeamDialog(
            userDepartment = currentUser!!.department,
            onDismiss = { viewModel.toggleCreateTeam(false) },
            onCreateTeam = { name, dept ->
              viewModel.repository.createTeam(name, dept)
            }
          )
        }

        if (showCreateChallenge) {
          CreateChallengeDialog(
            onDismiss = { viewModel.toggleCreateChallenge(false) },
            onCreateChallenge = { title, desc, skill, major, diff, dur, pts, questions ->
              viewModel.repository.createChallenge(title, desc, skill, major, diff, dur, pts, questions)
            }
          )
        }

        if (showAddUser) {
          AddUserDialog(
            onDismiss = { viewModel.toggleAddUser(false) },
            onAddUser = { name, email, role, dept ->
              viewModel.repository.adminAddUser(name, email, role, dept)
            }
          )
        }
      }
    }
  }
}
