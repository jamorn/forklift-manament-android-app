// 📁 core/domain/model/Vehicle.kt
package com.irpc.forklift.core.domain.model

/**
 * 🚛 Vehicle — เทียบ Vehicle interface ใน types/index.ts
 */
data class Vehicle(
    val chassis_no: String,
    val current_flno: String,
    val department_id: String,
    val vehicle_type: String,          // "diesel" | "ev" | "hybrid"
    val status: String,                // "active" | "maintenance" | "inactive"
    val is_active: Boolean,
    val lease_start: String,
    val lease_end: String,
    val rental_price: Int,
    val flno_history: List<FlnoHistoryItem>,
)

data class FlnoHistoryItem(
    val flno: String,
    val start_date: String,
    val end_date: String?,             // null = ปัจจุบัน
)

/** Helpers */
fun Vehicle.isInMaintenance(): Boolean = status == "maintenance"
fun Vehicle.isDiesel(): Boolean = vehicle_type == "diesel"
fun Vehicle.isEV(): Boolean = vehicle_type == "ev"
fun Vehicle.isHybrid(): Boolean = vehicle_type == "hybrid"
