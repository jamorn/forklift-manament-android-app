// 📁 core/domain/usecase/checklist/GetPreviousChecksheetUseCase.kt
package com.irpc.forklift.core.domain.usecase.checklist

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import com.irpc.forklift.core.common.utils.DateUtils
import javax.inject.Inject

/**
 * 🔄 Copy-Forward Logic
 *
 * ดึง checksheet ก่อนหน้าของรถคันนี้ (ตามรอบกะ M/E/N — ดู Docs/18)
 * เพื่อ copy ผลตรวจมาเป็น default บนฟอร์ม
 *
 * แผนผัง "กะก่อนหน้า":
 *   M (เช้า)   → ก่อนหน้า = N (ดึก) ของเมื่อวาน (currentDate - 1)
 *   E (บ่าย)   → ก่อนหน้า = M (เช้า) ของวันนี้
 *   N (ดึก)    → ก่อนหน้า = E (บ่าย) ของวันนี้
 *
 * ⭐ (getWorkDate จัดการกะข้ามคืนให้ถูกต้อง — หลังเที่ยงคืนของกะ N = เมื่อวาน)
 */
class GetPreviousChecksheetUseCase
    @Inject
    constructor(
        private val checksheetRepository: ChecksheetRepository,
    ) {
        /**
         * @param chassisNo เลขตัวถังรถ
         * @param currentDate วันทำงาน (work date) ของกะปัจจุบันแบบ yyyy-MM-dd
         * @param currentShift กะปัจจุบัน ("M" | "E" | "N")
         * @return checksheet ก่อนหน้า หรือ null ถ้าไม่มี (กะก่อนไม่ได้ตรวจ)
         */
        suspend operator fun invoke(
            chassisNo: String,
            currentDate: String,
            currentShift: String,
        ): Result<DailyChecksheet?> {
            val (prevDate, prevShift) = resolvePredecessor(currentDate, currentShift)
            return checksheetRepository.getPreviousChecksheet(
                chassisNo = chassisNo,
                prevDate = prevDate,
                prevShift = prevShift,
            )
        }

        /**
         * คำนวณ "กะก่อนหน้า" + "วันที่ต้องไปถาม" ตามตารางกะ
         */
        private fun resolvePredecessor(
            currentDate: String,
            currentShift: String,
        ): Pair<String, String> =
            when (currentShift) {
                // M (เช้า) → ก่อนหน้า N (ดึก) ของเมื่อวาน
                "M" -> DateUtils.toFirestoreString(DateUtils.parseDate(currentDate).minusDays(1)) to "N"
                // E (บ่าย) → ก่อนหน้า M (เช้า) ของวันนี้
                "E" -> currentDate to "M"
                // N (ดึก) → ก่อนหน้า E (บ่าย) ของวันนี้
                "N" -> currentDate to "E"
                // fail-fast: ถ้า shift ผิด/ไม่รู้จัก (ควรไม่มีวันเกิด เพราะ ShiftCode มีแค่ M/E/N)
                // → โผล่ error ทันที แทนที่จะหา doc กะที่ผิดเงียบ ๆ
                else -> throw IllegalArgumentException("Unknown shift: $currentShift")
            }
    }
