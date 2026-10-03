package com.example.data.model

enum class UserRole(val labelAr: String, val labelEn: String) {
  STUDENT("طالب", "Student"),
  TEACHER("عضو هيئة تدريس", "Teacher"),
  ADMIN("إدارة الجامعة", "University Admin")
}

data class UserProfile(
  val id: String,
  val name: String,
  val email: String,
  val role: UserRole,
  val avatar: String = "",
  val university: String = "جامعة الملك سعود",
  val college: String = "كلية علوم الحاسب والمعلومات",
  val department: String = "تقنية المعلومات",
  val major: String = "هندسة البرمجيات والبيانات",
  val academicLevel: String = "المستوى الخامس - سنة 3",
  val points: Int = 5200,
  val levelNumber: Int = 3,
  val levelTitle: String = "متقدم",
  val rankUniversity: Int = 3,
  val rankCollege: Int = 2,
  val rankDepartment: Int = 1,
  val completedChallengesCount: Int = 14,
  val unlockedAchievementsCount: Int = 7,
  val hoursSpent: Float = 28.5f
)

data class SkillItem(
  val id: String,
  val nameAr: String,
  val nameEn: String,
  val category: String,
  val score: Int, // 0 - 100
  val levelStatusAr: String, // ممتاز، جيد جدًا، جيد، متوسط، يحتاج إلى تطوير
  val levelStatusEn: String,
  val iconName: String = "code"
)

data class Question(
  val id: String,
  val text: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val points: Int = 50
)

data class Challenge(
  val id: String,
  val title: String,
  val description: String,
  val skillCategory: String,
  val major: String = "علوم الحاسب وتقنية المعلومات",
  val difficulty: String, // Easy, Medium, Hard, Expert
  val durationMinutes: Int,
  val points: Int,
  val participantsCount: Int,
  val isTeam: Boolean = false,
  val startDate: String = "2026-09-01",
  val endDate: String = "2026-09-30",
  val createdBy: String = "د. عبد الرحمن الخالدي",
  val questions: List<Question>
) {
  val questionCount: Int get() = questions.size
}

data class ChallengeAttempt(
  val id: String,
  val challengeId: String,
  val challengeTitle: String,
  val userId: String,
  val scorePercent: Int,
  val correctCount: Int,
  val totalCount: Int,
  val pointsEarned: Int,
  val timeSpentSeconds: Int,
  val date: String,
  val skillCategory: String,
  val difficulty: String
)

data class Achievement(
  val id: String,
  val titleAr: String,
  val titleEn: String,
  val descriptionAr: String,
  val descriptionEn: String,
  val iconName: String,
  val unlocked: Boolean,
  val unlockedDate: String? = null,
  val badgeColor: Long = 0xFFD97706,
  val requirementHint: String
)

data class Competition(
  val id: String,
  val title: String,
  val description: String,
  val skill: String,
  val typeAr: String, // فردية، جماعية، بين الأقسام، بين الكليات
  val typeEn: String,
  val date: String,
  val startTime: String,
  val endTime: String,
  val participantsCount: Int,
  val points: Int,
  val rules: String,
  val isJoined: Boolean = false
)

data class Team(
  val id: String,
  val name: String,
  val department: String,
  val avatar: String = "👥",
  val memberNames: List<String>,
  val totalPoints: Int,
  val competitionsWon: Int,
  val rank: Int
)

data class DepartmentRanking(
  val id: String,
  val departmentName: String,
  val collegeName: String,
  val studentCount: Int,
  val totalPoints: Int,
  val rank: Int
)

data class AppNotification(
  val id: String,
  val title: String,
  val message: String,
  val timeAgo: String,
  val type: String, // challenge, tournament, badge, level, team, rank
  val isRead: Boolean = false
)

data class AIRecommendation(
  val skillName: String,
  val reasonAr: String,
  val reasonEn: String,
  val suggestedChallengeId: String,
  val suggestedChallengeTitle: String,
  val urgency: String = "متوسطة" // عالية، متوسطة، تحفيزية
)

data class UserAccount(
  val id: String,
  val name: String,
  val email: String,
  val role: UserRole,
  val department: String,
  val status: String = "نشط", // نشط، معطل
  val points: Int = 0
)
