package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.UniGold
import com.example.ui.theme.UniPrimary

@Composable
fun OnboardingDialog(
  onDismiss: () -> Unit
) {
  var step by remember { mutableStateOf(0) }

  val steps = listOf(
    Triple(
      "مرحبًا بك في دوري المهارات الجامعي! 🎓",
      "UniLeague يحول تعلمك الأكاديمي والتقني إلى تجربة تفاعلية وبطولات ممتعة.",
      Icons.Default.School
    ),
    Triple(
      "نافس وتصدر ترتيب القسم والجامعة 🏆",
      "حل التحديات، ساهم في رفع ترتيب قسمك، وتألق في منصة التتويج الأكاديمية.",
      Icons.Default.EmojiEvents
    ),
    Triple(
      "شارك في فرق البطولات الجماعية 👥",
      "انضم إلى زملائك في الهاكاثونات ودوري الأمن السيبراني وحقق أوسمة الشرف.",
      Icons.Default.Groups
    )
  )

  val (title, description, icon) = steps[step]

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(UniPrimary.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = UniPrimary,
            modifier = Modifier.size(34.dp)
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          textAlign = TextAlign.Center
        )
      }
    },
    text = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = description,
          fontSize = 13.sp,
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Step Dots Indicator
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          steps.indices.forEach { index ->
            Box(
              modifier = Modifier
                .size(if (index == step) 18.dp else 8.dp, 8.dp)
                .clip(CircleShape)
                .background(if (index == step) UniPrimary else MaterialTheme.colorScheme.surfaceVariant)
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (step < steps.size - 1) {
            step++
          } else {
            onDismiss()
          }
        },
        shape = RoundedCornerShape(10.dp)
      ) {
        Text(if (step < steps.size - 1) "التالي" else "ابدأ التحدي الآن 🚀")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("تخطي")
      }
    }
  )
}
