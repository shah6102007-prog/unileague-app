package com.example

import com.example.data.UniLeagueRepository
import com.example.data.model.UserRole
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UniLeagueRepositoryTest {

  private lateinit var repository: UniLeagueRepository

  @Before
  fun setUp() {
    repository = UniLeagueRepository()
  }

  @Test
  fun testInitialStudentState() {
    val student = repository.currentUser.value
    assertNotNull(student)
    assertEquals("شهد الأحمدي", student?.name)
    assertEquals(UserRole.STUDENT, student?.role)
    assertTrue(student!!.points >= 5000)
    assertTrue(repository.challenges.value.isNotEmpty())
    assertTrue(repository.skills.value.isNotEmpty())
  }

  @Test
  fun testSubmitChallengeAttemptUpdatesPointsAndLevel() {
    val initialPoints = repository.currentUser.value?.points ?: 0
    val challenge = repository.challenges.value.first()

    // Submit attempt with 5 out of 5 correct
    val attempt = repository.submitChallengeAttempt(
      challengeId = challenge.id,
      correctCount = 5,
      totalCount = 5,
      timeSpentSeconds = 300
    )

    assertEquals(100, attempt.scorePercent)
    val updatedUser = repository.currentUser.value
    assertNotNull(updatedUser)
    assertTrue(updatedUser!!.points > initialPoints)
    assertEquals(1, repository.attempts.value.count { it.id == attempt.id })
  }

  @Test
  fun testSwitchUserRoles() {
    repository.switchUserRole(UserRole.TEACHER)
    assertEquals(UserRole.TEACHER, repository.currentUser.value?.role)

    repository.switchUserRole(UserRole.ADMIN)
    assertEquals(UserRole.ADMIN, repository.currentUser.value?.role)

    repository.switchUserRole(UserRole.STUDENT)
    assertEquals(UserRole.STUDENT, repository.currentUser.value?.role)
  }

  @Test
  fun testAiRecommendationGenerated() {
    repository.refreshAIRecommendations()
    val recommendation = repository.aiRecommendation.value
    assertNotNull(recommendation)
    assertTrue(recommendation!!.suggestedChallengeTitle.isNotBlank())
    assertTrue(recommendation.reasonAr.isNotBlank())
  }

  @Test
  fun testJoinCompetition() {
    val comp = repository.competitions.value.first()
    val initialStatus = comp.isJoined
    repository.joinCompetition(comp.id)

    val updatedComp = repository.competitions.value.find { it.id == comp.id }
    assertEquals(!initialStatus, updatedComp?.isJoined)
  }

  @Test
  fun testCreateTeam() {
    val initialCount = repository.teams.value.size
    repository.createTeam("TestRobots", "تقنية المعلومات")
    assertEquals(initialCount + 1, repository.teams.value.size)
  }
}
