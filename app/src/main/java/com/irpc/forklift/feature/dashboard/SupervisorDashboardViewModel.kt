// 📁 feature/dashboard/SupervisorDashboardViewModel.kt
package com.irpc.forklift.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import com.irpc.forklift.core.domain.repository.VehicleRepository
import com.irpc.forklift.core.domain.usecase.department.GetAccessibleDepartmentsUseCase
import com.irpc.forklift.core.domain.usecase.shift.GetCurrentShiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 📊 Supervisor Dashboard ViewModel
 *
 * - คำนวณ shift ปัจจุบัน
 * - โหลดรายการรถที่ยังไม่ถูกตรวจ (missing vehicles)
 * - filter ตาม department (ผ่าน FilterChips)
 */
@HiltViewModel
class SupervisorDashboardViewModel @Inject constructor(
    private val getShiftUseCase: GetCurrentShiftUseCase,
    private val getAccessibleDepts: GetAccessibleDepartmentsUseCase,
    private val vehicleRepository: VehicleRepository,
    private val checksheetRepository: ChecksheetRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                                // Get current shift info
                val shiftResult = getShiftUseCase()

                // Use mock vehicles for now (replace with real repo call)
                val vehicles = MockData.vehicles
                val activeVehicles = vehicles.filter { it.is_active }

                                // Mock: simulate some checked vehicles
                // In real implementation, load from checksheetRepository

                // For mock, mark first 5 vehicles as checked
                val checkedChassis = activeVehicles.take(5).map { it.chassis_no }.toSet()
                val missing = activeVehicles.filter { it.chassis_no !in checkedChassis }

                _uiState.value = DashboardUiState(
                    shift = shiftResult,
                    totalVehicles = activeVehicles.size,
                    checkedCount = checkedChassis.size,
                    missingVehicles = missing,
                    filteredMissingVehicles = missing,
                    isLoading = false,
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message,
                )
            }
        }
    }

    fun filterByDepartment(departmentId: String?) {
        val allMissing = _uiState.value.missingVehicles
        val filtered = if (departmentId == null) {
            allMissing
        } else {
            allMissing.filter { it.department_id == departmentId }
        }
        _uiState.value = _uiState.value.copy(
            selectedDepartment = departmentId,
            filteredMissingVehicles = filtered,
        )
    }

    fun refresh() {
        loadDashboard()
    }
}

