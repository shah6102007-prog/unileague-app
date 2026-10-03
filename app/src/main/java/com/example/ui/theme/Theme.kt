package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = UniGoldLight,
  onPrimary = Color(0xFF0F172A),
  primaryContainer = UniPrimaryVariant,
  onPrimaryContainer = Color(0xFFE2E8F0),
  secondary = UniPrimaryLight,
  onSecondary = Color(0xFF0F172A),
  secondaryContainer = UniDarkSurfaceHigh,
  onSecondaryContainer = Color(0xFFF1F5F9),
  tertiary = UniEmeraldLight,
  onTertiary = Color(0xFF064E3B),
  background = UniDarkBg,
  onBackground = UniDarkTextPrimary,
  surface = UniDarkSurface,
  onSurface = UniDarkTextPrimary,
  surfaceVariant = UniDarkSurfaceHigh,
  onSurfaceVariant = UniDarkTextSecondary,
  error = UniRuby
)

private val LightColorScheme = lightColorScheme(
  primary = UniPrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFDBEAFE),
  onPrimaryContainer = UniPrimaryVariant,
  secondary = UniGold,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFEF3C7),
  onSecondaryContainer = UniGoldDark,
  tertiary = UniEmerald,
  onTertiary = Color.White,
  background = UniLightBg,
  onBackground = UniLightTextPrimary,
  surface = UniLightSurface,
  onSurface = UniLightTextPrimary,
  surfaceVariant = UniLightSurfaceVariant,
  onSurfaceVariant = UniLightTextSecondary,
  error = UniRuby
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep cohesive UniLeague brand identity
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

