// 📁 core/data/repository/ChecksheetRepositoryImpl.kt
package com.irpc.forklift.core.data.repository

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 📋 Checksheet Repository Implementation
 *
 * - Firestore สำหรับ save/load
 * - Offline queue สำหรับ save ขณะไม่มีเน็ต
 * - Room cache สำหรับ getPreviousChecksheet
 */
@Singleton
class ChecksheetRepositoryImpl
    @Inject
    constructor(
        // private val firestore: FirebaseFirestore,
        // private val checksheetDao: ChecksheetCacheDao,
    ) : ChecksheetRepository {
        override suspend fun getChecksheets(
            date: String,
            shift: String,
            vehicleIds: List<String>,
        ): Result<List<DailyChecksheet>> =
            try {
                // val snapshot = firestore
                //     .collection(AppConstants.COLLECTION_CHECKSHEETS)
                //     .whereEqualTo("date", date)
                //     .whereEqualTo("shift", shift)
                //     .whereIn("chassis_no", vehicleIds)
                //     .get()
                //     .await()
                // val sheets = snapshot.documents.map { it.toObject(DailyChecksheet::class.java) }
                Result.success(emptyList()) // TODO: implement
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getPreviousChecksheet(
            chassisNo: String,
            currentDate: String,
            currentShift: String,
        ): Result<DailyChecksheet?> {
            // TODO: query checksheet ก่อนหน้า ของรถคันนี้
            return Result.success(null)
        }

        override suspend fun saveChecksheet(checksheet: DailyChecksheet): Result<String> =
            try {
                // val docRef = firestore
                //     .collection(AppConstants.COLLECTION_CHECKSHEETS)
                //     .document()
                // docRef.set(checksheet).await()
                // Result.success(docRef.id)
                Result.success("mock-id") // TODO: implement
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun syncPendingChecksheets(): Result<Int> {
            // TODO: ดึงจาก offline queue → อัปโหลด
            return Result.success(0)
        }
    }
