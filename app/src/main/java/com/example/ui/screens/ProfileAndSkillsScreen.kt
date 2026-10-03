package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.model.*
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.*

@Composable
fun ProfileAndSkillsScreen(
  user: UserProfile,
  skills: List<SkillItem>,
  achievements: List<Achievement>,
  attempts: List<ChallengeAttempt>,
  onEditProfileClick: () -> Unit,
  onAddSkillClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedSubTab by remember { mutableStateOf(0) } // 0: Skills, 1: Achievements, 2: History

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
  ) {
    // 1. Profile Header Card
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(56.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
              ) {
                Text(user.avatar.ifEmpty { "👩‍🎓" }, fontSize = 32.sp)
              }
              Column {
                Text(user.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("${user.major} • ${user.academicLevel}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("${user.university} - ${user.college}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
              }
            }

            IconButton(
              onClick = onEditProfileClick,
              modifier = Modifier.testTag("edit_profile_btn")
            ) {
              Icon(Icons.Outlined.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary)
            }
          }

          HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

          // Key Stats Grid: Level, Points, Department Rank
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("المستوى الأكاديمي", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text("Level ${user.levelNumber} (${user.levelTitle})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("إجمالي النقاط", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text("%,d".format(user.points), fontSize = 14.sp, fontWeight = FontWeight.Black, color = UniGold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("الترتيب بالقسم", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text("#${user.rankDepartment}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = UniEmerald)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("الترتيب بالجامعة", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text("#${user.rankUniversity}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. Sub Tabs: Skills / Achievements / Attempt History
    item {
      TabRow(
        selectedTabIndex = selectedSubTab,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
      ) {
        Tab(
          selected = selectedSubTab == 0,
          onClick = { selectedSubTab = 0 },
          text = { Text("المهارات (${skills.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
          modifier = Modifier.testTag("subtab_skills")
        )
        Tab(
          selected = selectedSubTab == 1,
          onClick = { selectedSubTab = 1 },
          text = { Text("الإنجازات (${achievements.count { it.unlocked }}/${achievements.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
          modifier = Modifier.testTag("subtab_achievements")
        )
        Tab(
          selected = selectedSubTab == 2,
          onClick = { selectedSubTab = 2 },
          text = { Text("سجل المحاولات", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
          modifier = Modifier.testTag("subtab_history")
        )
      }
    }

    if (selectedSubTab == 0) {
      // --- SKILLS LIST (Section 6 & 10) ---
      item {
        SectionHeader(
          title = "شجرة المهارات ومستوى الكفاءة 🌳",
          actionLabel = "+ إضافة مهارة",
          onActionClick = onAddSkillClick
        )
      }

      items(skills) { skill ->
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
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
                text = skill.nameAr,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = when {
                  skill.score >= 85 -> Color(0xFFDCFCE7)
                  skill.score >= 70 -> Color(0xFFDBEAFE)
                  skill.score >= 55 -> Color(0xFFFEF3C7)
                  else -> Color(0xFFFFEDD5)
                }
              ) {
                Text(
                  text = "${skill.score}% • ${skill.levelStatusAr}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = when {
                    skill.score >= 85 -> Color(0xFF15803D)
                    skill.score >= 70 -> Color(0xFF1E40AF)
                    skill.score >= 55 -> Color(0xFFB45309)
                    else -> Color(0xFFC2410C)
                  },
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }

            LinearProgressIndicator(
              progress = { skill.score / 100f },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = when {
                skill.score >= 85 -> UniEmerald
                skill.score >= 70 -> MaterialTheme.colorScheme.primary
                skill.score >= 55 -> UniGold
                else -> UniRuby
              },
              trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
          }
        }
      }
    } else if (selectedSubTab == 1) {
      // --- ACHIEVEMENTS & BADGES (Section 11) ---
      item {
        SectionHeader(
          title = "الأوسمة والشارات الأكاديمية 🎖️"
        )
      }

      items(achievements) { ach ->
        val isUnlocked = ach.unlocked
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 2.dp else 0.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                  if (isUnlocked) Color(ach.badgeColor).copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (isUnlocked) Icons.Default.Stars else Icons.Outlined.Lock,
                contentDescription = null,
                tint = if (isUnlocked) Color(ach.badgeColor) else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(26.dp)
              )
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(
                text = ach.titleAr,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = ach.descriptionAr,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
              )
              Text(
                text = "الشرط: ${ach.requirementHint}",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
              )
            }

            if (isUnlocked) {
              Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFDCFCE7)) {
                Text(
                  text = "مكتملة ✅",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF15803D),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            } else {
              Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                Text(
                  text = "مقفلة 🔒",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }
        }
      }
    } else {
      // --- ATTEMPTS HISTORY ---
      item {
        SectionHeader(
          title = "سجل التحديات ونتائجك السابقة 📋"
        )
      }

      if (attempts.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text("لم تكمل أي تحدٍ حتى الآن. ابدأ أول تحدٍ لتحقيق النقاط!", color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      } else {
        items(attempts) { att ->
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .clip(CircleShape)
                  .background(
                    if (att.scorePercent >= 60) UniEmerald.copy(alpha = 0.15f) else UniGold.copy(alpha = 0.15f)
                  ),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "${att.scorePercent}%",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = if (att.scorePercent >= 60) UniEmerald else UniGold
                )
              }

              Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(att.challengeTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${att.date} • ${att.correctCount}/${att.totalCount} صحيح", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }

              Text(
                text = "+${att.pointsEarned} نقطة",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = UniGold
              )
            }
          }
        }
      }
    }
  }
}
