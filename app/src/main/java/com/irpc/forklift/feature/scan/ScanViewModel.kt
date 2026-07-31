package com.irpc.forklift.feature.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.irpc.forklift.core.data.mock.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ScanViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(ScanUiState())
        val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

        /**
         * QR code encode chassis_no
         * scan -> map หา vehicle -> แสดง FL No.
         */
        fun onCodeScanned(code: String) {
            val now =
                ZonedDateTime
                    .now(ZoneId.of("Asia/Bangkok"))
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            Log.d("ScanVM", "QR scanned at: $now — chassis: $code")

            val trimmedCode = code.trim()
            val vehicle =
                MockData.vehicles.firstOrNull { v ->
                    v.chassis_no.equals(trimmedCode, ignoreCase = true)
                }

            if (vehicle != null) {
                _uiState.value =
                    _uiState.value.copy(
                        isScanning = false,
                        scannedCode = trimmedCode,
                        matchedVehicle = vehicle,
                    )
            } else {
                _uiState.value =
                    _uiState.value.copy(
                        isScanning = false,
                        error = "ไม่พบรถในระบบ (chassis: $trimmedCode)",
                    )
            }
        }

        fun retry() {
            _uiState.value = _uiState.value.copy(isScanning = true)
        }
    }
