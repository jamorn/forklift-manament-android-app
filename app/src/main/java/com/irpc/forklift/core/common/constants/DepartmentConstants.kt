// 📁 core/common/constants/DepartmentConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * 🏭 Department Constants (Fallback)
 * ข้อมูลหลักมาจาก Firestore — ค่านี้ใช้ตอน local dev เท่านั้น
 *
 * struct:
 *   deptId → parentGroup
 *   "dept-bagging-pp12" → "dept-bagging"
 *   "dept-warehouse-wh40" → "dept-warehouse"
 */
object DepartmentConstants {
    // Group parent
    const val GROUP_BAGGING = "dept-bagging"
    const val GROUP_WAREHOUSE = "dept-warehouse"

    // Department ID → Display Name (Fallback)
    val DEPARTMENTS = mapOf(
        // Bagging
        "dept-bagging-pp12" to "PP12",
        "dept-bagging-pp3" to "PP3",
        "dept-bagging-ppe" to "PPE",
        "dept-bagging-ppc" to "PPC",
        "dept-bagging-hd" to "HD",
        "dept-sealroom" to "Seal Room",
        "dept-bagging-sasb" to "SASB",
        // Warehouse
        "dept-warehouse-wh40" to "WH40",
        "dept-warehouse-wh41" to "WH41",
    )

    /** departmentId → parent group */
    fun getParentGroup(deptId: String): String {
        return when {
            deptId.startsWith("dept-bagging") -> GROUP_BAGGING
            deptId.startsWith("dept-warehouse") -> GROUP_WAREHOUSE
            else -> deptId
        }
    }
}
