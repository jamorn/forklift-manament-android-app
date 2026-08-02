// 📁 feature/vehicles/VehicleListScreen.kt
@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift.feature.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.irpc.forklift.core.common.constants.DepartmentConstants
import com.irpc.forklift.core.domain.model.ShiftCode
import com.irpc.forklift.core.domain.model.TodayShifts
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.ui.components.StatusBadge
import com.irpc.forklift.ui.theme.ForkliftColors

// =====================================================================
// 🚛 VEHICLE LIST
// =====================================================================
@Composable
fun VehicleListScreen(
    todayShifts: TodayShifts? = null,
    currentShift: ShiftCode? = null,
    checkedVehicles: Map<String, String> = emptyMap(),
    isOperator: Boolean = false,
    onVehicleClick: (Vehicle) -> Unit,
    onScan: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit = {},
    viewModel: VehicleListViewModel = hiltViewModel(),
) {
    // ดึงรถเฉพาะที่ user เข้าถึงได้ (ผ่าน session + repository กลาง)
    val vehicles by viewModel.vehicles.collectAsState()
    val checkedCount = vehicles.count { it.chassis_no in checkedVehicles }

    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = ForkliftColors.BgSecondary,
                        titleContentColor = ForkliftColors.TextPrimary,
                    ),
                title = {
                    Text(
                        "เลือกรถ Forklift 🚛",
                        color = ForkliftColors.TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                },
                navigationIcon = {
                    if (isOperator) {
                        TextButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Filled.PowerSettingsNew,
                                contentDescription = "ออกจากระบบ",
                                tint = ForkliftColors.Danger, // สีแดง
                                modifier = Modifier.size(22.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text("ออกจากระบบ", color = ForkliftColors.Danger, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        }
                    } else {
                        TextButton(onClick = onBack) {
                            Text(
                                "← กลับ",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                            )
                        }
                    }
                },
                actions = {
                    TextButton(onClick = onScan) {
                        Text(
                            "📷 สแกน",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp)) {
            // Summary header — แสดงเฉพาะทีมที่ตรงกับกะเวลาปัจจุบัน
            item {
                val todayStr =
                    java.time.LocalDate.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    )
                // หาทีมที่วันนี้อยู่กะเดียวกับ clock time ปัจจุบัน (M/E/N)
                val matchingTeam =
                    todayShifts?.teams?.firstOrNull { it.shift == currentShift }
                val teamName = matchingTeam?.teamName?.replace("กะ ", "") ?: "?"
                val shiftLabel = currentShift?.label ?: "?"
                val subIndex = matchingTeam?.subIndex ?: 1

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
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    ) {
                        Text(
                            text = "วันนี้ $todayStr   กะ $teamName: $shiftLabel $subIndex",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text =
                                if (checkedCount == vehicles.size) {
                                    "✅ ตรวจครบแล้วทุกคัน ($checkedCount/${vehicles.size})"
                                } else {
                                    "ตรวจแล้ว $checkedCount/${vehicles.size} คัน"
                                },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
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
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "${DepartmentConstants.deptIcons[deptId] ?: "📦"} ${DepartmentConstants.displayName(deptId)}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Spacer(Modifier.weight(1f))
                                    val deptChecked = list.count { it.chassis_no in checkedVehicles }
                                    Text(
                                        text = "$deptChecked/${list.size}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontSize = 16.sp,
                                        color =
                                            if (deptChecked == list.size) {
                                                Color(0xFF16A34A)
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            },
                                    )
                                }
                            }
                            // Vehicle cards inside (แต่ละคันเป็น Card ย่อยแยกชิ้น)
                            list.forEach { v ->
                                VehicleCard(
                                    v = v,
                                    checkedInfo = checkedVehicles[v.chassis_no],
                                    onClick = { onVehicleClick(v) },
                                )
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
    val borderColor =
        if (isChecked) {
            Color(0xFF16A34A)
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        }

    Card(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (isChecked) {
                        Color(0xFF16A34A).copy(alpha = 0.07f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
            ),
        border = BorderStroke(1.5.dp, borderColor),
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(v.current_flno, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
                    if (isChecked) {
                        Spacer(Modifier.width(8.dp))
                        Text("✅", fontSize = 18.sp)
                    }
                }
                if (isChecked) {
                    Text(
                        checkedInfo ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = Color(0xFF16A34A),
                    )
                } else {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "⏳ รอตรวจ",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 16.sp,
                        color = Color(0xFFF59E0B),
                    )
                }
            }
            StatusBadge(v.status)
        }
    }
}
