// 📁 core/domain/model/UserProfile.kt
package com.irpc.forklift.core.domain.model

/**
 * 👤 User Profile — เทียบ UserProfile interface ใน types/index.ts
 */
data class UserProfile(
    val email: String,
    val displayName: String,
    val position: String,
    val employmentType: String,       // "permanent" | "contractor"
    val companyName: String,
    val status: List<String>,         // ["active"] | ["pending"] | ["inactive"]
    val roles: ForkliftRoles,
    val mailto: List<String>,
    val createdAt: String,
    val lastLoginAt: String,
    val lastUpdatedAt: String,
)

data class ForkliftRoles(
    val role: String,                  // "sa" | "admin" | "operator" | "viewer"
    val scope: List<String>,           // [] = เห็นทั้งหมด (SA), ["dept-bagging-pp12"] = เฉพาะ PP12
)

/** Permission helpers */
fun UserProfile.isSuperAdmin(): Boolean = roles.role == "sa"
fun UserProfile.isAdmin(): Boolean = roles.role in listOf("sa", "admin", "super")
fun UserProfile.isActive(): Boolean = status.contains("active")

/** สามารถเข้าถึง department นี้ได้หรือไม่? */
fun UserProfile.canAccessDepartment(departmentId: String): Boolean {
    if (roles.scope.isEmpty()) return true   // SA เห็นหมด
    return roles.scope.any { deptId ->
        deptId == departmentId || departmentId.startsWith(deptId)
    }
}
