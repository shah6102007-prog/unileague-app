package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
  onLoginSuccess: (UserRole) -> Unit,
  onRegisterSuccess: (
    name: String,
    email: String,
    university: String,
    college: String,
    department: String,
    major: String,
    academicLevel: String,
    role: UserRole
  ) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Login, 1: Register
  val scrollState = rememberScrollState()

  // Login Form States
  var loginEmail by remember { mutableStateOf("shahd.student@ksu.edu.sa") }
  var loginPassword by remember { mutableStateOf("123456") }
  var passwordVisible by remember { mutableStateOf(false) }
  var showForgotPasswordDialog by remember { mutableStateOf(false) }
  var forgotEmail by remember { mutableStateOf("") }
  var forgotSentMessage by remember { mutableStateOf(false) }

  // Register Form States (requested by user)
  var regName by remember { mutableStateOf("") }
  var regEmail by remember { mutableStateOf("") }
  var regPassword by remember { mutableStateOf("") }
  var regUniversity by remember { mutableStateOf("جامعة الملك سعود") }
  var regCollege by remember { mutableStateOf("كلية علوم الحاسب والمعلومات") }
  var regDepartment by remember { mutableStateOf("تقنية المعلومات") }
  var regMajor by remember { mutableStateOf("علوم الحاسب وهندسة البرمجيات") }
  var regAcademicLevel by remember { mutableStateOf("المستوى الخامس - سنة 3") }
  var regRole by remember { mutableStateOf(UserRole.STUDENT) }
  var regErrorMessage by remember { mutableStateOf<String?>(null) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "UniLeague | بوابة الدخول",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { padding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(padding)
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Demo Quick Login Callout
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Stars,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "دخول سريع للحسابات التجريبية (Demo)",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          }
          Text(
            text = "اختر دورك لتجربة صلاحيات ومزايا النظام مباشرة:",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { onLoginSuccess(UserRole.STUDENT) },
              modifier = Modifier
                .weight(1f)
                .testTag("demo_student_btn"),
              shape = RoundedCornerShape(10.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
            ) {
              Text("🎓 طالب", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Button(
              onClick = { onLoginSuccess(UserRole.TEACHER) },
              modifier = Modifier
                .weight(1f)
                .testTag("demo_teacher_btn"),
              colors = ButtonDefaults.buttonColors(containerColor = UniGold),
              shape = RoundedCornerShape(10.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
            ) {
              Text("👨‍🏫 دكتور", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Button(
              onClick = { onLoginSuccess(UserRole.ADMIN) },
              modifier = Modifier
                .weight(1f)
                .testTag("demo_admin_btn"),
              colors = ButtonDefaults.buttonColors(containerColor = UniPurple),
              shape = RoundedCornerShape(10.dp),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
            ) {
              Text("🏛️ إدارة", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Tab Switcher
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("تسجيل الدخول", fontWeight = FontWeight.Bold) },
          modifier = Modifier.testTag("tab_login")
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("إنشاء حساب جديد", fontWeight = FontWeight.Bold) },
          modifier = Modifier.testTag("tab_register")
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      if (selectedTab == 0) {
        // --- LOGIN TAB ---
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          OutlinedTextField(
            value = loginEmail,
            onValueChange = { loginEmail = it },
            label = { Text("البريد الإلكتروني الجامعي") },
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("login_email_input"),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = loginPassword,
            onValueChange = { loginPassword = it },
            label = { Text("كلمة المرور") },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
            trailingIcon = {
              IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                  imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                  contentDescription = null
                )
              }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("login_password_input"),
            shape = RoundedCornerShape(12.dp)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            TextButton(onClick = { showForgotPasswordDialog = true }) {
              Text("نسيت كلمة المرور؟", fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
            }
          }

          Button(
            onClick = { onLoginSuccess(UserRole.STUDENT) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("login_submit_btn")
          ) {
            Text("تسجيل الدخول", fontSize = 15.sp, fontWeight = FontWeight.Bold)
          }
        }
      } else {
        // --- REGISTER TAB ---
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          // Role selection
          Text(
            text = "اختر نوع الحساب:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            UserRole.values().forEach { role ->
              FilterChip(
                selected = regRole == role,
                onClick = { regRole = role },
                label = { Text(role.labelAr, fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
              )
            }
          }

          OutlinedTextField(
            value = regName,
            onValueChange = { regName = it },
            label = { Text("الاسم الكامل") },
            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("reg_name_input"),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regEmail,
            onValueChange = { regEmail = it },
            label = { Text("البريد الإلكتروني الجامعي") },
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("reg_email_input"),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regPassword,
            onValueChange = { regPassword = it },
            label = { Text("كلمة المرور (مشفّرة)") },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("reg_password_input"),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regUniversity,
            onValueChange = { regUniversity = it },
            label = { Text("الجامعة") },
            leadingIcon = { Icon(Icons.Outlined.School, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regCollege,
            onValueChange = { regCollege = it },
            label = { Text("الكلية") },
            leadingIcon = { Icon(Icons.Outlined.Business, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regDepartment,
            onValueChange = { regDepartment = it },
            label = { Text("القسم") },
            leadingIcon = { Icon(Icons.Outlined.Domain, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regMajor,
            onValueChange = { regMajor = it },
            label = { Text("التخصص الأكاديمي") },
            leadingIcon = { Icon(Icons.Outlined.AutoAwesome, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = regAcademicLevel,
            onValueChange = { regAcademicLevel = it },
            label = { Text("المستوى الدراسي") },
            leadingIcon = { Icon(Icons.Outlined.Grade, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          if (regErrorMessage != null) {
            Text(
              text = regErrorMessage!!,
              color = UniRuby,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
          }

          Button(
            onClick = {
              if (regName.isBlank() || regEmail.isBlank()) {
                regErrorMessage = "يرجى تعبئة الاسم والبريد الإلكتروني"
              } else {
                regErrorMessage = null
                onRegisterSuccess(
                  regName,
                  regEmail,
                  regUniversity,
                  regCollege,
                  regDepartment,
                  regMajor,
                  regAcademicLevel,
                  regRole
                )
              }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("reg_submit_btn")
          ) {
            Text("إنشاء الحساب وبدء الدوري", fontSize = 15.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }

  // Forgot Password Dialog
  if (showForgotPasswordDialog) {
    AlertDialog(
      onDismissRequest = {
        showForgotPasswordDialog = false
        forgotSentMessage = false
      },
      title = { Text("استعادة كلمة المرور", fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          if (!forgotSentMessage) {
            Text("أدخل بريدك الجامعي لإرسال رابط إعادة تعيين كلمة المرور الآمن:")
            OutlinedTextField(
              value = forgotEmail,
              onValueChange = { forgotEmail = it },
              label = { Text("البريد الإلكتروني") },
              singleLine = true,
              modifier = Modifier.fillMaxWidth()
            )
          } else {
            Text(
              text = "✅ تم إرسال رابط التحقق وإعادة التعيين إلى بريدك الإلكتروني بنجاح.",
              color = UniEmerald,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      },
      confirmButton = {
        if (!forgotSentMessage) {
          Button(onClick = { forgotSentMessage = true }) {
            Text("إرسال الرابط")
          }
        } else {
          Button(onClick = {
            showForgotPasswordDialog = false
            forgotSentMessage = false
          }) {
            Text("حسناً")
          }
        }
      },
      dismissButton = {
        TextButton(onClick = {
          showForgotPasswordDialog = false
          forgotSentMessage = false
        }) {
          Text("إلغاء")
        }
      }
    )
  }
}
