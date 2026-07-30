// 📁 feature/checklist/ChecklistViewModel.kt

package com.irpc.forklift.feature.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.usecase.checklist.GetPreviousChecksheetUseCase
import com.irpc.forklift.core.domain.usecase.checklist.SubmitChecksheetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * 📋 Checklist ViewModel
 *
 * - selectVehicle: เลือกรถ + โหลด checksheet ก่อนหน้า (Copy-Forward)
 * - checkItem: บันทึกผลตรวจแต่ละรายการ
 * - submitChecksheet: ส่งบันทึก
 * - reset: กลับไปเริ่มใหม่
 */
@HiltViewModel
class ChecklistViewModel @Inject constructor(
    private val getPreviousChecksheet: GetPreviousChecksheetUseCase,
    private val submitChecksheet: SubmitChecksheetUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChecklistUiState())
    val uiState: StateFlow<ChecklistUiState> = _uiState.asStateFlow()

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        _uiState.value = _uiState.value.copy(
            vehicles = MockData.vehicles,
        )
    }

    fun selectVehicle(vehicle: Vehicle) {
        _uiState.value = _uiState.value.copy(
            selectedVehicle = vehicle,
            step = 2,
            isLoading = true,
        )

        viewModelScope.launch {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            // Default to morning shift for now; real app would use GetCurrentShiftUseCase
            val result = getPreviousChecksheet(
                chassisNo = vehicle.chassis_no,
                currentDate = today,
                currentShift = "M",
            )
            result.onSuccess { cs ->
                _uiState.value = _uiState.value.copy(
                    previousChecksheet = cs,
                    isCopyForward = cs != null,
                    isLoading = false,
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false,
                )
            }
        }
    }

    fun checkItem(itemId: String, result: String) {
        val current = _uiState.value.checkResults.toMutableMap()
        current[itemId] = result
        _uiState.value = _uiState.value.copy(checkResults = current)
    }

    fun submitChecksheet() {
        val state = _uiState.value
        val vehicle = state.selectedVehicle ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val checksheet = DailyChecksheet(
                date = today,
                shift = "M", // TODO: use GetCurrentShiftUseCase
                shift_order = 1,
                chassis_no = vehicle.chassis_no,
                flno_at_time = vehicle.current_flno,
                operator_uid = "",
                results = state.checkResults,
                remarks = emptyMap(),
                main_remark = "",
                manhourMeter = "",
                status = if (state.checkResults.any { it.value == "fail" }) "unsafe" else "normal",
                created_at = "",
            )

            val result = submitChecksheet.invoke(checksheet)
            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    step = 3,
                    isLoading = false,
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false,
                )
            }
        }
    }

    fun reset() {
        _uiState.value = ChecklistUiState(vehicles = MockData.vehicles)
    }
}

