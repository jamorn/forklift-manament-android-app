// 📁 core/domain/model/UserProfile.kt
package com.irpc.forklift.core.domain.model
import com.irpc.forklift.core.common.constants.DepartmentConstants

/**
 * 👤 User Profile — เทียบ UserProfile interface ใน types/index.ts
 */
data class UserProfile(
    val email: String,
    val displayName: String,
    val position: String,
    val employmentType: String, // "permanent" | "contractor"
    val companyName: String,
    val status: List<String>, // ["active"] | ["pending"] | ["inactive"]
    val roles: ForkliftRoles,
    val mailto: List<String>,
    val createdAt: String,
    val lastLoginAt: String,
    val lastUpdatedAt: String,
)

data class ForkliftRoles(
    val role: String, // "sa" | "admin" | "operator" | "viewer"
    val scope: List<String>, // [] = เห็นทั้งหมด (SA), ["dept-bagging-pp12"] = เฉพาะ PP12
    val access: Int = 0, // ★ ใหม่: bitmask ของแผนกที่ user เห็นได้ (จาก DepartmentConstants)
)

/** Permission helpers */
fun UserProfile.isSuperAdmin(): Boolean = roles.role == "sa"

fun UserProfile.isAdmin(): Boolean = roles.role in listOf("sa", "admin", "super")

fun UserProfile.isActive(): Boolean = status.contains("active")

/** สามารถเข้าถึง department นี้ได้หรือไม่? (ใช้ bitmask access เป็นหลัก) */
fun UserProfile.canAccessDepartment(departmentId: String): Boolean {
    // SA: access = -1 (ทุกบิตเป็น 1) → เห็นหมด
    if (roles.access == DepartmentConstants.ACCESS_ALL) return true

    val deptBit = DepartmentConstants.bitOf(departmentId)

    // bitmask ไม่รู้จักแผนก → fallback ใช้ scope เดิม (legacy)
    if (deptBit == 0) {
        if (roles.scope.isEmpty()) return true
        return roles.scope.any { departmentId == it || departmentId.startsWith(it) }
    }

    // เช็ค bitmask เร็ว ๆ: userAccess AND deptBit != 0 → เห็น
    return (roles.access and deptBit) != 0
}
