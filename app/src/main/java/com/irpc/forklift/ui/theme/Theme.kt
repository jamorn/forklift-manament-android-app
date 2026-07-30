// 📁 ui/theme/Theme.kt
package com.irpc.forklift.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * 🎨 Forklift Theme — Dark Mode Only (Matches Web UI)
 */
private val DarkColorScheme = darkColorScheme(
    primary = ForkliftColors.Accent,
    onPrimary = ForkliftColors.TextPrimary,
    primaryContainer = ForkliftColors.AccentBg,
    secondary = ForkliftColors.AccentLight,
    background = ForkliftColors.BgPrimary,
    surface = ForkliftColors.BgCard,
    surfaceVariant = ForkliftColors.BgSecondary,
    onBackground = ForkliftColors.TextPrimary,
    onSurface = ForkliftColors.TextPrimary,
    onSurfaceVariant = ForkliftColors.TextSecondary,
    outline = ForkliftColors.Border,
    error = ForkliftColors.Danger,
    errorContainer = ForkliftColors.DangerBg,
)

@Composable
fun ForkliftTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = ForkliftTypography,
        shapes = ForkliftShapes,
        content = content,
    )
}
