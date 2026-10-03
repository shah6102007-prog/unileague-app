package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.UniLeagueRepository
import com.example.data.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
  SPLASH,
  AUTH,
  MAIN_STUDENT,
  MAIN_TEACHER,
  MAIN_ADMIN,
  CHALLENGE_ACTIVE,
  CHALLENGE_RESULT
}

enum class StudentTab(val titleAr: String, val titleEn: String) {
  DASHBOARD("الرئيسية", "Home"),
  CHALLENGES("التحديات", "Challenges"),
  COMPETITIONS("البطولات والفرق", "Tournaments"),
  LEADERBOARD("الترتيب", "Leaderboard"),
  PROFILE("الملف والمهارات", "Profile")
}

enum class LeaderboardScope(val labelAr: String, val labelEn: String) {
  UNIVERSITY("الجامعة", "University"),
  COLLEGE("الكلية", "College"),
  DEPARTMENT("القسم", "Department"),
  SKILL("حسب المهارة", "By Skill"),
  DEPARTMENTS_LEAGUE("منافسة الأقسام", "Dept League")
}

data class ChallengeSession(
  val challenge: Challenge,
  val questions: List<Question>,
  val currentQuestionIndex: Int = 0,
  val selectedAnswers: Map<Int, Int> = emptyMap(), // questionIndex -> selectedOptionIndex
  val remainingSeconds: Int = 0,
  val totalSeconds: Int = 0,
  val isFinished: Boolean = false
)

