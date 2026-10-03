package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SplashScreen(
  onStartClick: () -> Unit,
  onDemoStudentClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val scale by infiniteTransition.animateFloat(
    initialValue = 0.96f,
    targetValue = 1.04f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "scale"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            UniPrimaryVariant,
            UniPrimary,
            UniDarkBg
          )
        )
      )
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      Spacer(modifier = Modifier.weight(1f))

      // Logo Icon with subtle glowing ring
      Box(
        modifier = Modifier
          .scale(scale)
          .size(110.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(
                UniGoldLight,
                UniGold,
                UniGoldDark
              )
            )
          )
          .padding(4.dp),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(UniPrimaryVariant),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "UniLeague Trophy",
            tint = UniGoldLight,
            modifier = Modifier.size(58.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Title & Slogan
      Text(
        text = "UniLeague",
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        letterSpacing = 1.5.sp
      )

      Text(
        text = "دوري المهارات الجامعي",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = UniGoldLight
      )

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "المنصة الجامعية التنافسية لتحويل المهارات الأكاديمية والتقنية إلى بطولات وتحديات تفاعلية",
        fontSize = 14.sp,
        color = Color(0xFFCBD5E1),
        textAlign = TextAlign.Center,
        lineHeight = 22.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(28.dp))

      // Feature Badges
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White.copy(alpha = 0.12f)
        ) {
          Text(
            text = "⚡ تحديات حية",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White.copy(alpha = 0.12f)
        ) {
          Text(
            text = "🏆 بطولات أقسام",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White.copy(alpha = 0.12f)
        ) {
          Text(
            text = "🤖 توصيات ذكية",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.weight(1.2f))

      // Primary Start Button
      Button(
        onClick = onStartClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = UniGoldLight,
          contentColor = Color(0xFF0F172A)
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("splash_start_button")
      ) {
        Text(
          text = "تسجيل الدخول / إنشاء حساب",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // One-click Demo Button
      OutlinedButton(
        onClick = onDemoStudentClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = Color.White
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("splash_demo_button")
      ) {
        Icon(
          imageVector = Icons.Default.Stars,
          contentDescription = null,
          tint = UniGoldLight,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "دخول سريع بتجربة الطالب (شهد 👋)",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
