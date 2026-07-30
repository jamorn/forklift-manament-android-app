// 📁 core/data/mapper/VehicleMapper.kt
package com.irpc.forklift.core.data.mapper

import com.irpc.forklift.core.data.local.entity.VehicleEntity
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 🚛 Vehicle Mapper — Entity ↔ Domain
 */
object VehicleMapper {
    fun entityToDomain(entity: VehicleEntity): Vehicle {
        return Vehicle(
            chassis_no = entity.chassis_no,
            current_flno = entity.current_flno,
            department_id = entity.department_id,
            vehicle_type = entity.vehicle_type,
            status = entity.status,
            is_active = entity.is_active,
            lease_start = entity.lease_start,
            lease_end = entity.lease_end,
            rental_price = entity.rental_price,
            flno_history = emptyList(), // TODO: parse from JSON
        )
    }

    fun domainToEntity(domain: Vehicle): VehicleEntity {
        return VehicleEntity(
            chassis_no = domain.chassis_no,
            current_flno = domain.current_flno,
            department_id = domain.department_id,
            vehicle_type = domain.vehicle_type,
            status = domain.status,
            is_active = domain.is_active,
            lease_start = domain.lease_start,
            lease_end = domain.lease_end,
            rental_price = domain.rental_price,
            flno_history_json = "", // TODO: serialize to JSON
        )
    }
}
