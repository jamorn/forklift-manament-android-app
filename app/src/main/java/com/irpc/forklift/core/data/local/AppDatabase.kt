// 📁 core/data/local/AppDatabase.kt
package com.irpc.forklift.core.data.local

/**
 * 🗄️ Room Database
 *
 * @Database(
 *     entities = [
 *         DepartmentEntity::class,
 *         VehicleEntity::class,
 *         ChecksheetEntity::class,
 *     ],
 *     version = 1,
 * )
 */
object AppDatabase {
    // companion object {
    //     @Volatile private var INSTANCE: AppDatabase? = null
    //
    //     fun getInstance(context: Context): AppDatabase {
    //         return INSTANCE ?: synchronized(this) {
    //             Room.databaseBuilder(
    //                 context.applicationContext,
    //                 AppDatabase::class.java,
    //                 "forklift_db"
    //             ).build().also { INSTANCE = it }
    //         }
    //     }
    // }
}
