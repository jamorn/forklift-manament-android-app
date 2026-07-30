// 📁 ui/theme/Type.kt
package com.irpc.forklift.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 🎨 Forklift Typography
 */
val ForkliftTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.Black, fontSize = 32.sp),
    displayMedium = TextStyle(fontWeight = FontWeight.Black, fontSize = 28.sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    titleSmall = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 0.5.sp),
)
