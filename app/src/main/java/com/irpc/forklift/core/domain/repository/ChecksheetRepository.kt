// 📁 core/domain/repository/ChecksheetRepository.kt
package com.irpc.forklift.core.domain.repository

import com.irpc.forklift.core.domain.model.DailyChecksheet

/**
 * 📋 Checksheet Repository Interface
 */
interface ChecksheetRepository {
    /** ดึง checksheet ของวัน/กะ */
    suspend fun getChecksheets(
        date: String,
        shift: String,
        vehicleIds: List<String>,
    ): Result<List<DailyChecksheet>>

    /** ดึง checksheet ก่อนหน้า (สำหรับ Copy-Forward) */
    suspend fun getPreviousChecksheet(
        chassisNo: String,
        currentDate: String,
        currentShift: String,
    ): Result<DailyChecksheet?>

    /** บันทึก checksheet (Firestore + Offline cache) */
    suspend fun saveChecksheet(checksheet: DailyChecksheet): Result<String>

    /** อัปโหลด checksheet ที่ค้าง offline */
    suspend fun syncPendingChecksheets(): Result<Int>
}
