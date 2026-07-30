// 📁 feature/checklist/components/VehicleSelector.kt
package com.irpc.forklift.feature.checklist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.ui.components.StatusBadge
import androidx.compose.material3.ExperimentalMaterial3Api

/**
 * 🚛 Step 1: Vehicle Selector
 *
 * แสดงรายการรถทั้งหมดให้ผู้ใช้เลือกรถที่จะตรวจ
 *
 * @param vehicles รายการรถ
 * @param onVehicleSelected callback เมื่อเลือกรถ
 */
@Composable
fun VehicleSelector(
    vehicles: List<Vehicle>,
    onVehicleSelected: (Vehicle) -> Unit,
) {
    val deptNames = mapOf(
        "dept-bagging-pp12" to "PP12 Bagging",
        "dept-bagging-pp3" to "PP3 Bagging",
        "dept-bagging-ppe" to "PPE Bagging",
        "dept-bagging-ppc" to "PPC Bagging",
        "dept-bagging-hd" to "HD Bagging",
        "dept-sealroom" to "Seal Room",
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                "Bagging (ไม่รวม SASB)",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        val grouped = vehicles.groupBy { it.department_id }
        grouped.forEach { (deptId, list) ->
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                ) {
                    Text(
                        text = deptNames[deptId] ?: deptId,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            items(list) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    onClick = { onVehicleSelected(vehicle) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VehicleCard(
    vehicle: Vehicle,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    vehicle.current_flno,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    "${vehicle.chassis_no} · ${vehicle.vehicle_type}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            StatusBadge(vehicle.status)
        }
    }
}

