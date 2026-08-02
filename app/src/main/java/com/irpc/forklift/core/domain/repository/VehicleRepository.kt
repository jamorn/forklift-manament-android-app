// 📁 core/domain/repository/VehicleRepository.kt
package com.irpc.forklift.core.domain.repository

import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 🚛 Vehicle Repository Interface
 */
interface VehicleRepository {
    /** ดึงรถทั้งหมดใน scope */
    suspend fun getVehicles(departmentIds: List<String>): Result<List<Vehicle>>

    /** ดึงรถเฉพาะที่ user เข้าถึงได้ (ใช้ canAccessDepartment ตัดสิน) */
    suspend fun getAccessibleVehicles(profile: UserProfile): Result<List<Vehicle>>

    /** ดึงรถคันเดียว */
    suspend fun getVehicleByChassis(chassisNo: String): Result<Vehicle>

    /** ดึงรถที่อยู่ maintenance */
    suspend fun getMaintenanceVehicles(): Result<List<Vehicle>>
}
