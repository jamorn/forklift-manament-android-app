// 📁 feature/dashboard/DashboardUiState.kt
package com.irpc.forklift.feature.dashboard

import com.irpc.forklift.core.domain.model.ShiftResult
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * 📊 Dashboard UI State
 */
data class DashboardUiState(
    val shift: ShiftResult? = null,
    val totalVehicles: Int = 0,
    val checkedCount: Int = 0,
    val missingVehicles: List<Vehicle> = emptyList(),
    val filteredMissingVehicles: List<Vehicle> = emptyList(),
    val selectedDepartment: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
