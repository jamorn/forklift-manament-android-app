// 📁 core/domain/usecase/checklist/GetPreviousChecksheetUseCase.kt
package com.irpc.forklift.core.domain.usecase.checklist

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import javax.inject.Inject

/**
 * 🔄 Copy-Forward Logic
 *
 * ดึง checksheet ก่อนหน้าของรถคันนี้ (ใน cycle เดียวกัน, shift ก่อนหน้า)
 * เพื่อ copy ผลตรวจมาเป็น default บนฟอร์ม
 *
 * เทียบกับ lib/copy-forward-utils.ts
 */
class GetPreviousChecksheetUseCase @Inject constructor(
    private val checksheetRepository: ChecksheetRepository,
) {
    /**
     * @param chassisNo เลขตัวถังรถ
     * @param currentDate วันที่ปัจจุบัน
     * @param currentShift กะปัจจุบัน ("M" | "E" | "N")
     * @return checksheet ก่อนหน้า หรือ null ถ้าไม่มี
     */
    suspend operator fun invoke(
        chassisNo: String,
        currentDate: String,
        currentShift: String,
    ): Result<DailyChecksheet?> {
        return checksheetRepository.getPreviousChecksheet(
            chassisNo = chassisNo,
            currentDate = currentDate,
            currentShift = currentShift,
        )
    }
}