class UniLeagueViewModel(
  val repository: UniLeagueRepository = UniLeagueRepository()
) : ViewModel() {

  // Current Navigation Screen
  private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.SPLASH)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  // Selected Bottom Tab for Student
  private val _studentTab = MutableStateFlow(StudentTab.DASHBOARD)
  val studentTab: StateFlow<StudentTab> = _studentTab.asStateFlow()

  // App Theme & Localization
  private val _isDarkMode = MutableStateFlow(false)
  val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

  private val _language = MutableStateFlow("ar") // "ar" or "en"
  val language: StateFlow<String> = _language.asStateFlow()

  // Onboarding
  private val _showOnboarding = MutableStateFlow(false)
  val showOnboarding: StateFlow<Boolean> = _showOnboarding.asStateFlow()

  // Dialog & Overlay visibility
  private val _showNotifications = MutableStateFlow(false)
  val showNotifications: StateFlow<Boolean> = _showNotifications.asStateFlow()

  private val _showSettings = MutableStateFlow(false)
  val showSettings: StateFlow<Boolean> = _showSettings.asStateFlow()

  private val _showEditProfile = MutableStateFlow(false)
  val showEditProfile: StateFlow<Boolean> = _showEditProfile.asStateFlow()

  private val _showAddSkill = MutableStateFlow(false)
  val showAddSkill: StateFlow<Boolean> = _showAddSkill.asStateFlow()

  private val _showCreateTeam = MutableStateFlow(false)
  val showCreateTeam: StateFlow<Boolean> = _showCreateTeam.asStateFlow()

  private val _showCreateChallenge = MutableStateFlow(false)
  val showCreateChallenge: StateFlow<Boolean> = _showCreateChallenge.asStateFlow()

  private val _showAddUser = MutableStateFlow(false)
  val showAddUser: StateFlow<Boolean> = _showAddUser.asStateFlow()

  // Active Challenge Session & Anti-Cheat Timer
  private val _challengeSession = MutableStateFlow<ChallengeSession?>(null)
  val challengeSession: StateFlow<ChallengeSession?> = _challengeSession.asStateFlow()

  private val _lastAttempt = MutableStateFlow<ChallengeAttempt?>(null)
  val lastAttempt: StateFlow<ChallengeAttempt?> = _lastAttempt.asStateFlow()

  private var timerJob: Job? = null

  // Leaderboard filters
  private val _leaderboardScope = MutableStateFlow(LeaderboardScope.COLLEGE)
  val leaderboardScope: StateFlow<LeaderboardScope> = _leaderboardScope.asStateFlow()

  private val _leaderboardSearchQuery = MutableStateFlow("")
  val leaderboardSearchQuery: StateFlow<String> = _leaderboardSearchQuery.asStateFlow()

  // Challenges filters
  private val _challengeSkillFilter = MutableStateFlow("الكل")
  val challengeSkillFilter: StateFlow<String> = _challengeSkillFilter.asStateFlow()

  private val _challengeDifficultyFilter = MutableStateFlow("الكل")
  val challengeDifficultyFilter: StateFlow<String> = _challengeDifficultyFilter.asStateFlow()

  // Repository Delegated Flows
  val currentUser = repository.currentUser
  val skills = repository.skills
  val challenges = repository.challenges
  val attempts = repository.attempts
  val achievements = repository.achievements
  val competitions = repository.competitions
  val teams = repository.teams
  val departmentRankings = repository.departmentRankings
  val notifications = repository.notifications
  val userAccounts = repository.userAccounts
  val aiRecommendation = repository.aiRecommendation

  fun navigateTo(screen: AppScreen) {
    _currentScreen.value = screen
  }

  fun setStudentTab(tab: StudentTab) {
    _studentTab.value = tab
  }

  fun toggleDarkMode() {
    _isDarkMode.update { !it }
  }

  fun toggleLanguage() {
    _language.update { if (it == "ar") "en" else "ar" }
  }

  fun dismissOnboarding() {
    _showOnboarding.value = false
  }

  fun toggleNotifications(show: Boolean) {
    _showNotifications.value = show
    if (show) repository.markAllNotificationsRead()
  }

  fun toggleSettings(show: Boolean) {
    _showSettings.value = show
  }

  fun toggleEditProfile(show: Boolean) {
    _showEditProfile.value = show
  }

  fun toggleAddSkill(show: Boolean) {
    _showAddSkill.value = show
  }

  fun toggleCreateTeam(show: Boolean) {
    _showCreateTeam.value = show
  }

  fun toggleCreateChallenge(show: Boolean) {
    _showCreateChallenge.value = show
  }

  fun toggleAddUser(show: Boolean) {
    _showAddUser.value = show
  }

  fun setLeaderboardScope(scope: LeaderboardScope) {
    _leaderboardScope.value = scope
  }

  fun setLeaderboardSearch(query: String) {
    _leaderboardSearchQuery.value = query
  }

  fun setChallengeFilters(skill: String, difficulty: String) {
    _challengeSkillFilter.value = skill
    _challengeDifficultyFilter.value = difficulty
  }

  // Auth Operations
  fun loginAs(role: UserRole) {
    repository.login(role)
    when (role) {
      UserRole.STUDENT -> {
        _showOnboarding.value = true
        _currentScreen.value = AppScreen.MAIN_STUDENT
      }
      UserRole.TEACHER -> _currentScreen.value = AppScreen.MAIN_TEACHER
      UserRole.ADMIN -> _currentScreen.value = AppScreen.MAIN_ADMIN
    }
  }

  fun registerUser(
    name: String,
    email: String,
    university: String,
    college: String,
    department: String,
    major: String,
    academicLevel: String,
    role: UserRole
  ) {
    repository.registerUser(name, email, university, college, department, major, academicLevel, role)
    loginAs(role)
  }

  fun logout() {
    repository.logout()
    _currentScreen.value = AppScreen.AUTH
  }

  // Challenge Flow
  fun startChallenge(challenge: Challenge) {
    // Anti-cheat: Shuffle questions and options
    val randomizedQuestions = challenge.questions.shuffled().map { q ->
      val shuffledOptions = q.options.shuffled()
      val correctOptionText = q.options[q.correctOptionIndex]
      val newCorrectIndex = shuffledOptions.indexOf(correctOptionText)
      q.copy(options = shuffledOptions, correctOptionIndex = newCorrectIndex)
    }

    val totalSeconds = challenge.durationMinutes * 60
    _challengeSession.value = ChallengeSession(
      challenge = challenge,
      questions = randomizedQuestions,
      currentQuestionIndex = 0,
      selectedAnswers = emptyMap(),
      remainingSeconds = totalSeconds,
      totalSeconds = totalSeconds,
      isFinished = false
    )

    _currentScreen.value = AppScreen.CHALLENGE_ACTIVE

    // Start Live Countdown Timer
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      while (true) {
        delay(1000)
        val current = _challengeSession.value ?: break
        if (current.remainingSeconds <= 1) {
          // Time expired -> auto-submit challenge!
          submitCurrentChallenge()
          break
        } else {
          _challengeSession.value = current.copy(remainingSeconds = current.remainingSeconds - 1)
        }
      }
    }
  }

  fun selectChallengeOption(optionIndex: Int) {
    val current = _challengeSession.value ?: return
    val newMap = current.selectedAnswers.toMutableMap()
    newMap[current.currentQuestionIndex] = optionIndex
    _challengeSession.value = current.copy(selectedAnswers = newMap)
  }

  fun nextQuestion() {
    val current = _challengeSession.value ?: return
    if (current.currentQuestionIndex < current.questions.size - 1) {
      _challengeSession.value = current.copy(currentQuestionIndex = current.currentQuestionIndex + 1)
    }
  }

  fun prevQuestion() {
    val current = _challengeSession.value ?: return
    if (current.currentQuestionIndex > 0) {
      _challengeSession.value = current.copy(currentQuestionIndex = current.currentQuestionIndex - 1)
    }
  }

  fun submitCurrentChallenge() {
    timerJob?.cancel()
    val session = _challengeSession.value ?: return

    var correctCount = 0
    session.questions.forEachIndexed { index, question ->
      val chosen = session.selectedAnswers[index]
      if (chosen != null && chosen == question.correctOptionIndex) {
        correctCount++
      }
    }

    val timeSpent = session.totalSeconds - session.remainingSeconds
    val attempt = repository.submitChallengeAttempt(
      challengeId = session.challenge.id,
      correctCount = correctCount,
      totalCount = session.questions.size,
      timeSpentSeconds = timeSpent
    )

    _lastAttempt.value = attempt
    _challengeSession.value = null
    _currentScreen.value = AppScreen.CHALLENGE_RESULT
  }

  fun cancelChallenge() {
    timerJob?.cancel()
    _challengeSession.value = null
    _currentScreen.value = AppScreen.MAIN_STUDENT
  }

  override fun onCleared() {
    super.onCleared()
    timerJob?.cancel()
  }
}
