// 📁 core/domain/model/MaintenanceLog.kt
package com.irpc.forklift.core.domain.model

/**
 * 🔧 Maintenance Log
 */
data class MaintenanceLog(
    val id: String, // document ID
    val chassis_no: String,
    val flno_at_time: String,
    val date: String,
    val category: String, // "Electrical" | "Tire" | "Hydraulic" | ...
    val description: String,
    val parts_cost: Int,
    val labor_cost: Int,
    val total_cost: Int,
    val mechanic: String,
    val parts_list: List<String>,
    val status: String, // "pending" | "completed"
    val created_at: String,
)

/** Helpers */
fun MaintenanceLog.isPending(): Boolean = status == "pending"

fun MaintenanceLog.isCompleted(): Boolean = status == "completed"
