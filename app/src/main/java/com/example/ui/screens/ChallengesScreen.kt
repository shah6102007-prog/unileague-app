package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Challenge
import com.example.ui.components.DifficultyBadge
import com.example.ui.theme.UniEmerald
import com.example.ui.theme.UniGold

@Composable
fun ChallengesScreen(
  challenges: List<Challenge>,
  selectedSkillFilter: String,
  selectedDifficultyFilter: String,
  onFilterChange: (skill: String, difficulty: String) -> Unit,
  onStartChallenge: (Challenge) -> Unit,
  modifier: Modifier = Modifier
) {
  val skillCategories = listOf(
    "الكل",
    "قواعد البيانات SQL",
    "الأمن السيبراني",
    "البرمجة والخوارزميات",
    "تطوير تطبيقات الويب",
    "الذكاء الاصطناعي"
  )

  val difficulties = listOf("الكل", "Easy", "Medium", "Hard", "Expert")

  val filteredChallenges = challenges.filter { challenge ->
    val matchesSkill = selectedSkillFilter == "الكل" || challenge.skillCategory.contains(selectedSkillFilter) || selectedSkillFilter.contains(challenge.skillCategory)
    val matchesDifficulty = selectedDifficultyFilter == "الكل" || challenge.difficulty.equals(selectedDifficultyFilter, ignoreCase = true)
    matchesSkill && matchesDifficulty
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(top = 12.dp)
  ) {
    // Skill Filter Horizontal Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      skillCategories.forEach { skill ->
        FilterChip(
          selected = selectedSkillFilter == skill,
          onClick = { onFilterChange(skill, selectedDifficultyFilter) },
          label = { Text(skill, fontSize = 12.sp, fontWeight = if (selectedSkillFilter == skill) FontWeight.Bold else FontWeight.Normal) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        )
      }
    }

    // Difficulty Filter Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = "الصعوبة:",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      difficulties.forEach { diff ->
        val label = when (diff) {
          "Easy" -> "سهل"
          "Medium" -> "متوسط"
          "Hard" -> "صعب"
          "Expert" -> "خبير"
          else -> "الكل"
        }
        FilterChip(
          selected = selectedDifficultyFilter == diff,
          onClick = { onFilterChange(selectedSkillFilter, diff) },
          label = { Text(label, fontSize = 11.sp) }
        )
      }
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Challenges List
    if (filteredChallenges.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(Icons.Outlined.SearchOff, contentDescription = null, modifier = Modifier.size(54.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("لا توجد تحديات مطابقة للفلتر المحدد", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Button(onClick = { onFilterChange("الكل", "الكل") }) {
            Text("إعادة ضبط الفلاتر")
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 6.dp)
      ) {
        items(filteredChallenges) { challenge ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("challenge_card_${challenge.id}")
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
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = challenge.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  Text(
                    text = "${challenge.skillCategory} • ${challenge.major}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
                DifficultyBadge(difficulty = challenge.difficulty)
              }

              Text(
                text = challenge.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
              )

              // Metrics Row: Questions, Time, Points, Participants
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(Icons.Outlined.Quiz, contentDescription = null, modifier = Modifier.size(15.dp), tint = MaterialTheme.colorScheme.primary)
                  Text("${challenge.questionCount} أسئلة", fontSize = 11.sp)
                }
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(Icons.Outlined.Timer, contentDescription = null, modifier = Modifier.size(15.dp), tint = UniGold)
                  Text("${challenge.durationMinutes} دقيقة", fontSize = 11.sp)
                }
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(Icons.Outlined.Groups, contentDescription = null, modifier = Modifier.size(15.dp), tint = MaterialTheme.colorScheme.secondary)
                  Text("${challenge.participantsCount} مشارك", fontSize = 11.sp)
                }
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(Icons.Outlined.Stars, contentDescription = null, modifier = Modifier.size(15.dp), tint = UniEmerald)
                  Text("+${challenge.points} نقطة", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = UniEmerald)
                }
              }

              Button(
                onClick = { onStartChallenge(challenge) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("start_challenge_btn_${challenge.id}")
              ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("ابدأ التحدي", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}
