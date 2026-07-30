// 📁 core/data/local/dao/VehicleDao.kt
package com.irpc.forklift.core.data.local.dao

/**
 * 🚛 Vehicle DAO
 *
 * @Dao
 * interface VehicleDao {
 *     @Query("SELECT * FROM vehicles WHERE department_id IN (:deptIds)")
 *     suspend fun getByDepartments(deptIds: List<String>): List<VehicleEntity>
 *
 *     @Insert(onConflict = OnConflictStrategy.REPLACE)
 *     suspend fun insertAll(vehicles: List<VehicleEntity>)
 *
 *     @Query("DELETE FROM vehicles")
 *     suspend fun clear()
 * }
 */
object VehicleDao {
    // TODO: implement Room DAO
}
