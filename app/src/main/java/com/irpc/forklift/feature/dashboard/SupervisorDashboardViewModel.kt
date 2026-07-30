// 📁 feature/dashboard/SupervisorDashboardViewModel.kt
package com.irpc.forklift.feature.dashboard

import androidx.lifecycle.ViewModel
import com.irpc.forklift.core.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 📊 Supervisor Dashboard ViewModel
 *
 * @HiltViewModel
 * class SupervisorDashboardViewModel @Inject constructor(
 *     private val getShiftUseCase: GetCurrentShiftUseCase,
 *     private val getAccessibleDepts: GetAccessibleDepartmentsUseCase,
 *     private val vehicleRepository: VehicleRepository,
 *     private val checksheetRepository: ChecksheetRepository,
 * ) : ViewModel() {
 *
 *     private val _uiState = MutableStateFlow(DashboardUiState())
 *     val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
 *
 *     init { loadDashboard() }
 *
 *     private fun loadDashboard() {
 *         viewModelScope.launch {
 *             val shift = getShiftUseCase()
 *             val profile = authRepository.getCurrentProfile()
 *             val depts = getAccessibleDepts(profile?.roles?.scope ?: emptyList())
 *             val vehicles = vehicleRepository.getVehicles(depts.map { it.id })
 *
 *             // โหลด checksheet วันนี้ของกะนี้
 *             val todaySheets = checksheetRepository.getChecksheets(
 *                 DateUtils.getTodayString(), shift.shift.name, vehicles.map { it.chassis_no }
 *             )
 *
 *             val checkedChassis = todaySheets.map { it.chassis_no }
 *             val missing = vehicles.filter { it.chassis_no !in checkedChassis }
 *
 *             _uiState.value = DashboardUiState(
 *                 shift = shift,
 *                 totalVehicles = vehicles.size,
 *                 checkedCount = checkedChassis.size,
 *                 missingVehicles = missing,
 *                 isLoading = false,
 *             )
 *         }
 *     }
 * }
 */
object SupervisorDashboardViewModel {
    // TODO: implement ViewModel
}
