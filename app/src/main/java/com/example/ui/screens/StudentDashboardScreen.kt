package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.StudentTab
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.*

@Composable
fun StudentDashboardScreen(
  user: UserProfile,
  challenges: List<Challenge>,
  competitions: List<Competition>,
  achievements: List<Achievement>,
  aiRecommendation: AIRecommendation?,
  onStartChallenge: (Challenge) -> Unit,
  onNavigateTab: (StudentTab) -> Unit,
  modifier: Modifier = Modifier
) {
  // Calculate progress toward next level (Levels: 1: <1500, 2: <3500, 3: <6000, 4: <9000, 5: 9000+)
  val (prevLevelPoints, nextLevelPoints) = when (user.levelNumber) {
    1 -> 0 to 1500
    2 -> 1500 to 3500
    3 -> 3500 to 6000
    4 -> 6000 to 9000
    else -> 9000 to 12000
  }
  val pointsInCurrentLevel = (user.points - prevLevelPoints).coerceAtLeast(0)
  val levelSpan = (nextLevelPoints - prevLevelPoints).coerceAtLeast(1)
  val levelProgress = (pointsInCurrentLevel.toFloat() / levelSpan).coerceIn(0f, 1f)
  val pointsToNextLevel = (nextLevelPoints - user.points).coerceAtLeast(0)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
  ) {
    // 1. Welcome & Level Banner (Academic League Hero)
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("dashboard_hero_card")
      ) {
        Box(
          modifier = Modifier
            .background(
              Brush.linearGradient(
                colors = listOf(
                  UniPrimaryVariant,
                  UniPrimary,
                  Color(0xFF1D4ED8)
                )
              )
            )
            .padding(20.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Student Greeting Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "مرحبًا ${user.name} 👋",
                  fontSize = 22.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color.White
                )
                Text(
                  text = "${user.college} • ${user.department}",
                  fontSize = 12.sp,
                  color = Color(0xFFCBD5E1)
                )
              }
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .clip(CircleShape)
                  .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = user.avatar.ifEmpty { "👩‍🎓" },
                  fontSize = 26.sp
                )
              }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

            // Level & Points Info
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = UniGoldLight
                  ) {
                    Text(
                      text = "Level ${user.levelNumber}",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold,
                      color = Color(0xFF0F172A),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                  }
                  Text(
                    text = user.levelTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "متبقي $pointsToNextLevel نقطة إلى Level ${user.levelNumber + 1}",
                  fontSize = 11.sp,
                  color = Color(0xFFE2E8F0)
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "%,d".format(user.points),
                  fontSize = 24.sp,
                  fontWeight = FontWeight.Black,
                  color = UniGoldLight
                )
                Text(
                  text = "نقطة مكتسبة",
                  fontSize = 11.sp,
                  color = Color(0xFFCBD5E1)
                )
              }
            }

            // Progress Bar to next level
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              LinearProgressIndicator(
                progress = { levelProgress },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(8.dp)
                  .clip(RoundedCornerShape(4.dp)),
                color = UniGoldLight,
                trackColor = Color.White.copy(alpha = 0.25f)
              )
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "${(levelProgress * 100).toInt()}% تقدم المستوى",
                  fontSize = 10.sp,
                  color = Color(0xFFCBD5E1)
                )
                Text(
                  text = "الهدف: $nextLevelPoints",
                  fontSize = 10.sp,
                  color = Color(0xFFCBD5E1)
                )
              }
            }
          }
        }
      }
    }

    // 2. Quick Stat Tiles Grid (Rank, Completed Challenges, Achievements, Study Hours)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = "الترتيب بالقسم",
          value = "#${user.rankDepartment}",
          subtitle = "#${user.rankUniversity} بالجامعة",
          icon = Icons.Default.Leaderboard,
          iconTint = UniGold,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "تحديات مكتملة",
          value = "${user.completedChallengesCount}",
          subtitle = "دقة 88%",
          icon = Icons.Default.CheckCircle,
          iconTint = UniEmerald,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "الشارات المفتوحة",
          value = "${user.unlockedAchievementsCount}",
          subtitle = "من 8 شارات",
          icon = Icons.Default.MilitaryTech,
          iconTint = UniPurple,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. AI Smart Recommendation Banner (Requested in prompt #19)
    if (aiRecommendation != null) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_recommendation_card")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(UniCyan.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = UniCyan,
                modifier = Modifier.size(24.dp)
              )
            }
            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "توصية ذكية مخصصة لك 💡",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = UniCyan.copy(alpha = 0.15f)
                ) {
                  Text(
                    text = aiRecommendation.urgency,
                    fontSize = 10.sp,
                    color = UniCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
              Text(
                text = aiRecommendation.reasonAr,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
              )
              val matchingChallenge = challenges.find { it.id == aiRecommendation.suggestedChallengeId }
              if (matchingChallenge != null) {
                Button(
                  onClick = { onStartChallenge(matchingChallenge) },
                  shape = RoundedCornerShape(10.dp),
                  contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Text(
                    text = "بدء التحدي المقترح: ${matchingChallenge.title}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        }
      }
    }

    // 4. Featured Upcoming / Next Challenge (Prompt Section 5 example)
    item {
      SectionHeader(
        title = "التحدي القادم 🎯",
        actionLabel = "عرض الكل",
        onActionClick = { onNavigateTab(StudentTab.CHALLENGES) }
      )
      val nextChallenge = challenges.firstOrNull()
      if (nextChallenge != null) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = nextChallenge.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              DifficultyBadge(difficulty = nextChallenge.difficulty)
            }

            Text(
              text = nextChallenge.description,
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(Icons.Outlined.Quiz, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                Text("${nextChallenge.questionCount} أسئلة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(Icons.Outlined.Timer, contentDescription = null, modifier = Modifier.size(16.dp), tint = UniGold)
                Text("${nextChallenge.durationMinutes} دقيقة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(Icons.Outlined.Stars, contentDescription = null, modifier = Modifier.size(16.dp), tint = UniEmerald)
                Text("+${nextChallenge.points} نقطة", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = UniEmerald)
              }
            }

            Button(
              onClick = { onStartChallenge(nextChallenge) },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("dashboard_start_challenge_btn"),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("ابدأ التحدي الآن", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
          }
        }
      }
    }

    // 5. Upcoming Competitions Preview
    item {
      SectionHeader(
        title = "البطولات القادمة 🏆",
        actionLabel = "كل البطولات",
        onActionClick = { onNavigateTab(StudentTab.COMPETITIONS) }
      )
    }

    items(competitions.take(2)) { comp ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(UniGold.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = UniGold, modifier = Modifier.size(24.dp))
          }
          Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(comp.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("${comp.date} • ${comp.typeAr}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("جائزة: +${comp.points} نقطة", fontSize = 11.sp, color = UniEmerald, fontWeight = FontWeight.SemiBold)
          }
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (comp.isJoined) Color(0xFFDCFCE7) else MaterialTheme.colorScheme.primaryContainer
          ) {
            Text(
              text = if (comp.isJoined) "منضم ✅" else "متاحة",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (comp.isJoined) Color(0xFF15803D) else MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }
      }
    }

    // 6. Recent Achievements Badge Row
    item {
      SectionHeader(
        title = "آخر الإنجازات والشارات 🎖️",
        actionLabel = "ملفي الشخصي",
        onActionClick = { onNavigateTab(StudentTab.PROFILE) }
      )
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(achievements.filter { it.unlocked }) { ach ->
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.width(130.dp)
          ) {
            Column(
              modifier = Modifier.padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
                  .background(Color(ach.badgeColor).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Stars,
                  contentDescription = null,
                  tint = Color(ach.badgeColor),
                  modifier = Modifier.size(24.dp)
                )
              }
              Text(
                text = ach.titleAr,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = ach.unlockedDate ?: "مفتوح",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }
  }
}
