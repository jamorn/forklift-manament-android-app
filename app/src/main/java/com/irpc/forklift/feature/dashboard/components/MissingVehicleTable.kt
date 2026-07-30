// 📁 feature/dashboard/components/MissingVehicleTable.kt
package com.irpc.forklift.feature.dashboard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.domain.model.Vehicle

/**
 * ❌ Missing Vehicle Table
 *
 * รถที่ยังไม่ถูกตรวจในกะนี้
 *
 * @param vehicles รายการรถที่ค้างตรวจ
 */
@Composable
fun MissingVehicleTable(
    vehicles: List<Vehicle>,
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(vehicles) { vehicle ->
            VehicleRow(
                vehicle = vehicle,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}

