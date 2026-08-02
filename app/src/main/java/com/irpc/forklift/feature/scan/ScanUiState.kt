// feature/scan/ScanUiState.kt
package com.irpc.forklift.feature.scan

import com.irpc.forklift.core.domain.model.Vehicle

data class ScanUiState(
    val isScanning: Boolean = true,
    val scannedCode: String? = null,
    val matchedVehicle: Vehicle? = null,
    val error: String? = null,
)
