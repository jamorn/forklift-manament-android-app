// 📁 core/data/local/dao/ChecksheetCacheDao.kt
package com.irpc.forklift.core.data.local.dao

/**
 * 📋 Checksheet Cache DAO (Offline Queue + Cache)
 *
 * @Dao
 * interface ChecksheetCacheDao {
 *     @Query("SELECT * FROM checksheet_cache ORDER BY created_at DESC LIMIT 1")
 *     suspend fun getLatest(): ChecksheetEntity?
 *
 *     @Query("SELECT * FROM checksheet_cache WHERE chassis_no = :chassisNo ORDER BY created_at DESC")
 *     suspend fun getByChassis(chassisNo: String): List<ChecksheetEntity>
 *
 *     @Insert(onConflict = OnConflictStrategy.REPLACE)
 *     suspend fun insert(checksheet: ChecksheetEntity)
 *
 *     @Query("DELETE FROM checksheet_cache WHERE synced = 1")
 *     suspend fun deleteSynced()
 * }
 */
object ChecksheetCacheDao {
    // TODO: implement Room DAO
}
