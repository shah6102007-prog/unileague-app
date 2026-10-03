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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.*

@Composable
fun AdminDashboardScreen(
  user: UserProfile,
  userAccounts: List<UserAccount>,
  onUpdateUserStatus: (userId: String, status: String) -> Unit,
  onChangeUserRole: (userId: String, role: UserRole) -> Unit,
  onAddUserClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: System Analytics, 1: User Management

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
  ) {
    // 1. Admin Header
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),
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
              .background(UniPurple),
            contentAlignment = Alignment.Center
          ) {
            Text("🏛️", fontSize = 26.sp)
          }
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "لوحة إدارة دوري المهارات الجامعي",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = Color(0xFF581C87)
            )
            Text(
              text = "${user.name} • ${user.college} - عمادة شؤون الطلاب",
              fontSize = 11.sp,
              color = Color(0xFF6B21A8)
            )
          }
        }
      }
    }

    // 2. Tab Navigation
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
          text = { Text("مؤشرات المنظومة والإحصائيات", fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("إدارة الحسابات والصلاحيات", fontWeight = FontWeight.Bold) }
        )
      }
    }

    if (selectedTab == 0) {
      // --- SYSTEM ANALYTICS (Section 18) ---
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "إجمالي الطلاب",
            value = "1,980",
            subtitle = "+140 هذا الأسبوع",
            icon = Icons.Default.School,
            iconTint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "التحديات المنجزة",
            value = "4,620",
            subtitle = "جلسة اختبارية",
            icon = Icons.Default.TaskAlt,
            iconTint = UniEmerald,
            modifier = Modifier.weight(1f)
          )
        }
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "معدل التفاعل اليومي",
            value = "82.4%",
            subtitle = "نشاط مرتفع",
            icon = Icons.Default.Speed,
            iconTint = UniGold,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "النزاهة الأكاديمية",
            value = "99.4%",
            subtitle = "منع غش آمن",
            icon = Icons.Default.VerifiedUser,
            iconTint = UniEmerald,
            modifier = Modifier.weight(1f)
          )
        }
      }

      // Department Championship Standings
      item {
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
            Text("القسم الأكثر تفوقًا ونشاطًا 🥇", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("قسم تقنية المعلومات (كلية علوم الحاسب)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
              Text("15,800 نقطة", fontWeight = FontWeight.Black, color = UniGold, fontSize = 14.sp)
            }
            LinearProgressIndicator(
              progress = { 0.92f },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = UniGold
            )
            Text(
              text = "• المهارة الأكثر نشاطًا: تطوير الويب والبرمجة (1,240 محاولة ناجحة)\n• المهارة التي تحتاج دعم أكاديمي: الأمن السيبراني (متوسط الدقة 56%)",
              fontSize = 11.sp,
              lineHeight = 18.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // Server & API status
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = UniEmerald)
            Column {
              Text("حالة الخوادم وقاعدة بيانات المنظومة 🟢", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF166534))
              Text("قاعدة بيانات MySQL / Room ومحرك التوصيات AI يعملان بكفاءة وسرعة استجابة 45ms.", fontSize = 11.sp, color = Color(0xFF15803D))
            }
          }
        }
      }
    } else {
      // --- USER & ROLES MANAGEMENT (Section 17) ---
      item {
        Button(
          onClick = onAddUserClick,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_add_user_btn")
        ) {
          Icon(Icons.Default.PersonAdd, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("إضافة مستخدم جديد أو مشرف نظام", fontWeight = FontWeight.Bold)
        }
      }

      items(userAccounts) { acc ->
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
              Text(acc.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (acc.status == "نشط") Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
              ) {
                Text(
                  text = acc.status,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (acc.status == "نشط") Color(0xFF15803D) else Color(0xFFB91C1C),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
              }
            }

            Text(
              text = "${acc.email} • ${acc.department}",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "الدور الحالي: ${acc.role.labelAr}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
              )

              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Toggle Status (Activate / Suspend)
                OutlinedButton(
                  onClick = {
                    val nextStatus = if (acc.status == "نشط") "معطل" else "نشط"
                    onUpdateUserStatus(acc.id, nextStatus)
                  },
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text(if (acc.status == "نشط") "تعطيل" else "تفعيل", fontSize = 11.sp)
                }

                // Change Role
                OutlinedButton(
                  onClick = {
                    val nextRole = when (acc.role) {
                      UserRole.STUDENT -> UserRole.TEACHER
                      UserRole.TEACHER -> UserRole.ADMIN
                      UserRole.ADMIN -> UserRole.STUDENT
                    }
                    onChangeUserRole(acc.id, nextRole)
                  },
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text("ترقية الدور", fontSize = 11.sp)
                }
              }
            }
          }
        }
      }
    }
  }
}
