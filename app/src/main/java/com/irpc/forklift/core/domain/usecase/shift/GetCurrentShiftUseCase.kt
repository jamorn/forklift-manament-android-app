package com.irpc.forklift.core.domain.usecase.shift

import com.irpc.forklift.core.common.constants.ShiftConstants
import com.irpc.forklift.core.common.utils.DateUtils
import com.irpc.forklift.core.domain.model.ShiftCode
import com.irpc.forklift.core.domain.model.TeamShift
import com.irpc.forklift.core.domain.model.TodayShifts
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * ⏰ ระบบกะ (Shift) ของ Forklift
 *
 * มี 2 concepts ที่แยกกันชัดเจน:
 *
 * 1) 📅 ตารางเวร (8 วัน loop) — "ทีมไหนเข้าบรรวันนี้" (M/E/N/O)
 *    ใช้แสดงผลให้เห็นว่าระบบอิงวันทำงานตามรอบเวร
 *
 * 2) ⏱️ กะตามช่วงเวลา (clock time) — "ตอนนี้เป็นเวลากะไหน" (M/E/N)
 *    ใช้บอก user ว่าช่วงเวลาปัจจุบันเป็นกะอะไร (06-14=M, 14-22=E, 22-06=N)
 *    — ใช้บันทึกลง DailyChecksheet.shift + แสดงให้ user เห็นตอน OT ควบกะ
 */
class GetCurrentShiftUseCase
    @Inject
    constructor() {
        // --------------------------------------------------------------
        // 1) 📅 ตารางเวร (8 วัน loop) — บอกว่า "ทีมไหนอยู่กะอะไรวันนี้"
        // --------------------------------------------------------------

        /** คำนวณตารางเวรของวันนี้ (ทุกทีม A/B/C/D) */
        fun getTodayShifts(targetDate: LocalDate = LocalDate.now()): TodayShifts {
            val teams =
                ShiftConstants.TEAMS.map { team ->
                    val code = getShiftCodeForTeam(team.offset, targetDate)
                    TeamShift(
                        teamId = team.id,
                        teamName = team.name,
                        shift = code, // null = Off/วันหยุด
                    )
                }
            return TodayShifts(
                date = targetDate.format(DateUtils.getDbFormatter()),
                teams = teams,
            )
        }

        /** คำนวณกะของทีมหนึ่ง (offset) ประจำวัน targetDate — คืน null ถ้า Off */
        fun getShiftForTeam(
            teamId: String,
            targetDate: LocalDate = LocalDate.now(),
        ): ShiftCode? {
            val team = ShiftConstants.TEAMS.firstOrNull { it.id == teamId } ?: return null
            return getShiftCodeForTeam(team.offset, targetDate)
        }

        private fun getShiftCodeForTeam(
            teamOffset: Int,
            targetDate: LocalDate,
        ): ShiftCode? {
            val base = LocalDate.parse(ShiftConstants.BASE_DATE)
            val diffDays = ChronoUnit.DAYS.between(base, targetDate).toInt()
            val idx = ((diffDays + teamOffset) % ShiftConstants.CYCLE_LENGTH)
            val code = ShiftConstants.SHIFT_CYCLE[if (idx < 0) idx + ShiftConstants.CYCLE_LENGTH else idx]
            return when (code) {
                "M" -> ShiftCode.M
                "E" -> ShiftCode.E
                "N" -> ShiftCode.N
                else -> null // "O" = Off/วันหยุด
            }
        }

        // --------------------------------------------------------------
        // 2) ⏱️ กะตามช่วงเวลา (clock time) — บอกว่า "ตอนนี้เป็นเวลากะไหน"
        // --------------------------------------------------------------

        /**
         * คำนวณกะตามช่วงเวลาปัจจุบัน (06-14=M, 14-22=E, 22-06=N)
         *
         * ใช้บันทึกลง `DailyChecksheet.shift` และ `shift_order`
         * ตรงกับที่ web ใช้: timeRange M=06:00-14:00, E=14:00-22:00, N=22:00-06:00
         */
        fun getShiftByTime(time: LocalTime = LocalTime.now()): ShiftCode =
            when {
                // 22:00 - 05:59 → N (กลางคืน)
                time.hour >= 22 || time.hour < 6 -> ShiftCode.N

                // 06:00 - 13:59 → M (เช้า)
                time.hour < 14 -> ShiftCode.M

                // 14:00 - 21:59 → E (บ่าย)
                else -> ShiftCode.E
            }

        /** shift_order: M=1, E=2, N=3 */
        fun getShiftOrder(shift: ShiftCode): Int =
            when (shift) {
                ShiftCode.M -> 1
                ShiftCode.E -> 2
                ShiftCode.N -> 3
            }
    }
