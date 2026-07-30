@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.ShiftResult
import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.usecase.shift.GetCurrentShiftUseCase
import com.irpc.forklift.core.common.utils.DateUtils
import com.irpc.forklift.feature.auth.LoginScreen
import com.irpc.forklift.feature.checklist.ChecklistScreen as NewChecklistScreen
import com.irpc.forklift.feature.scan.ScannerScreen
import com.irpc.forklift.feature.dashboard.SupervisorDashboardScreen
import com.irpc.forklift.feature.maintenance.MaintenanceScreen
import com.irpc.forklift.feature.report.ReportScreen
import com.irpc.forklift.ui.components.StatusBadge
import com.irpc.forklift.ui.theme.ForkliftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForkliftTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppRoot()
                }
            }
        }
    }
}

// ============ ROOT NAV ============
@Composable
fun AppRoot() {
        var screen by remember { mutableStateOf("login") }
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    var selectedV by remember { mutableStateOf<Vehicle?>(null) }

    // Shift ปัจจุบัน
    val shiftUseCase = remember { GetCurrentShiftUseCase() }
    val shiftResult = remember { shiftUseCase() }
    val todayStr = DateUtils.getTodayString()
    val shiftKey = "${todayStr}_${shiftResult.shift}"

    // track รถที่ตรวจแล้วในกะนี้: chassis_no → "08:45 โดย wiroj"
    // key = "2025-07-30_M" แยกข้อมูลแต่ละกะ
    val allChecked = remember { mutableStateMapOf<String, MutableMap<String, String>>() }
    val checkedVehicles = allChecked.getOrPut(shiftKey) { mutableMapOf() }

    val isOperator: Boolean = profile?.roles?.role == "operator"
    val canAccessMenu: Boolean = profile?.roles?.role in listOf("sa", "admin", "super")

    when (screen) {
        "login" -> LoginScreen(
            onLoginSuccess = { p ->
                profile = p
                screen = if (p.roles.role == "operator") "vehicles" else "menu"
            }
        )
        "menu" -> MainMenuScreen(
            onGoChecklist = { screen = "vehicles" },
            onGoDashboard = { screen = "dashboard" },
            onGoMaintenance = { screen = "maintenance" },
            onGoReport = { screen = "report" }
        )
        "vehicles" -> VehicleListScreen(
            shiftResult = shiftResult,
            checkedVehicles = checkedVehicles,
            onVehicleClick = { v -> selectedV = v; screen = "checklist" },
            onScan = { screen = "scan" },
            onBack = {
                screen = if (isOperator) "login" else "menu"
            }
        )
        "scan" -> ScannerScreen(
            onVehicleScanned = { chassisNo ->
                val found = MockData.vehicles.firstOrNull { it.chassis_no == chassisNo }
                if (found != null) {
                    selectedV = found
                    screen = "checklist"
                }
            },
            onBack = { screen = "vehicles" }
        )
        "checklist" -> NewChecklistScreen(
            initialVehicle = selectedV,
            onBack = { screen = "vehicles" },
            onChecklistSaved = { chassisNo, info ->
                checkedVehicles[chassisNo] = info
            }
        )
        "dashboard" -> SupervisorDashboardScreen(onBack = { screen = "menu" })
        "maintenance" -> MaintenanceScreen(onBack = { screen = "menu" })
        "report" -> ReportScreen(onBack = { screen = "menu" })
    }
}

