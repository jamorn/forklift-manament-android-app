// 📁 core/domain/model/Vehicle.kt
package com.irpc.forklift.core.domain.model

/**
 * 🚛 Vehicle — เทียบ Vehicle interface ใน types/index.ts
 */
data class Vehicle(
    val chassis_no: String,
    val current_flno: String,
    val department_id: String,
    val vehicle_type: String, // "diesel" | "ev" | "hybrid"
    val model: String = "", // ชื่อรุ่น เช่น "Y1F2A25U -2W300" (display)
    val status: String, // "active" | "maintenance" | "inactive"
    val is_active: Boolean,
    val lease_start: String,
    val lease_end: String,
    val rental_price: Int,
    val flno_history: List<FlnoHistoryItem>,
    val dept_bit: Int = 0, // ★ ใหม่: bit ของแผนก (จาก DepartmentConstants)
)

data class FlnoHistoryItem(
    val flno: String,
    val start_date: String,
    val end_date: String?, // null = ปัจจุบัน
)

/** Helpers */
fun Vehicle.isInMaintenance(): Boolean = status == "maintenance"

fun Vehicle.isDiesel(): Boolean = vehicle_type == "diesel"

fun Vehicle.isEV(): Boolean = vehicle_type == "ev"

fun Vehicle.isHybrid(): Boolean = vehicle_type == "hybrid"

/** กรอง List<Vehicle> ด้วย bitmask access — เห็นคันที่ (access and dept_bit) != 0 */
fun List<Vehicle>.visibleTo(access: Int): List<Vehicle> =
    this.filter { (access and it.dept_bit) != 0 }
