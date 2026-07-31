// 📁 core/domain/model/Department.kt
package com.irpc.forklift.core.domain.model

/**
 * 🏭 Department — รวม scope logic
 *
 * โหลดจาก Firestore (collection "departments") หรือใช้ Fallback (DepartmentConstants)
 */
data class Department(
    val id: String, // "dept-bagging-pp12"
    val name: String, // "PP12"
    val parentGroup: String, // "dept-bagging"
    val sortOrder: Int, // ลำดับสำหรับจัดเรียง UI
)

/** Parent group helpers */
fun Department.isBagging(): Boolean = parentGroup == "dept-bagging"

fun Department.isWarehouse(): Boolean = parentGroup == "dept-warehouse"
