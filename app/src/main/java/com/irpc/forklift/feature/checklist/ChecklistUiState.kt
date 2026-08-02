// feature/checklist/ChecklistUiState.kt
package com.irpc.forklift.feature.checklist

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.model.ShiftCode
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 📋 Checklist UI State
 */
data class ChecklistUiState(
    val step: Int = 1, // 1=เลือก, 2=ตรวจ, 3=สำเร็จ
    val vehicles: List<Vehicle> = emptyList(),
    val selectedVehicle: Vehicle? = null,
    val currentUser: String? = null, // email ของ user ที่ login
    val currentShift: ShiftCode? = null, // กะตามช่วงเวลาปัจจุบัน (แสดงให้ user เห็น)
    val checkResults: Map<String, String> = emptyMap(), // itemId → "pass" | "fail"
    val remarks: Map<String, String> = emptyMap(), // itemId → ข้อความ (เฉพาะ fail)
    val mainRemark: String = "", // หมายเหตุรวม
    val manhourMeter: String = "", // เลขไมล์
    val previousChecksheet: DailyChecksheet? = null,
    val isCopyForward: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
