// 📁 ui/theme/Color.kt
package com.irpc.forklift.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 🎨 Color Palette — Forklift Theme
 * เทียบกับ app/globals.css CSS Variables
 */
object ForkliftColors {
    // Primary / Accent
    val Accent = Color(0xFF6366F1) // indigo-500
    val AccentBg = Color(0x1A6366F1) // indigo-500/10
    val AccentLight = Color(0xFF818CF8) // indigo-400

    // Background
    val BgPrimary = Color(0xFF0F172A) // slate-900
    val BgSecondary = Color(0xFF1E293B) // slate-800
    val BgCard = Color(0xFF1E293B) // slate-800
    val BgElevated = Color(0xFF334155) // slate-700

    // Text
    val TextPrimary = Color(0xFFF1F5F9) // slate-100
    val TextSecondary = Color(0xFF94A3B8) // slate-400
    val TextMuted = Color(0xFF64748B) // slate-500

    // Border
    val Border = Color(0xFF334155) // slate-700

    // Status Colors
    val Success = Color(0xFF10B981) // emerald-500
    val SuccessBg = Color(0x1A10B981) // emerald-500/10
    val Warning = Color(0xFFF59E0B) // amber-500
    val WarningBg = Color(0x1AF59E0B) // amber-500/10
    val Danger = Color(0xFFEF4444) // red-500
    val DangerBg = Color(0x1AEF4444) // red-500/10
    val Info = Color(0xFF3B82F6) // blue-500
    val InfoBg = Color(0x1A3B82F6) // blue-500/10

    // Badge Colors
    val BadgeSa = Color(0xFF6366F1) // indigo
    val BadgeAdmin = Color(0xFF10B981) // emerald
    val BadgeOperator = Color(0xFFF59E0B) // amber
}
