// 📁 feature/checklist/ChecklistScreen.kt
package com.irpc.forklift.feature.checklist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import com.irpc.forklift.feature.checklist.components.VehicleSelector
import com.irpc.forklift.feature.checklist.components.ChecklistForm
import com.irpc.forklift.core.domain.model.Vehicle
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
    onBack: () -> Unit = {},
    initialVehicle: Vehicle? = null,
    viewModel: ChecklistViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    // ถ้ามี initialVehicle → select ทันที (ข้าม Step 1)
    LaunchedEffect(initialVehicle) {
        if (initialVehicle != null && uiState.step == 1) {
            viewModel.selectVehicle(initialVehicle)
        }
    }

    Scaffold(
        topBar = {
            when (uiState.step) {
                1 -> TopAppBar(title = { Text("เลือกรถโฟร์คลิฟท์") })
                2 -> uiState.selectedVehicle?.let { v ->
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            TextButton(onClick = onBack) {
                                Text("← กลับ", style = MaterialTheme.typography.bodyLarge)
                            }
                        },
                        actions = {
                            Text(
                                text = v.current_flno,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 16.dp),
                            )
                        },
                    )
                }
                3 -> TopAppBar(
                    title = { Text("บันทึกสำเร็จ") },
                    navigationIcon = {
                        TextButton(onClick = { viewModel.reset(); onBack() }) {
                            Text("← กลับ")
                        }
                    },
                )
            }
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
                        results = uiState.checkResults,
                        remarks = uiState.remarks,
                        mainRemark = uiState.mainRemark,
                        manhourMeter = uiState.manhourMeter,
                        onItemChecked = { itemId, result -> viewModel.checkItem(itemId, result) },
                        onItemRemark = { itemId, remark -> viewModel.remarkItem(itemId, remark) },
                        onMainRemarkChange = { viewModel.setMainRemark(it) },
                        onManhourMeterChange = { viewModel.setManhourMeter(it) },
                        onPassAll = { viewModel.passAllItems() },
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

