package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ChallengeSession
import com.example.ui.theme.UniEmerald
import com.example.ui.theme.UniGold
import com.example.ui.theme.UniRuby

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeActiveScreen(
  session: ChallengeSession,
  onSelectOption: (Int) -> Unit,
  onNext: () -> Unit,
  onPrev: () -> Unit,
  onSubmit: () -> Unit,
  onCancel: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showExitConfirmDialog by remember { mutableStateOf(false) }
  var showSubmitConfirmDialog by remember { mutableStateOf(false) }

  val currentQuestion = session.questions.getOrNull(session.currentQuestionIndex)
  val selectedOption = session.selectedAnswers[session.currentQuestionIndex]

  val minutes = session.remainingSeconds / 60
  val seconds = session.remainingSeconds % 60
  val timerFormatted = "%02d:%02d".format(minutes, seconds)
  val isLowTime = session.remainingSeconds < 120

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = session.challenge.title,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
            Text(
              text = "السؤال ${session.currentQuestionIndex + 1} من ${session.questions.size}",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        actions = {
          // Live Countdown Timer Badge
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isLowTime) UniRuby.copy(alpha = 0.15f) else UniGold.copy(alpha = 0.15f),
            modifier = Modifier.padding(end = 8.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = null,
                tint = if (isLowTime) UniRuby else UniGold,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = timerFormatted,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isLowTime) UniRuby else UniGold
              )
            }
          }

          // Close / Exit button
          IconButton(onClick = { showExitConfirmDialog = true }) {
            Icon(Icons.Default.Close, contentDescription = "Exit Challenge")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      // Navigation & Submit Bottom Bar
      Surface(
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Prev Button
          OutlinedButton(
            onClick = onPrev,
            enabled = session.currentQuestionIndex > 0,
            shape = RoundedCornerShape(10.dp)
          ) {
            Text("السابق")
          }

          // Answered counter
          Text(
            text = "أجبت ${session.selectedAnswers.size}/${session.questions.size}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          // Next or Submit Button
          if (session.currentQuestionIndex < session.questions.size - 1) {
            Button(
              onClick = onNext,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("challenge_next_btn")
            ) {
              Text("التالي")
            }
          } else {
            Button(
              onClick = { showSubmitConfirmDialog = true },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = UniEmerald),
              modifier = Modifier.testTag("challenge_submit_btn")
            ) {
              Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("إنهاء التحدي")
            }
          }
        }
      }
    }
  ) { padding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(padding)
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Progress indicator bar
      LinearProgressIndicator(
        progress = { (session.currentQuestionIndex + 1).toFloat() / session.questions.size },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
      )

      // Anti-Cheat & Instructions Notice
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Outlined.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
          Text(
            text = "نظام النزاهة الأكاديمي: الأسئلة والخيارات مرتبة عشوائيًا. سينتهي التحدي تلقائيًا عند نفاد المؤقت.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      if (currentQuestion != null) {
        // Question Card
        Card(
          shape = RoundedCornerShape(16.dp),
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
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
              ) {
                Text(
                  text = "سؤال ${session.currentQuestionIndex + 1}",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
              Text(
                text = "+${currentQuestion.points} نقطة",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = UniEmerald
              )
            }

            Text(
              text = currentQuestion.text,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              lineHeight = 24.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        // Options List (Selectable Cards)
        Text(
          text = "اختر الإجابة الصحيحة:",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        currentQuestion.options.forEachIndexed { optIndex, optionText ->
          val isSelected = selectedOption == optIndex
          val cardBorderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
          val cardBgColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface

          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = cardBgColor),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
            modifier = Modifier
              .fillMaxWidth()
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(14.dp)
              )
              .clip(RoundedCornerShape(14.dp))
              .clickable { onSelectOption(optIndex) }
              .testTag("option_${session.currentQuestionIndex}_$optIndex")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                  ),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = ('أ' + optIndex).toString(),
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
              Text(
                text = optionText,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
              )
              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }
        }
      }
    }
  }

  // Submit Confirmation Dialog
  if (showSubmitConfirmDialog) {
    val unansweredCount = session.questions.size - session.selectedAnswers.size
    AlertDialog(
      onDismissRequest = { showSubmitConfirmDialog = false },
      title = { Text("تأكيد إنهاء التحدي", fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          if (unansweredCount > 0) {
            Text("تنبيه: لديك $unansweredCount أسئلة لم تقم بالإجابة عليها بعد. هل تريد الإرسال الآن؟")
          } else {
            Text("هل أنت متأكد من إنهاء التحدي وتسليم الإجابات لاحتساب النقاط وتحديث المستوى؟")
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showSubmitConfirmDialog = false
            onSubmit()
          },
          colors = ButtonDefaults.buttonColors(containerColor = UniEmerald)
        ) {
          Text("تأكيد التسليم")
        }
      },
      dismissButton = {
        TextButton(onClick = { showSubmitConfirmDialog = false }) {
          Text("متابعة الحل")
        }
      }
    )
  }

  // Exit Confirmation Dialog
  if (showExitConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showExitConfirmDialog = false },
      title = { Text("مغادرة التحدي؟", fontWeight = FontWeight.Bold) },
      text = {
        Text("إذا خرجت الآن فستفقد تقدمك ولن يتم احتساب النقاط.")
      },
      confirmButton = {
        Button(
          onClick = {
            showExitConfirmDialog = false
            onCancel()
          },
          colors = ButtonDefaults.buttonColors(containerColor = UniRuby)
        ) {
          Text("مغادرة")
        }
      },
      dismissButton = {
        TextButton(onClick = { showExitConfirmDialog = false }) {
          Text("البقاء بالتحدي")
        }
      }
    )
  }
}
