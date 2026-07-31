// 📁 core/data/local/entity/VehicleEntity.kt
package com.irpc.forklift.core.data.local.entity

import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 🚛 Vehicle Room Entity
 *
 * @Entity(tableName = "vehicles")
 */
data class VehicleEntity(
    val chassis_no: String,
    val current_flno: String,
    val department_id: String,
    val vehicle_type: String,
    val status: String,
    val is_active: Boolean,
    val lease_start: String,
    val lease_end: String,
    val rental_price: Int,
    val flno_history_json: String = "",
)

fun VehicleEntity.toDomain(): Vehicle =
    Vehicle(
        chassis_no = chassis_no,
        current_flno = current_flno,
        department_id = department_id,
        vehicle_type = vehicle_type,
        status = status,
        is_active = is_active,
        lease_start = lease_start,
        lease_end = lease_end,
        rental_price = rental_price,
        flno_history = emptyList(), // TODO: parse from JSON
    )
