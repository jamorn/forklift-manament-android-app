// 📁 feature/vehicles/VehicleListScreen.kt
@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift.feature.vehicles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.TodayShifts
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.ui.components.StatusBadge

// =====================================================================
// 🚛 VEHICLE LIST
// =====================================================================
@Composable
fun VehicleListScreen(
    todayShifts: TodayShifts? = null,
    checkedVehicles: Map<String, String> = emptyMap(),
    onVehicleClick: (Vehicle) -> Unit,
    onScan: () -> Unit,
    onBack: () -> Unit,
) {
    val deptNames =
        mapOf(
            "dept-bagging-pp12" to "PP12 Bagging",
            "dept-bagging-pp3" to "PP3 Bagging",
            "dept-bagging-ppe" to "PPE Bagging",
            "dept-bagging-ppc" to "PPC Bagging",
            "dept-bagging-hd" to "HD Bagging",
            "dept-sealroom" to "Seal Room",
        )
    val deptIcons =
        mapOf(
            "dept-bagging-pp12" to "🏭",
            "dept-bagging-pp3" to "🏭",
            "dept-bagging-ppe" to "🏭",
            "dept-bagging-ppc" to "🏭",
            "dept-bagging-hd" to "🏭",
            "dept-sealroom" to "🚪",
        )
    val vehicles = MockData.vehicles
    val checkedCount = vehicles.count { it.chassis_no in checkedVehicles }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("เลือกรถโฟร์คลิฟท์ 🚛") },
                navigationIcon = { TextButton(onClick = onBack) { Text("← กลับ") } },
                actions = {
                    TextButton(onClick = onScan) { Text("📷 สแกน") }
                },
            )
        },
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp)) {
            // Summary header with shift info (ตารางเวรวันนี้)
            item {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                if (checkedCount == vehicles.size) {
                                    Color(0xFF16A34A).copy(alpha = 0.1f)
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                },
                        ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        // แสดงตารางเวร: แต่ละทีมวันนี้อยู่กะอะไร
                        todayShifts?.teams?.forEach { team ->
                            val label = team.shift?.label ?: "หยุด"
                            Text(
                                text = "${team.teamName}: กะ$label",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color =
                                    if (team.shift == null) {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    } else {
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    },
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text =
                                if (checkedCount == vehicles.size) {
                                    "✅ ตรวจครบแล้วทุกคัน ($checkedCount/${vehicles.size})"
                                } else {
                                    "ตรวจแล้ว $checkedCount/${vehicles.size} คัน"
                                },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color =
                                if (checkedCount == vehicles.size) {
                                    Color(0xFF16A34A)
                                } else {
                                    Color(0xFFF59E0B)
                                },
                        )
                    }
                }
            }

            // Vehicle list grouped by department — each group in a Card
            vehicles.groupBy { it.department_id }.forEach { (deptId, list) ->
                item {
                    Spacer(Modifier.height(4.dp))
                    Card(
                        colors =
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            // Dept header
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "${deptIcons[deptId] ?: "📦"} ${deptNames[deptId] ?: deptId}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Spacer(Modifier.weight(1f))
                                    val deptChecked = list.count { it.chassis_no in checkedVehicles }
                                    Text(
                                        text = "$deptChecked/${list.size}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color =
                                            if (deptChecked == list.size) {
                                                Color(0xFF16A34A)
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            },
                                    )
                                }
                            }
                            // Vehicle cards inside
                            list.forEach { v ->
                                VehicleCard(
                                    v = v,
                                    checkedInfo = checkedVehicles[v.chassis_no],
                                    onClick = { onVehicleClick(v) },
                                )
                                if (v != list.last()) {
                                    Divider(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }

            // Bottom spacer
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun VehicleCard(
    v: Vehicle,
    checkedInfo: String?,
    onClick: () -> Unit,
) {
    val isChecked = checkedInfo != null

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color =
            if (isChecked) {
                Color(0xFF16A34A).copy(alpha = 0.04f)
            } else {
                Color.Transparent
            },
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(v.current_flno, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    if (isChecked) {
                        Spacer(Modifier.width(8.dp))
                        Text("✅", fontSize = 14.sp)
                    }
                }
                if (isChecked) {
                    Text(
                        checkedInfo ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF16A34A),
                    )
                } else {
                    Text(
                        "${v.chassis_no} · ${v.vehicle_type}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "⏳ รอตรวจ",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFF59E0B),
                    )
                }
            }
            StatusBadge(v.status)
        }
    }
}
