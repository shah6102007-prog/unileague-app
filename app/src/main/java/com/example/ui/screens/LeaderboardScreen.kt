package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DepartmentRanking
import com.example.data.model.UserProfile
import com.example.ui.LeaderboardScope
import com.example.ui.theme.*

data class LeaderboardEntry(
  val rank: Int,
  val name: String,
  val department: String,
  val points: Int,
  val isCurrentUser: Boolean = false,
  val avatar: String = "🎓"
)

@Composable
fun LeaderboardScreen(
  currentUser: UserProfile,
  departmentRankings: List<DepartmentRanking>,
  selectedScope: LeaderboardScope,
  searchQuery: String,
  onScopeChange: (LeaderboardScope) -> Unit,
  onSearchChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  // Demo Leaderboard dataset
  val rawStudents = listOf(
    LeaderboardEntry(1, "أحمد العتيبي", "علوم الحاسب", 5800, false, "👨‍🎓"),
    LeaderboardEntry(2, "سارة الغامدي", "تقنية المعلومات", 5500, false, "👩‍🎓"),
    LeaderboardEntry(3, currentUser.name, currentUser.department, currentUser.points, true, currentUser.avatar.ifEmpty { "👩‍🎓" }),
    LeaderboardEntry(4, "عمر الشهري", "نظم المعلومات", 4950, false, "👨‍💻"),
    LeaderboardEntry(5, "ريان القحطاني", "تقنية المعلومات", 4600, false, "👨‍🎓"),
    LeaderboardEntry(6, "لين الحربي", "علوم الحاسب", 4300, false, "👩‍💻"),
    LeaderboardEntry(7, "فيصل الدوسري", "هندسة البرمجيات", 4100, false, "👨‍🎓"),
    LeaderboardEntry(8, "منيرة السبيعي", "الذكاء الاصطناعي", 3950, false, "👩‍🎓")
  )

  val filteredStudents = rawStudents
    .map { if (it.isCurrentUser) it.copy(points = currentUser.points) else it }
    .sortedByDescending { it.points }
    .mapIndexed { index, item -> item.copy(rank = index + 1) }
    .filter {
      searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.department.contains(searchQuery, ignoreCase = true)
    }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(top = 12.dp)
  ) {
    // Search Bar
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchChange,
      placeholder = { Text("ابحث عن طالب أو قسم...") },
      leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { onSearchChange("") }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear")
          }
        }
      },
      singleLine = true,
      shape = RoundedCornerShape(14.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .testTag("leaderboard_search_input")
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Scope Filters
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      LeaderboardScope.values().forEach { scope ->
        FilterChip(
          selected = selectedScope == scope,
          onClick = { onScopeChange(scope) },
          label = { Text(scope.labelAr, fontSize = 12.sp, fontWeight = if (selectedScope == scope) FontWeight.Bold else FontWeight.Normal) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(6.dp))

    if (selectedScope == LeaderboardScope.DEPARTMENTS_LEAGUE) {
      // --- DEPARTMENT LEAGUE (Section 15) ---
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 6.dp)
      ) {
        item {
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(Icons.Default.Apartment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Column {
                Text("منافسة الأقسام الأكاديمية 🎓", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("تُحسب نقاط كل قسم تراكمياً بناءً على إنجازات ونتائج طلابه في التحديات والبطولات.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }

        items(departmentRankings) { dept ->
          val isUserDept = dept.departmentName == currentUser.department
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isUserDept) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(
                    when (dept.rank) {
                      1 -> UniGoldLight
                      2 -> Color(0xFFE2E8F0)
                      3 -> Color(0xFFFDBA74)
                      else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                  ),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = when (dept.rank) {
                    1 -> "🥇"
                    2 -> "🥈"
                    3 -> "🥉"
                    else -> "#${dept.rank}"
                  },
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Text(dept.departmentName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                  if (isUserDept) {
                    Surface(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.primary) {
                      Text("قسمك", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                  }
                }
                Text("${dept.studentCount} طالب مشارك", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "%,d".format(dept.totalPoints),
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp,
                  color = MaterialTheme.colorScheme.primary
                )
                Text("نقطة", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }
      }
    } else {
      // --- STUDENTS LEADERBOARD WITH PODIUM ---
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 6.dp)
      ) {
        // Top 3 Podium
        if (filteredStudents.size >= 3 && searchQuery.isBlank()) {
          item {
            Card(
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = "منصة التتويج الأكاديمية 🏆",
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceEvenly,
                  verticalAlignment = Alignment.Bottom
                ) {
                  // 2nd Place
                  PodiumColumn(
                    entry = filteredStudents[1],
                    medal = "🥈",
                    color = Color(0xFF94A3B8),
                    height = 80.dp
                  )
                  // 1st Place (Center, tallest)
                  PodiumColumn(
                    entry = filteredStudents[0],
                    medal = "🥇",
                    color = UniGoldLight,
                    height = 105.dp
                  )
                  // 3rd Place
                  PodiumColumn(
                    entry = filteredStudents[2],
                    medal = "🥉",
                    color = Color(0xFFFDBA74),
                    height = 65.dp
                  )
                }
              }
            }
          }
        }

        // Students Table List
        items(filteredStudents) { student ->
          val isCurrentUser = student.isCurrentUser
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isCurrentUser) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentUser) 3.dp else 1.dp),
            modifier = Modifier
              .fillMaxWidth()
              .then(
                if (isCurrentUser) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp))
                else Modifier
              )
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              // Rank Medal / Number
              Text(
                text = when (student.rank) {
                  1 -> "🥇"
                  2 -> "🥈"
                  3 -> "🥉"
                  else -> "#${student.rank}"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.width(32.dp),
                textAlign = TextAlign.Center
              )

              // Avatar
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
              ) {
                Text(student.avatar, fontSize = 20.sp)
              }

              // Name & Department
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                  Text(
                    text = student.name,
                    fontWeight = if (isCurrentUser) FontWeight.ExtraBold else FontWeight.SemiBold,
                    fontSize = 14.sp
                  )
                  if (isCurrentUser) {
                    Text("(أنت)", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                  }
                }
                Text(
                  text = student.department,
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              // Points
              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "%,d".format(student.points),
                  fontWeight = FontWeight.Black,
                  fontSize = 15.sp,
                  color = if (isCurrentUser) UniGold else MaterialTheme.colorScheme.primary
                )
                Text("نقطة", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun PodiumColumn(
  entry: LeaderboardEntry,
  medal: String,
  color: Color,
  height: androidx.compose.ui.unit.Dp
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    Text(medal, fontSize = 22.sp)
    Text(
      text = entry.name.split(" ").firstOrNull() ?: entry.name,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
      maxLines = 1
    )
    Text(
      text = "%,d".format(entry.points),
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = UniGold
    )
    Box(
      modifier = Modifier
        .width(80.dp)
        .height(height)
        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        .background(color.copy(alpha = 0.35f)),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "#${entry.rank}",
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}