// =====================================================================
// 🏠 MAIN MENU
// =====================================================================
@Composable
fun MainMenuScreen(onGoChecklist: () -> Unit, onGoDashboard: () -> Unit, onGoMaintenance: () -> Unit, onGoReport: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("🚛 IRPC Forklift") }) }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🚛", fontSize = 64.sp)
            Spacer(Modifier.height(8.dp))
            Text("IRPC Forklift Management", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(4.dp))
            Text("ระบบตรวจสอบรถโฟร์คลิฟท์", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(40.dp))

            // Menu buttons
            MenuButton("📋 ทำ Checklist", "เข้าสู่ระบบตรวจเช็ครถ", Color(0xFF1E40AF), onClick = onGoChecklist)
            Spacer(Modifier.height(12.dp))
            MenuButton("📊 Supervisor Dashboard", "ดูภาพรวม สถิติ รถค้าง", Color(0xFF059669), onClick = onGoDashboard)
            Spacer(Modifier.height(12.dp))
            MenuButton("🔧 ซ่อมบำรุง", "ตารางงานซ่อมและการดูแลรักษา", Color(0xFFD97706), onClick = onGoMaintenance)
            Spacer(Modifier.height(12.dp))
            MenuButton("📈 รายงาน", "สถิติและรายงานประจำเดือน", Color(0xFF7C3AED), onClick = onGoReport)

            Spacer(Modifier.height(40.dp))
            Text("v1.0.0-dev", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun MenuButton(title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(72.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontWeight = FontWeight.Bold, color = color, fontSize = 16.sp, modifier = Modifier.weight(1f))
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = color.copy(alpha = 0.7f))
        }
    }
}

// =====================================================================
// 🚛 VEHICLE LIST
// =====================================================================
@Composable
fun VehicleListScreen(
    shiftResult: ShiftResult? = null,
    checkedVehicles: Map<String, String> = emptyMap(),
    onVehicleClick: (Vehicle) -> Unit,
    onScan: () -> Unit,
    onBack: () -> Unit,
) {
    val deptNames = mapOf(
        "dept-bagging-pp12" to "PP12 Bagging", "dept-bagging-pp3" to "PP3 Bagging",
        "dept-bagging-ppe" to "PPE Bagging", "dept-bagging-ppc" to "PPC Bagging",
        "dept-bagging-hd" to "HD Bagging", "dept-sealroom" to "Seal Room",
    )
    val deptIcons = mapOf(
        "dept-bagging-pp12" to "🏭", "dept-bagging-pp3" to "🏭",
        "dept-bagging-ppe" to "🏭", "dept-bagging-ppc" to "🏭",
        "dept-bagging-hd" to "🏭", "dept-sealroom" to "🚪",
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
                }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp)) {
            // Summary header with shift info
            item {
                val shiftLabel = shiftResult?.shift?.label ?: "?"
                val teamLabel = shiftResult?.team ?: ""
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (checkedCount == vehicles.size)
                            Color(0xFF16A34A).copy(alpha = 0.1f)
                        else
                            MaterialTheme.colorScheme.primaryContainer,
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        Text(
                            text = "⏰ กะ$shiftLabel (กะ $teamLabel)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (checkedCount == vehicles.size)
                                "✅ ตรวจครบแล้วทุกคัน ($checkedCount/${vehicles.size})"
                            else
                                "ตรวจแล้ว $checkedCount/${vehicles.size} คัน",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (checkedCount == vehicles.size) Color(0xFF16A34A)
                                    else Color(0xFFF59E0B),
                        )
                    }
                }
            }

            // Vehicle list grouped by department — each group in a Card
            vehicles.groupBy { it.department_id }.forEach { (deptId, list) ->
                item {
                    Spacer(Modifier.height(4.dp))
                    Card(
                        colors = CardDefaults.cardColors(
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
                                        color = if (deptChecked == list.size) Color(0xFF16A34A)
                                                else MaterialTheme.colorScheme.onSurfaceVariant,
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
fun VehicleCard(v: Vehicle, checkedInfo: String?, onClick: () -> Unit) {
    val isChecked = checkedInfo != null

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = if (isChecked) Color(0xFF16A34A).copy(alpha = 0.04f)
                else Color.Transparent,
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

// =====================================================================
// 📋 CHECKLIST (OLD — replaced by NewChecklistScreen from feature/checklist)
// =====================================================================
