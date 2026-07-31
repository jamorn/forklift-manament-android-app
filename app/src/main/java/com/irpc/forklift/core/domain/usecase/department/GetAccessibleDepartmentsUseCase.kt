// 📁 core/domain/usecase/department/GetAccessibleDepartmentsUseCase.kt
package com.irpc.forklift.core.domain.usecase.department

import com.irpc.forklift.core.domain.model.Department
import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.core.domain.repository.DepartmentRepository
import javax.inject.Inject

/**
 * 🏭 Scope-based Department Access
 *
 * เทียบกับ canAccessDepartment() + useAccessibleVehicles() ในเว็บ
 *
 * scope rules:
 *   scope.isEmpty() → SA (เห็นทุกแผนก)
 *   scope มีค่า → filter เฉพาะแผนกที่ตรง
 */
class GetAccessibleDepartmentsUseCase
    @Inject
    constructor(
        private val departmentRepository: DepartmentRepository,
    ) {
        suspend operator fun invoke(profile: UserProfile): Result<List<Department>> {
            val allDepts = departmentRepository.getDepartments()

            return allDepts.map { departments ->
                if (profile.roles.scope.isEmpty()) {
                    // SA — เห็นทุกแผนก
                    departments
                } else {
                    // Admin/Operator — filter ตาม scope
                    departments.filter { dept ->
                        profile.roles.scope.any { scopeId ->
                            // scope "dept-bagging" → match "dept-bagging-pp12", etc.
                            scopeId == dept.id || dept.id.startsWith(scopeId)
                        }
                    }
                }
            }
        }
    }
