package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Challenge
import com.example.data.model.UserProfile
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.*

@Composable
fun TeacherDashboardScreen(
  user: UserProfile,
  challenges: List<Challenge>,
  onCreateChallengeClick: () -> Unit,
  onDeleteChallenge: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Challenges, 1: Student Analytics
  var challengeToDelete by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
  ) {
    // 1. Teacher Welcome Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
          ) {
            Text("👨‍🏫", fontSize = 28.sp)
          }
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "لوحة تحكم الأستاذ الأكاديمي",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
              text = "${user.name} • ${user.college} (${user.department})",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
            )
          }
        }
      }
    }

    // 2. Metrics Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = "التحديات النشطة",
          value = "${challenges.size}",
          subtitle = "منشورة للطلاب",
          icon = Icons.Default.Quiz,
          iconTint = UniGold,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "إجمالي المشاركات",
          value = "${challenges.sumOf { it.participantsCount }}",
          subtitle = "طالب تم تقييمهم",
          icon = Icons.Default.Groups,
          iconTint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "متوسط الدرجات",
          value = "78%",
          subtitle = "دقة الاستجابة",
          icon = Icons.Default.TrendingUp,
          iconTint = UniEmerald,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Quick Action: Add Challenge Button
    item {
      Button(
        onClick = onCreateChallengeClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("teacher_create_challenge_btn")
      ) {
        Icon(Icons.Default.AddCircle, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("إنشاء تحدٍ أكاديمي جديد مع الأسئلة", fontWeight = FontWeight.Bold)
      }
    }

    // 4. Tab Selector (Challenges Management vs Analytics)
    item {
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("إدارة التحديات المنشورة", fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("تحليلات أداء الطلاب", fontWeight = FontWeight.Bold) }
        )
      }
    }

    if (selectedTab == 0) {
      // --- CHALLENGES MANAGEMENT LIST ---
      items(challenges) { challenge ->
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = challenge.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              DifficultyBadge(difficulty = challenge.difficulty)
            }

            Text(
              text = "${challenge.skillCategory} • ${challenge.questionCount} أسئلة • ${challenge.durationMinutes} دقيقة • +${challenge.points} نقطة",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "عدد المختبرين: ${challenge.participantsCount} طالب",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
              )

              IconButton(
                onClick = { challengeToDelete = challenge.id },
                modifier = Modifier.testTag("delete_challenge_${challenge.id}")
              ) {
                Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = UniRuby)
              }
            }
          }
        }
      }
    } else {
      // --- STUDENT PERFORMANCE ANALYTICS (Section 16) ---
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text("تحليل الأسئلة الأكثر صعوبة ⚠️", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
              text = "• سؤال خوارزميات التعقيد الزمني Big-O (Merge Sort): نسبة الخطأ 48%\n• سؤال أنواع فهارس SQL (Clustered Index): نسبة الخطأ 35%\n• سؤال نموذج الحماية CIA Triad: نسبة الدقة 89% (ممتاز)",
              fontSize = 12.sp,
              lineHeight = 20.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text("الطلاب المتميزون في مسارك الأكاديمي 🌟", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
              text = "1. شهد الأحمدي (تقنية المعلومات) - 5,200 نقطة (دقة 92%)\n2. أحمد العتيبي (علوم الحاسب) - 5,800 نقطة (دقة 89%)\n3. سارة الغامدي (تقنية المعلومات) - 5,500 نقطة (دقة 87%)",
              fontSize = 12.sp,
              lineHeight = 22.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text("تنبيه التدخل الأكاديمي المبكر 🔔", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = UniRuby)
            Text(
              text = "يوجد 6 طلاب انخفضت درجاتهم في مهارة 'الأمن السيبراني' عن 50%. تم إرسال توصيات ذكية تلقائية لهم لتمكينهم من التدريب التعويضي.",
              fontSize = 12.sp,
              lineHeight = 18.sp,
              color = Color(0xFF991B1B)
            )
          }
        }
      }
    }
  }

  // Delete Confirmation Dialog
  if (challengeToDelete != null) {
    AlertDialog(
      onDismissRequest = { challengeToDelete = null },
      title = { Text("تأكيد حذف التحدي") },
      text = { Text("هل أنت متأكد من حذف هذا التحدي نهائيًا من منصة الطلاب؟") },
      confirmButton = {
        Button(
          onClick = {
            onDeleteChallenge(challengeToDelete!!)
            challengeToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = UniRuby)
        ) {
          Text("حذف")
        }
      },
      dismissButton = {
        TextButton(onClick = { challengeToDelete = null }) {
          Text("إلغاء")
        }
      }
    )
  }
}
