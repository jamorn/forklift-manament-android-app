// 📁 core/data/repository/DepartmentRepositoryImpl.kt
package com.irpc.forklift.core.data.repository

import com.irpc.forklift.core.common.constants.AppConstants
import com.irpc.forklift.core.common.constants.DepartmentConstants
import com.irpc.forklift.core.domain.model.Department
import com.irpc.forklift.core.domain.repository.DepartmentRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🏭 Department Repository Implementation
 *
 * 1. พยายามโหลดจาก Firestore (collection "departments")
 * 2. Fallback → DepartmentConstants (offline/dev)
 * 3. Cache ใน Room
 */
@Singleton
class DepartmentRepositoryImpl @Inject constructor(
    // private val firestore: FirebaseFirestore,
    // private val departmentDao: DepartmentDao,
) : DepartmentRepository {

    // Fallback data สำหรับ offline/dev
    private val fallbackDepartments = DepartmentConstants.DEPARTMENTS.map { (id, name) ->
        Department(
            id = id,
            name = name,
            parentGroup = DepartmentConstants.getParentGroup(id),
            sortOrder = DepartmentConstants.DEPARTMENTS.keys.indexOf(id),
        )
    }

    override suspend fun getDepartments(): Result<List<Department>> {
        return try {
            // TODO: โหลดจาก Firestore
            // val snapshot = firestore.collection(AppConstants.COLLECTION_DEPARTMENTS).get().await()
            // val departments = snapshot.documents.map { it.toObject(Department::class.java) }
            Result.success(fallbackDepartments)
        } catch (e: Exception) {
            Result.success(fallbackDepartments) // Fallback
        }
    }

    override suspend fun getDepartmentById(id: String): Result<Department> {
        val dept = fallbackDepartments.find { it.id == id }
        return if (dept != null) Result.success(dept)
        else Result.failure(Exception("Department not found: $id"))
    }

    override suspend fun getAccessibleDepartments(scope: List<String>): Result<List<Department>> {
        val allDepts = getDepartments().getOrDefault(fallbackDepartments)

        return if (scope.isEmpty()) {
            Result.success(allDepts)
        } else {
            val filtered = allDepts.filter { dept ->
                scope.any { scopeId -> dept.id == scopeId || dept.id.startsWith(scopeId) }
            }
            Result.success(filtered)
        }
    }
}
