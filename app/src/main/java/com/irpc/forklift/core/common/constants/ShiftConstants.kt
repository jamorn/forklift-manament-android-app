// 📁 core/common/constants/ShiftConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * ⏰ Shift Cycle Constants (8-Day Cycle)
 * เทียบกับ shift.ts ในเว็บ
 */
object ShiftConstants {
    // Cycle 8 วัน: day 0-2 = M, day 3-5 = E, day 6-7 = N
    const val CYCLE_LENGTH = 8
    const val MORNING_SHIFT_COUNT = 3   // day 0,1,2
    const val EVENING_SHIFT_COUNT = 3   // day 3,4,5
    const val NIGHT_SHIFT_COUNT = 2     // day 6,7

    // Epoch Reference — วันที่เริ่มนับ Cycle แรก
    const val CYCLE_EPOCH = "2026-07-01"

    // Team Colors (สำหรับ UI)
    val TEAM_COLORS = mapOf(
        "M" to "#10B981",   // emerald
        "E" to "#F59E0B",   // amber
        "N" to "#6366F1",   // indigo
    )
}
