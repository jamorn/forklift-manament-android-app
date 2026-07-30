// 📁 core/domain/repository/DepartmentRepository.kt
package com.irpc.forklift.core.domain.repository

import com.irpc.forklift.core.domain.model.Department

/**
 * 🏭 Department Repository Interface
 */
interface DepartmentRepository {
    /** โหลด departments ทั้งหมด (Firestore + cache) */
    suspend fun getDepartments(): Result<List<Department>>

    /** ดึง department ตาม id */
    suspend fun getDepartmentById(id: String): Result<Department>

    /** scope logic: user → departments ที่เข้าถึงได้ */
    suspend fun getAccessibleDepartments(scope: List<String>): Result<List<Department>>
}
