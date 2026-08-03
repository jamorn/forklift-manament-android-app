// 📁 core/domain/model/DailyChecksheet.kt
package com.irpc.forklift.core.domain.model

/**
 * 📋 Daily Checksheet — เทียบ DailyChecksheet interface ใน types/index.ts
 */
data class DailyChecksheet(
    val id: String = "", // document ID (Firestore generated)
    val date: String, // "2026-07-28"
    val shift: String, // "M" | "E" | "N"
    val shift_order: Int,
    val chassis_no: String,
    val flno_at_time: String,
    val operator_uid: String,
    val results: Map<String, String>, // itemId → "pass" | "fail"
    val remarks: Map<String, String>, // itemId → ข้อความ (เฉพาะ fail)
    val main_remark: String, // หมายเหตุรวม
    val manhourMeter: String,
    val status: String, // "normal" | "unsafe"
    val created_at: String,
    val updated_at: String = "",
)

/** Helpers */
fun DailyChecksheet.isUnsafe(): Boolean = status == "unsafe"

fun DailyChecksheet.isNormal(): Boolean = status == "normal"

fun DailyChecksheet.failCount(): Int = results.values.count { it == "fail" }
