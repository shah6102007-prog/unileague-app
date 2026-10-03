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
import com.example.data.model.Competition
import com.example.data.model.Team
import com.example.ui.theme.*

@Composable
fun CompetitionsAndTeamsScreen(
  competitions: List<Competition>,
  teams: List<Team>,
  onJoinCompetition: (String) -> Unit,
  onJoinTeam: (String) -> Unit,
  onCreateTeamClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Competitions, 1: Teams
  var selectedCompetitionForDetails by remember { mutableStateOf<Competition?>(null) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(top = 12.dp)
  ) {
    // Top Tabs
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.primary,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(12.dp))
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("🏆 البطولات الجامعية", fontWeight = FontWeight.Bold) },
        modifier = Modifier.testTag("tab_competitions")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("👥 فرق الطلاب", fontWeight = FontWeight.Bold) },
        modifier = Modifier.testTag("tab_teams")
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (selectedTab == 0) {
      // --- COMPETITIONS LIST ---
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
      ) {
        items(competitions) { comp ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                  text = comp.title,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.weight(1f),
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = when (comp.typeAr) {
                    "بين الكليات" -> Color(0xFFF3E8FF)
                    "بين الأقسام" -> Color(0xFFDBEAFE)
                    "جماعية" -> Color(0xFFFEF3C7)
                    else -> Color(0xFFDCFCE7)
                  }
                ) {
                  Text(
                    text = comp.typeAr,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (comp.typeAr) {
                      "بين الكليات" -> Color(0xFF7E22CE)
                      "بين الأقسام" -> Color(0xFF1E40AF)
                      "جماعية" -> Color(0xFFB45309)
                      else -> Color(0xFF15803D)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
              }

              Text(
                text = comp.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
              )

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("المسار: ${comp.skill}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Text("التاريخ: ${comp.date}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("الوقت: ${comp.startTime} - ${comp.endTime}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("الجائزة: +${comp.points} نقطة", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = UniEmerald)
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Button(
                  onClick = { onJoinCompetition(comp.id) },
                  shape = RoundedCornerShape(10.dp),
                  colors = ButtonDefaults.buttonColors(
                    containerColor = if (comp.isJoined) UniEmerald else MaterialTheme.colorScheme.primary
                  ),
                  modifier = Modifier.weight(1f)
                ) {
                  Text(
                    text = if (comp.isJoined) "منضم للبطولة ✅" else "انضم للبطولة 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                  )
                }

                OutlinedButton(
                  onClick = { selectedCompetitionForDetails = comp },
                  shape = RoundedCornerShape(10.dp)
                ) {
                  Text("عرض القواعد")
                }
              }
            }
          }
        }
      }
    } else {
      // --- TEAMS LIST ---
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp)
      ) {
        // Create Team Action Card
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .padding(14.dp)
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("أنشئ فريقك التنافسي 🚀", fontWeight = FontWeight.Bold, fontSize = 14.sp)
              Text("اجمع زملاءك من القسم للمنافسة في البطولات الجماعية.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(
              onClick = onCreateTeamClick,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("create_team_btn")
            ) {
              Text("+ إنشاء فريق", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(12.dp),
          contentPadding = PaddingValues(bottom = 80.dp)
        ) {
          items(teams) { team ->
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(team.avatar, fontSize = 22.sp)
                    }
                    Column {
                      Text(team.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                      Text(team.department, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                  }
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = UniGold.copy(alpha = 0.15f)
                  ) {
                    Text(
                      text = "الترتيب #${team.rank}",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = UniGold,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("الأعضاء: ${team.memberNames.joinToString("، ")}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text("إجمالي نقاط الفريق", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("%,d نقطة".format(team.totalPoints), fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                  }
                  Button(
                    onClick = { onJoinTeam(team.id) },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                  ) {
                    Text("طلب انضمام", fontSize = 12.sp)
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Competition Rules Dialog
  if (selectedCompetitionForDetails != null) {
    val comp = selectedCompetitionForDetails!!
    AlertDialog(
      onDismissRequest = { selectedCompetitionForDetails = null },
      title = { Text(comp.title, fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("📜 القواعد واللوائح:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Text(comp.rules, fontSize = 12.sp, lineHeight = 18.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text("عدد المشاركين المسجلين: ${comp.participantsCount}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("تاريخ البطولة: ${comp.date} (${comp.startTime} إلى ${comp.endTime})", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      },
      confirmButton = {
        Button(onClick = {
          onJoinCompetition(comp.id)
          selectedCompetitionForDetails = null
        }) {
          Text(if (comp.isJoined) "إلغاء الانضمام" else "انضم الآن")
        }
      },
      dismissButton = {
        TextButton(onClick = { selectedCompetitionForDetails = null }) {
          Text("إغلاق")
        }
      }
    )
  }
}
