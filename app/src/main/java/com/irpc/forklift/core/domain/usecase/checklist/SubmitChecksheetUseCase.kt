// 📁 core/domain/usecase/checklist/SubmitChecksheetUseCase.kt
package com.irpc.forklift.core.domain.usecase.checklist

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import javax.inject.Inject

/**
 * ✅ Submit Checksheet
 *
 * - บันทึก checksheet ใหม่
 * - ถ้า offline → queue ไว้ (sync ทีหลัง)
 * - อัปเดต vehicle status ถ้าจำเป็น
 */
class SubmitChecksheetUseCase
    @Inject
    constructor(
        private val checksheetRepository: ChecksheetRepository,
    ) {
        suspend operator fun invoke(checksheet: DailyChecksheet): Result<String> = checksheetRepository.saveChecksheet(checksheet)
    }
