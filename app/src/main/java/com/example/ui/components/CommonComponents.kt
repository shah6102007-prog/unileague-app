package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.StudentTab
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniLeagueTopBar(
  currentRole: UserRole,
  unreadNotificationsCount: Int,
  isDarkMode: Boolean,
  isArabic: Boolean,
  onNotificationsClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onSwitchRole: (UserRole) -> Unit,
  modifier: Modifier = Modifier
) {
  var showRoleMenu by remember { mutableStateOf(false) }

  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
              Brush.linearGradient(
                colors = listOf(UniPrimary, UniPrimaryLight)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = UniGoldLight,
            modifier = Modifier.size(22.dp)
          )
        }
        Column {
          Text(
            text = "UniLeague",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
          )
          Text(
            text = if (isArabic) "دوري المهارات الجامعي" else "University Skills League",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
          )
        }
      }
    },
    actions = {
      // Role Badge with Quick Switch
      Box {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = when (currentRole) {
            UserRole.STUDENT -> MaterialTheme.colorScheme.primaryContainer
            UserRole.TEACHER -> MaterialTheme.colorScheme.secondaryContainer
            UserRole.ADMIN -> Color(0xFFF3E8FF)
          },
          modifier = Modifier
            .testTag("role_switcher_badge")
            .clip(RoundedCornerShape(20.dp))
            .clickable { showRoleMenu = true }
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = when (currentRole) {
                UserRole.STUDENT -> "🎓 " + if (isArabic) "طالب" else "Student"
                UserRole.TEACHER -> "👨‍🏫 " + if (isArabic) "دكتور" else "Teacher"
                UserRole.ADMIN -> "🏛️ " + if (isArabic) "إدارة" else "Admin"
              },
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = when (currentRole) {
                UserRole.STUDENT -> MaterialTheme.colorScheme.onPrimaryContainer
                UserRole.TEACHER -> MaterialTheme.colorScheme.onSecondaryContainer
                UserRole.ADMIN -> Color(0xFF6B21A8)
              }
            )
            Icon(
              imageVector = Icons.Default.ArrowDropDown,
              contentDescription = "Switch Role",
              modifier = Modifier.size(16.dp),
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        DropdownMenu(
          expanded = showRoleMenu,
          onDismissRequest = { showRoleMenu = false }
        ) {
          DropdownMenuItem(
            text = { Text("🎓 " + if (isArabic) "واجهة الطالب (Demo)" else "Student View") },
            onClick = {
              showRoleMenu = false
              onSwitchRole(UserRole.STUDENT)
            }
          )
          DropdownMenuItem(
            text = { Text("👨‍🏫 " + if (isArabic) "واجهة الدكتور (Demo)" else "Teacher View") },
            onClick = {
              showRoleMenu = false
              onSwitchRole(UserRole.TEACHER)
            }
          )
          DropdownMenuItem(
            text = { Text("🏛️ " + if (isArabic) "واجهة إدارة الجامعة (Demo)" else "Admin View") },
            onClick = {
              showRoleMenu = false
              onSwitchRole(UserRole.ADMIN)
            }
          )
        }
      }

      // Notifications Button with Badge
      IconButton(
        onClick = onNotificationsClick,
        modifier = Modifier.testTag("topbar_notifications_button")
      ) {
        BadgedBox(
          badge = {
            if (unreadNotificationsCount > 0) {
              Badge(
                containerColor = UniRuby,
                contentColor = Color.White
              ) {
                Text(text = "$unreadNotificationsCount", fontSize = 10.sp)
              }
            }
          }
        ) {
          Icon(
            imageVector = if (unreadNotificationsCount > 0) Icons.Filled.Notifications else Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }

      // Settings Button
      IconButton(
        onClick = onSettingsClick,
        modifier = Modifier.testTag("topbar_settings_button")
      ) {
        Icon(
          imageVector = Icons.Outlined.Settings,
          contentDescription = "Settings",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    modifier = modifier
  )
}

@Composable
fun StudentBottomNavigationBar(
  currentTab: StudentTab,
  onTabSelected: (StudentTab) -> Unit,
  isArabic: Boolean,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 6.dp,
    modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    StudentTab.values().forEach { tab ->
      val selected = currentTab == tab
      val icon = when (tab) {
        StudentTab.DASHBOARD -> if (selected) Icons.Filled.Dashboard else Icons.Outlined.Dashboard
        StudentTab.CHALLENGES -> if (selected) Icons.Filled.Extension else Icons.Outlined.Extension
        StudentTab.COMPETITIONS -> if (selected) Icons.Filled.Groups else Icons.Outlined.Groups
        StudentTab.LEADERBOARD -> if (selected) Icons.Filled.Leaderboard else Icons.Outlined.Leaderboard
        StudentTab.PROFILE -> if (selected) Icons.Filled.Person else Icons.Outlined.PersonOutline
      }

      val title = if (isArabic) tab.titleAr else tab.titleEn

      NavigationBarItem(
        selected = selected,
        onClick = { onTabSelected(tab) },
        icon = {
          Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp)
          )
        },
        label = {
          Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("bottom_nav_${tab.name.lowercase()}")
      )
    }
  }
}

@Composable
fun StatCard(
  title: String,
  value: String,
  subtitle: String? = null,
  icon: ImageVector,
  iconTint: Color = UniPrimary,
  bgTint: Color = MaterialTheme.colorScheme.surfaceVariant,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = bgTint)
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(iconTint.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
          )
        }
        Text(
          text = value,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 18.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
      Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          fontSize = 10.sp,
          color = iconTint,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
fun DifficultyBadge(
  difficulty: String,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, labelAr) = when (difficulty.lowercase()) {
    "easy" -> Triple(Color(0xFFDCFCE7), Color(0xFF15803D), "سهل")
    "medium" -> Triple(Color(0xFFFEF3C7), Color(0xFFB45309), "متوسط")
    "hard" -> Triple(Color(0xFFFFEDD5), Color(0xFFC2410C), "صعب")
    "expert" -> Triple(Color(0xFFF3E8FF), Color(0xFF7E22CE), "خبير")
    else -> Triple(Color(0xFFF1F5F9), Color(0xFF475569), difficulty)
  }

  Surface(
    shape = RoundedCornerShape(8.dp),
    color = bgColor,
    modifier = modifier
  ) {
    Text(
      text = labelAr,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = textColor,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
    )
  }
}

@Composable
fun SectionHeader(
  title: String,
  actionLabel: String? = null,
  onActionClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = title,
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    if (actionLabel != null && onActionClick != null) {
      TextButton(
        onClick = onActionClick,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
      ) {
        Text(
          text = actionLabel,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary
        )
      }
    }
  }
}
