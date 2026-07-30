// 📁 core/data/local/dao/DepartmentDao.kt
package com.irpc.forklift.core.data.local.dao

/**
 * 🏭 Department DAO
 *
 * @Dao
 * interface DepartmentDao {
 *     @Query("SELECT * FROM departments ORDER BY sortOrder")
 *     suspend fun getAll(): List<DepartmentEntity>
 *
 *     @Insert(onConflict = OnConflictStrategy.REPLACE)
 *     suspend fun insertAll(departments: List<DepartmentEntity>)
 *
 *     @Query("DELETE FROM departments")
 *     suspend fun clear()
 * }
 */
object DepartmentDao {
    // TODO: implement Room DAO
}
