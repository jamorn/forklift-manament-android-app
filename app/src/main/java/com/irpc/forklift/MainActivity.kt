@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.feature.auth.LoginScreen
import com.irpc.forklift.feature.checklist.ChecklistScreen as NewChecklistScreen
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
    var selectedV by remember { mutableStateOf<Vehicle?>(null) }

    when (screen) {
        "login" -> LoginScreen(
            onLoginSuccess = { screen = "menu" }
        )
        "menu" -> MainMenuScreen(
            onGoChecklist = { screen = "vehicles" },
            onGoDashboard = { screen = "dashboard" },
            onGoMaintenance = { screen = "maintenance" },
            onGoReport = { screen = "report" }
        )
        "vehicles" -> VehicleListScreen(
            onVehicleClick = { v -> selectedV = v; screen = "checklist" },
            onBack = { screen = "menu" }
        )
        "checklist" -> NewChecklistScreen()
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
fun VehicleListScreen(onVehicleClick: (Vehicle) -> Unit, onBack: () -> Unit) {
    val deptNames = mapOf(
        "dept-bagging-pp12" to "PP12 Bagging", "dept-bagging-pp3" to "PP3 Bagging",
        "dept-bagging-ppe" to "PPE Bagging", "dept-bagging-ppc" to "PPC Bagging",
        "dept-bagging-hd" to "HD Bagging", "dept-sealroom" to "Seal Room",
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("เลือกรถโฟร์คลิฟท์ 🚛") },
                navigationIcon = { TextButton(onClick = onBack) { Text("← กลับ") } }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            item { Text("Bagging (ไม่รวม SASB)", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp)) }
            MockData.vehicles.groupBy { it.department_id }.forEach { (deptId, list) ->
                item {
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(text = deptNames[deptId] ?: deptId, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    }
                }
                items(list) { v -> VehicleCard(v, onClick = { onVehicleClick(v) }) }
            }
        }
    }
}

@Composable
fun VehicleCard(v: Vehicle, onClick: () -> Unit) {
    Card(onClick = onClick, Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(v.current_flno, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text("${v.chassis_no} · ${v.vehicle_type}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            StatusBadge(v.status)
        }
    }
}

// =====================================================================
// 📋 CHECKLIST (OLD — replaced by NewChecklistScreen from feature/checklist)
// =====================================================================
