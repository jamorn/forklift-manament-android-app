// 📁 feature/checklist/ChecklistUiState.kt
package com.irpc.forklift.feature.checklist

import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 📋 Checklist UI State
 */
data class ChecklistUiState(
    val step: Int = 1,                           // 1=เลือก, 2=ตรวจ, 3=สำเร็จ
    val vehicles: List<Vehicle> = emptyList(),
    val selectedVehicle: Vehicle? = null,
    val checkResults: Map<String, String> = emptyMap(),  // itemId → "pass" | "fail"
    val previousChecksheet: DailyChecksheet? = null,
    val isCopyForward: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
