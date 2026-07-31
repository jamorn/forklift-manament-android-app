// 📁 core/common/constants/ShiftConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * ⏰ Shift Cycle Constants (8-Day Cycle)
 * เทียบกับ shift.ts ในเว็บ
 *
 * กำหนด "ตารางเวร" ของแต่ละทีม
 * getShift(teamOffset, targetDate) จะคำนวณว่า
 * แต่ละทีม (A/B/C/D) วันนี้ต้องมากะอะไร (M/E/N) หรือวันหยุด (O)
 */
object ShiftConstants {
    // Cycle 8 วัน (ลำดับตายตัว): M,M,E,E,N,N,O,O
    const val CYCLE_LENGTH = 8

    // ลำดับกะในรอบ 8 วัน: index 0 = M, 1 = M, 2 = E, 3 = E, 4 = N, 5 = N, 6 = O, 7 = O
    val SHIFT_CYCLE = arrayOf("M", "M", "E", "E", "N", "N", "O", "O")

    // Base Date — เริ่มต้นรอบแรก (ตาม ref: new Date(2026, 0, 1))
    const val BASE_DATE = "2026-01-01"

    // แต่ละทีมมี offset ต่างกัน → ทำให้ 4 ทีมหมุนเวียนไม่ชนกันในวันเดียวกัน
    data class Team(
        val id: String, // "A" | "B" | "C" | "D"
        val name: String, // "กะ A" | "กะ B" ...
        val offset: Int, // เอาไปคำนวณ idx = (diffDays + offset) % 8
    )

    val TEAMS =
        listOf(
            Team("A", "กะ A", 3),
            Team("B", "กะ B", 5),
            Team("C", "กะ C", 7),
            Team("D", "กะ D", 1),
        )
}
