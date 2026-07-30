// 📁 core/domain/model/Shift.kt
package com.irpc.forklift.core.domain.model

/**
 * ⏰ Shift Model
 * 
 * ShiftCode: "M" | "E" | "N"
 * 
 * Shift Cycle (8 วัน):
 *   day 0-2 → M (Morning)
 *   day 3-5 → E (Evening)
 *   day 6-7 → N (Night)
 */
enum class ShiftCode(val label: String, val labelEn: String) {
    M("เช้า", "Morning"),
    E("บ่าย", "Evening"),
    N("กลางคืน", "Night"),
}

data class ShiftResult(
    val shift: ShiftCode,
    val cycleDay: Int,           // 0-7
    val team: String,            // "A" | "B" | "C" | "D"
)

data class TeamShiftInfo(
    val team: String,
    val currentShift: ShiftCode,
    val todayCycleDay: Int,
)
