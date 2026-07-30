// 📁 core/data/local/entity/DepartmentEntity.kt
package com.irpc.forklift.core.data.local.entity

import com.irpc.forklift.core.domain.model.Department

/**
 * 🏭 Department Room Entity
 *
 * @Entity(tableName = "departments")
 */
data class DepartmentEntity(
    val id: String,
    val name: String,
    val parentGroup: String,
    val sortOrder: Int,
)

fun DepartmentEntity.toDomain(): Department = Department(
    id = id,
    name = name,
    parentGroup = parentGroup,
    sortOrder = sortOrder,
)

fun Department.toEntity(): DepartmentEntity = DepartmentEntity(
    id = id,
    name = name,
    parentGroup = parentGroup,
    sortOrder = sortOrder,
)
