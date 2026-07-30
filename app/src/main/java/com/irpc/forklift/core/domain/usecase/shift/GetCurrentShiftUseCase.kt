// 📁 core/domain/usecase/shift/GetCurrentShiftUseCase.kt
package com.irpc.forklift.core.domain.usecase.shift

import com.irpc.forklift.core.common.constants.ShiftConstants
import com.irpc.forklift.core.common.utils.DateUtils
import com.irpc.forklift.core.domain.model.ShiftCode
import com.irpc.forklift.core.domain.model.ShiftResult
import com.irpc.forklift.core.domain.model.TeamShiftInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * ⏰ คำนวณ Shift ปัจจุบัน (Cycle 8 วัน)
 *
 * เทียบกับ useGetCurrentShift() ใน shift.ts
 */
class GetCurrentShiftUseCase {

    operator fun invoke(): ShiftResult {
        val today = LocalDate.now()
        val epoch = LocalDate.parse(ShiftConstants.CYCLE_EPOCH)
        val daysSinceEpoch = ChronoUnit.DAYS.between(epoch, today).toInt()
        val cycleDay = ((daysSinceEpoch % ShiftConstants.CYCLE_LENGTH) + ShiftConstants.CYCLE_LENGTH) % ShiftConstants.CYCLE_LENGTH

        val shift = when {
            cycleDay < ShiftConstants.MORNING_SHIFT_COUNT -> ShiftCode.M
            cycleDay < ShiftConstants.MORNING_SHIFT_COUNT + ShiftConstants.EVENING_SHIFT_COUNT -> ShiftCode.E
            else -> ShiftCode.N
        }

        // Team: A (M), B (E), C (N), D (หมุน)
        val team = when (shift) {
            ShiftCode.M -> "A"
            ShiftCode.E -> "B"
            ShiftCode.N -> "C"
        }

        return ShiftResult(shift = shift, cycleDay = cycleDay, team = team)
    }

    /** ดึงข้อมูล Team + Shift สำหรับ UI */
    fun getTeamShiftInfo(): TeamShiftInfo {
        val result = invoke()
        return TeamShiftInfo(
            team = result.team,
            currentShift = result.shift,
            todayCycleDay = result.cycleDay,
        )
    }
}
