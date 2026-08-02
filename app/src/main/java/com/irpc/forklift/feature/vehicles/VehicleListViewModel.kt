// 📁 feature/vehicles/VehicleListViewModel.kt
package com.irpc.forklift.feature.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irpc.forklift.core.data.repository.*
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🚛 VehicleList ViewModel
 *
 * โหลดรายการรถที่ user เข้าถึงได้ (ผ่าน SessionRepository + VehicleRepository)
 * และกรองตามสิทธิ์ด้วย canAccessDepartment ใน repository กลาง
 */
@HiltViewModel
class VehicleListViewModel
    @Inject
    constructor(
        private val sessionRepository: SessionRepository,
        private val vehicleRepository: VehicleRepository,
    ) : ViewModel() {
        private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
        val vehicles: StateFlow<List<Vehicle>> = _vehicles.asStateFlow()

        init {
            loadVehicles()
        }

        private fun loadVehicles() {
            val profile = sessionRepository.getProfile()
            viewModelScope.launch {
                if (profile != null) {
                    _vehicles.value = vehicleRepository.getAccessibleVehicles(profile).getOrDefault(emptyList())
                }
            }
        }

        fun refresh() {
            loadVehicles()
        }
    }
