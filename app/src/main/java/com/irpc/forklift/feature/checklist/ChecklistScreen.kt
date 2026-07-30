// 📁 feature/checklist/ChecklistScreen.kt
package com.irpc.forklift.feature.checklist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import com.irpc.forklift.feature.checklist.components.VehicleSelector
import com.irpc.forklift.feature.checklist.components.ChecklistForm
import com.irpc.forklift.feature.checklist.components.SuccessScreen.SuccessScreen
import com.irpc.forklift.ui.components.LoadingSpinner

/**
 * 📋 Operator Checklist Screen (Main Feature)
 *
 * Step 1: VehicleSelector — เลือกรถ
 * Step 2: ChecklistForm — ตรวจเช็ค (พร้อม Copy-Forward)
 * Step 3: SuccessScreen — บันทึกสำเร็จ
 *
 * @param viewModel ChecklistViewModel (injected by Hilt)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(
    viewModel: ChecklistViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("📋 Daily Checklist") })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (uiState.step) {
                1 -> VehicleSelector(
                    vehicles = uiState.vehicles,
                    onVehicleSelected = { vehicle -> viewModel.selectVehicle(vehicle) },
                )
                2 -> uiState.selectedVehicle?.let { vehicle ->
                    ChecklistForm(
                        vehicle = vehicle,
                        previousChecksheet = uiState.previousChecksheet,
                        onItemChecked = { itemId, result -> viewModel.checkItem(itemId, result) },
                        onSubmit = { viewModel.submitChecksheet() },
                    )
                }
                3 -> SuccessScreen(
                    onGoHome = { viewModel.reset() },
                )
            }

            // Loading overlay
            if (uiState.isLoading) {
                LoadingSpinner()
            }
        }
    }
}

