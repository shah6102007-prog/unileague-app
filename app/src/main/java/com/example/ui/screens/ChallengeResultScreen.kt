package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChallengeAttempt
import com.example.data.model.UserProfile
import com.example.ui.StudentTab
import com.example.ui.theme.*

@Composable
fun ChallengeResultScreen(
  attempt: ChallengeAttempt,
  currentUser: UserProfile,
  onBackToDashboard: () -> Unit,
  onViewLeaderboard: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isPass = attempt.scorePercent >= 60
  val minutes = attempt.timeSpentSeconds / 60
  val seconds = attempt.timeSpentSeconds % 60
  val formattedTime = "%d دقيقة و %d ثانية".format(minutes, seconds)
  val incorrectCount = attempt.totalCount - attempt.correctCount

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 20.dp, vertical = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Celebration Icon
    Box(
      modifier = Modifier
        .size(90.dp)
        .clip(CircleShape)
        .background(
          if (isPass) Brush.radialGradient(listOf(UniEmeraldLight, UniEmerald))
          else Brush.radialGradient(listOf(UniGoldLight, UniGold))
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = if (isPass) Icons.Default.EmojiEvents else Icons.Default.Celebration,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(50.dp)
      )
    }

    Text(
      text = if (attempt.scorePercent >= 85) "أداء استثنائي! 🎉" else if (isPass) "أحسنت! إنجاز رائع 👏" else "محاولة جيدة! واصل التدريب 💪",
      fontSize = 22.sp,
      fontWeight = FontWeight.ExtraBold,
      color = MaterialTheme.colorScheme.onSurface
    )

    Text(
      text = attempt.challengeTitle,
      fontSize = 14.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      textAlign = TextAlign.Center
    )

    // Main Score Card
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Percentage Display
        Text(
          text = "${attempt.scorePercent}%",
          fontSize = 48.sp,
          fontWeight = FontWeight.Black,
          color = if (isPass) UniEmerald else UniGold
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        // Breakdown Grid
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("الإجابات الصحيحة", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${attempt.correctCount} / ${attempt.totalCount}",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = UniEmerald
            )
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("الإجابات الخاطئة", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "$incorrectCount",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = if (incorrectCount > 0) UniRuby else MaterialTheme.colorScheme.onSurface
            )
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("النقاط المكتسبة", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "+${attempt.pointsEarned}",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = UniGold
            )
          }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("الوقت المستغرق:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(formattedTime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("مستوى الطالب الحالي:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("Level ${currentUser.levelNumber} (${currentUser.levelTitle})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("إجمالي نقاطك الآن:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("%,d نقطة".format(currentUser.points), fontSize = 13.sp, fontWeight = FontWeight.Black, color = UniGold)
        }
      }
    }

    // Performance Diagnostic Insight
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Default.Analytics, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Column {
          Text(
            text = "تحليل الأداء الذكي 📊",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
          Text(
            text = if (attempt.scorePercent >= 80)
              "تم تحديث نقاط قسم '${currentUser.department}' للأعلى! عززت خبرتك في ${attempt.skillCategory} وارتفعت نسبة كفاءتك."
            else
              "تم تسجيل نتيجتك ونقاطك. يوصى بمراجعة المفاهيم المتعلقة بـ ${attempt.skillCategory} قبل خوض بطولات الفرق.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 16.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Action Buttons
    Button(
      onClick = onBackToDashboard,
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("result_back_dashboard_btn")
    ) {
      Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("العودة إلى الصفحة الرئيسية", fontWeight = FontWeight.Bold)
    }

    OutlinedButton(
      onClick = onViewLeaderboard,
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("result_view_leaderboard_btn")
    ) {
      Icon(Icons.Default.Leaderboard, contentDescription = null, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("مشاهدة الترتيب المحدث", fontWeight = FontWeight.Bold)
    }
  }
}
