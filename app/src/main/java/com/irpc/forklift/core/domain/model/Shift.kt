// 📁 core/domain/model/Shift.kt
package com.irpc.forklift.core.domain.model

/**
 * ⏰ Shift Model
 *
 * ShiftCode: "M" | "E" | "N"
 *
 * ⏱️ กะตามช่วงเวลา (ใช้บอก user ว่า "ตอนนี้เป็นเวลากะไหน"):
 *   M = 06:00 - 14:00 น.  (Morning)
 *   E = 14:00 - 22:00 น.  (Evening)
 *   N = 22:00 - 06:00 น.  (Night)
 *
 * 📅 "ตารางเวร" (8 วัน loop) — ใช้บอกว่า "ทีมไหนเข้าบรรวันนี้":
 *   SHIFT_CYCLE = ['M','M','E','E','N','N','O','O']
 *   idx = (diffDays + teamOffset) % 8 → SHIFT_CYCLE[idx]
 */
enum class ShiftCode(
    val label: String,
    val labelEn: String,
    val timeRange: String,
) {
    M("เช้า", "Morning", "06:00-14:00 น."),
    E("บ่าย", "Evening", "14:00-22:00 น."),
    N("ดึก", "Night", "22:00-06:00 น."),
}

// / กะของทีมหนึ่งๆ ในวันหนึ่ง (shift = null หมายถึง Off/วันหยุด)
data class TeamShift(
    val teamId: String, // "A" | "B" | "C" | "D"
    val teamName: String, // "กะ A" | "กะ B" ...
    val shift: ShiftCode?, // M | E | N | null (Off)
    val subIndex: Int = 1, // 1 หรือ 2 — บอกว่าเป็นกะรอบไหน (เช้า 1 / เช้า 2)
)

// / ข้อมูลกะ + ตำแหน่งใน cycle — ใช้คำนวณ "เช้า 1 / เช้า 2" ฯลฯ
data class ShiftDetail(
    val shift: ShiftCode?, // null = Off
    val subIndex: Int, // 1 หรือ 2
)

// / ตารางเวรของวันหนึ่ง — รวมทุกทีม (4 ทีม)
data class TodayShifts(
    val date: String, // "2026-07-30"
    val teams: List<TeamShift>, // ทั้ง 4 ทีม + กะวันนี้
)
