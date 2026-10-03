package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppNotification
import com.example.data.model.Question
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.ui.theme.UniEmerald
import com.example.ui.theme.UniRuby

@Composable
fun EditProfileDialog(
  user: UserProfile,
  onDismiss: () -> Unit,
  onSave: (name: String, university: String, college: String, department: String, major: String, academicLevel: String) -> Unit
) {
  var name by remember { mutableStateOf(user.name) }
  var university by remember { mutableStateOf(user.university) }
  var college by remember { mutableStateOf(user.college) }
  var department by remember { mutableStateOf(user.department) }
  var major by remember { mutableStateOf(user.major) }
  var academicLevel by remember { mutableStateOf(user.academicLevel) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("تعديل البيانات الأكاديمية", fontWeight = FontWeight.Bold) },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("الاسم الكامل") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = university,
          onValueChange = { university = it },
          label = { Text("الجامعة") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = college,
          onValueChange = { college = it },
          label = { Text("الكلية") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = department,
          onValueChange = { department = it },
          label = { Text("القسم") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = major,
          onValueChange = { major = it },
          label = { Text("التخصص") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = academicLevel,
          onValueChange = { academicLevel = it },
          label = { Text("المستوى الدراسي") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onSave(name, university, college, department, major, academicLevel)
          onDismiss()
        }
      ) {
        Text("حفظ التغييرات")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("إلغاء")
      }
    }
  )
}

@Composable
fun AddSkillDialog(
  onDismiss: () -> Unit,
  onAddSkill: (nameAr: String, nameEn: String, category: String) -> Unit
) {
  var nameAr by remember { mutableStateOf("") }
  var nameEn by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("تقنية المعلومات") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("إضافة مهارة أكاديمية جديدة", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
          value = nameAr,
          onValueChange = { nameAr = it },
          label = { Text("اسم المهارة بالعربية") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = nameEn,
          onValueChange = { nameEn = it },
          label = { Text("اسم المهارة بالإنجليزية (Skill Name)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = category,
          onValueChange = { category = it },
          label = { Text("المسار / التصنيف الأكاديمي") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (nameAr.isNotBlank()) {
            onAddSkill(nameAr, nameEn.ifBlank { nameAr }, category)
            onDismiss()
          }
        }
      ) {
        Text("إضافة المهارة")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("إلغاء")
      }
    }
  )
}

@Composable
fun CreateTeamDialog(
  userDepartment: String,
  onDismiss: () -> Unit,
  onCreateTeam: (name: String, department: String) -> Unit
) {
  var teamName by remember { mutableStateOf("") }
  var department by remember { mutableStateOf(userDepartment) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("إنشاء فريق للبطولات الجامعية", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
          value = teamName,
          onValueChange = { teamName = it },
          label = { Text("اسم الفريق (مثال: CodeWarriors)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = department,
          onValueChange = { department = it },
          label = { Text("القسم الأكاديمي") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (teamName.isNotBlank()) {
            onCreateTeam(teamName, department)
            onDismiss()
          }
        }
      ) {
        Text("إنشاء الفريق")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("إلغاء")
      }
    }
  )
}

@Composable
fun CreateChallengeDialog(
  onDismiss: () -> Unit,
  onCreateChallenge: (
    title: String,
    description: String,
    skill: String,
    major: String,
    difficulty: String,
    duration: Int,
    points: Int,
    questions: List<Question>
  ) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var skill by remember { mutableStateOf("البرمجة والخوارزميات") }
  var difficulty by remember { mutableStateOf("Medium") }
  var duration by remember { mutableStateOf("20") }
  var points by remember { mutableStateOf("500") }

  // Question form
  var qText by remember { mutableStateOf("") }
  var opt1 by remember { mutableStateOf("") }
  var opt2 by remember { mutableStateOf("") }
  var opt3 by remember { mutableStateOf("") }
  var opt4 by remember { mutableStateOf("") }
  var correctOption by remember { mutableStateOf(0) }

  val questionsList = remember { mutableStateListOf<Question>() }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("إنشاء تحدٍ أكاديمي جديد", fontWeight = FontWeight.Bold) },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("عنوان التحدي") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("وصف التحدي") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = skill,
          onValueChange = { skill = it },
          label = { Text("المهارة المستهدفة") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("المدة (د)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = points,
            onValueChange = { points = it },
            label = { Text("النقاط") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
          )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        Text("إضافة أسئلة التحدي (المضافة: ${questionsList.size}):", fontWeight = FontWeight.Bold, fontSize = 13.sp)

        OutlinedTextField(
          value = qText,
          onValueChange = { qText = it },
          label = { Text("نص السؤال") },
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = opt1,
          onValueChange = { opt1 = it },
          label = { Text("الخيار أ (الصحيح مبدئيًا)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = opt2,
          onValueChange = { opt2 = it },
          label = { Text("الخيار ب") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = opt3,
          onValueChange = { opt3 = it },
          label = { Text("الخيار ج") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = opt4,
          onValueChange = { opt4 = it },
          label = { Text("الخيار د") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Button(
          onClick = {
            if (qText.isNotBlank() && opt1.isNotBlank() && opt2.isNotBlank()) {
              questionsList.add(
                Question(
                  id = "q_${System.currentTimeMillis()}",
                  text = qText,
                  options = listOf(opt1, opt2, opt3.ifBlank { "خيار إضافي" }, opt4.ifBlank { "خيار بديل" }),
                  correctOptionIndex = correctOption,
                  points = 100
                )
              )
              qText = ""
              opt1 = ""
              opt2 = ""
              opt3 = ""
              opt4 = ""
            }
          },
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("+ تثبيت السؤال في التحدي")
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.isNotBlank()) {
            val finalQuestions = if (questionsList.isEmpty()) {
              listOf(
                Question(
                  id = "q_default_1",
                  text = "ما هو المعيار المعتمد في التصميم الأكاديمي لتحدي $title؟",
                  options = listOf("المعيار الأكاديمي الشامل", "المعيار التجريبي", "المعيار العشوائي", "لا يوجد معيار"),
                  correctOptionIndex = 0,
                  points = 100
                )
              )
            } else questionsList.toList()

            onCreateChallenge(
              title,
              description.ifBlank { "تحدٍ تدريبي متخصص لطلاب الكلية." },
              skill,
              "تقنية المعلومات والحاسب",
              difficulty,
              duration.toIntOrNull() ?: 20,
              points.toIntOrNull() ?: 500,
              finalQuestions
            )
            onDismiss()
          }
        }
      ) {
        Text("نشر التحدي")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("إلغاء")
      }
    }
  )
}

@Composable
fun AddUserDialog(
  onDismiss: () -> Unit,
  onAddUser: (name: String, email: String, role: UserRole, department: String) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var department by remember { mutableStateOf("تقنية المعلومات") }
  var role by remember { mutableStateOf(UserRole.STUDENT) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("إضافة مستخدم جديد للنظام", fontWeight = FontWeight.Bold) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("الاسم") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("البريد الإلكتروني") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
          value = department,
          onValueChange = { department = it },
          label = { Text("القسم الأكاديمي") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        Text("نوع الحساب والصلاحيات:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          UserRole.values().forEach { r ->
            FilterChip(
              selected = role == r,
              onClick = { role = r },
              label = { Text(r.labelAr, fontSize = 11.sp) }
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank() && email.isNotBlank()) {
            onAddUser(name, email, role, department)
            onDismiss()
          }
        }
      ) {
        Text("إضافة")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("إلغاء")
      }
    }
  )
}

@Composable
fun NotificationsSheet(
  notifications: List<AppNotification>,
  onDismiss: () -> Unit,
  onClearAll: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("الإشعارات والتنبيهات 🔔", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        TextButton(onClick = onClearAll) {
          Text("مسح الكل", fontSize = 12.sp)
        }
      }
    },
    text = {
      if (notifications.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text("لا توجد إشعارات جديدة حالياً")
        }
      } else {
        Column(
          modifier = Modifier.verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          notifications.forEach { notif ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (notif.isRead) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                  Text(notif.timeAgo, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(notif.message, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss) {
        Text("إغلاق")
      }
    }
  )
}

@Composable
fun SettingsDialog(
  isDarkMode: Boolean,
  language: String,
  onToggleDarkMode: () -> Unit,
  onToggleLanguage: () -> Unit,
  onLogout: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("إعدادات المنصة ⚙️", fontWeight = FontWeight.Bold) },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Dark Mode Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("الوضع الداكن (Dark Mode)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(if (isDarkMode) "مفعّل" else "الوضع الفاتح", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Switch(
            checked = isDarkMode,
            onCheckedChange = { onToggleDarkMode() }
          )
        }

        HorizontalDivider()

        // Language Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("لغة التطبيق (Language)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Text(if (language == "ar") "العربية (افتراضي)" else "English", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Button(onClick = onToggleLanguage, shape = RoundedCornerShape(8.dp)) {
            Text(if (language == "ar") "English" else "العربية", fontSize = 11.sp)
          }
        }

        HorizontalDivider()

        // About UniLeague
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text("عن المنصة (UniLeague)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
          Text(
            "منصة دوري المهارات الجامعي - إصدار 1.0.0\nمبنية لدعم المنافسات الأكاديمية والتقنية بين كليات وأقسام الجامعات السعودية.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 16.sp
          )
        }

        HorizontalDivider()

        // Logout
        Button(
          onClick = {
            onDismiss()
            onLogout()
          },
          colors = ButtonDefaults.buttonColors(containerColor = UniRuby),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(Icons.Default.ExitToApp, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("تسجيل الخروج من الحساب")
        }
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("إغلاق")
      }
    }
  )
}
