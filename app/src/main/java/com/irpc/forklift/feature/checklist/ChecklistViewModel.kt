// 📁 feature/checklist/ChecklistViewModel.kt
package com.irpc.forklift.feature.checklist

import androidx.lifecycle.ViewModel
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.usecase.checklist.GetPreviousChecksheetUseCase
import com.irpc.forklift.core.domain.usecase.checklist.SubmitChecksheetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 📋 Checklist ViewModel
 *
 * @HiltViewModel
 * class ChecklistViewModel @Inject constructor(
 *     private val getPreviousChecksheet: GetPreviousChecksheetUseCase,
 *     private val submitChecksheet: SubmitChecksheetUseCase,
 * ) : ViewModel() {
 *
 *     private val _uiState = MutableStateFlow(ChecklistUiState())
 *     val uiState: StateFlow<ChecklistUiState> = _uiState.asStateFlow()
 *
 *     fun selectVehicle(vehicle: Vehicle) {
 *         viewModelScope.launch {
 *             _uiState.value = _uiState.value.copy(selectedVehicle = vehicle, step = 2)
 *
 *             // Copy-Forward: โหลด checksheet ก่อนหน้า
 *             val previous = getPreviousChecksheet(
 *                 vehicle.chassis_no,
 *                 DateUtils.getTodayString(),
 *                 "M"  // TODO: get from shift usecase
 *             )
 *             previous.onSuccess { cs ->
 *                 _uiState.value = _uiState.value.copy(previousChecksheet = cs)
 *             }
 *         }
 *     }
 *
 *     fun checkItem(itemId: String, result: String) { ... }
 *     fun submitChecksheet() { ... }
 *     fun reset() { ... }
 * }
 */
object ChecklistViewModel {
    // TODO: implement ViewModel
}
