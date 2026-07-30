@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift.feature.maintenance

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MaintenanceJob(
    val date: String,
    val flno: String,
    val chassis: String,
    val dept: String,
    val type: String,       // "ซ่อมตามแผน" หรือ "ซ่อมฉุกเฉิน"
    val task: String,
    val status: String,     // "pending", "in_progress", "done"
    val assignedTo: String,
)

@Composable
fun MaintenanceScreen(onBack: () -> Unit) {
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("📅 ซ่อมตามแผน", "🔧 ซ่อมฉุกเฉิน", "📋 ประวัติ")

    val plannedJobs = listOf(
        MaintenanceJob("29/07/68", "FL-1202", "FK-002", "PP12 Bagging", "ซ่อมตามแผน", "เช็คระยะ 500 ชม. เปลี่ยนถ่ายน้ำมันเครื่อง กรองอากาศ กรองไฮดรอลิก", "pending", "แผนกซ่อมบำรุง"),
        MaintenanceJob("30/07/68", "FL-1207", "FK-007", "Seal Room", "ซ่อมตามแผน", "เปลี่ยนน้ำมันไฮดรอลิก ตรวจสอบระบบไฮดรอลิกทั้งหมด", "pending", "อู่เจริญยนต์"),
        MaintenanceJob("01/08/68", "FL-1205", "FK-005", "PPC Bagging", "ซ่อมตามแผน", "เปลี่ยนยางทั้ง 4 เส้น ตั้งศูนย์ถ่วง", "pending", "อู่เจริญยนต์"),
        MaintenanceJob("03/08/68", "FL-1201", "FK-001", "PP12 Bagging", "ซ่อมตามแผน", "ตรวจสอบระบบเบรก เปลี่ยนผ้าเบรก", "pending", "แผนกซ่อมบำรุง"),
        MaintenanceJob("05/08/68", "FL-1206", "FK-006", "HD Bagging", "ซ่อมตามแผน", "เปลี่ยนแบตเตอรี่ ตรวจสอบระบบไฟ", "pending", "อู่เจริญยนต์"),
    )

    val emergencyJobs = listOf(
        MaintenanceJob("28/07/68", "FL-1204", "FK-004", "PPE Bagging", "ซ่อมฉุกเฉิน", "ยางหน้าแตกขณะปฏิบัติงาน เปลี่ยนยางหน้า 2 เส้น", "done", "อู่เจริญยนต์"),
        MaintenanceJob("27/07/68", "FL-1203", "FK-003", "PP3 Bagging", "ซ่อมฉุกเฉิน", "โซ่ยกขาด เปลี่ยนโซ่ยกและปรับตั้ง", "done", "แผนกซ่อมบำรุง"),
        MaintenanceJob("25/07/68", "FL-1208", "FK-008", "PP12 Bagging", "ซ่อมฉุกเฉิน", "น้ำมันไฮดรอลิกรั่ว ซ่อมซีลกระบอกไฮดรอลิก", "done", "อู่เจริญยนต์"),
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("🔧 ซ่อมบำรุง") }, navigationIcon = { TextButton(onClick = onBack) { Text("← กลับ") } }) }
    ) { pad ->
        Column(Modifier.fillMaxSize().padding(pad)) {
            TabRow(selectedTabIndex = tab) {
                tabs.forEachIndexed { i, t -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t, fontSize = 13.sp) }) }
            }
            when (tab) {
                0 -> PlannedTab(plannedJobs)
                1 -> EmergencyTab(emergencyJobs)
                2 -> HistoryTab(plannedJobs + emergencyJobs)
            }
        }
    }
}

@Composable
fun PlannedTab(jobs: List<MaintenanceJob>) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E40AF).copy(alpha = 0.1f))) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("📅", fontSize = 24.sp); Spacer(Modifier.width(12.dp))
                    Column {
                        Text("งานซ่อมตามแผน", fontWeight = FontWeight.Bold, color = Color(0xFF1E40AF))
                        Text("จำนวน ${jobs.size} รายการ ที่ต้องดำเนินการ", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        items(jobs) { job ->
            JobCard(job)
        }
    }
}

@Composable
fun EmergencyTab(jobs: List<MaintenanceJob>) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFDC2626).copy(alpha = 0.1f))) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🔧", fontSize = 24.sp); Spacer(Modifier.width(12.dp))
                    Column {
                        Text("ซ่อมฉุกเฉิน", fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                        Text("จำนวน ${jobs.size} ครั้ง ที่เกิดขึ้น", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        items(jobs) { job ->
            JobCard(job)
        }
    }
}

@Composable
fun HistoryTab(jobs: List<MaintenanceJob>) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item { Text("📋 ประวัติการซ่อมบำรุงทั้งหมด", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium) }
        item { Spacer(Modifier.height(8.dp)) }
        items(jobs.sortedByDescending { it.date }) { job ->
            JobCard(job)
        }
    }
}

@Composable
fun JobCard(job: MaintenanceJob) {
    val statusColor = when (job.status) {
        "pending" -> Color(0xFFF59E0B)
        "in_progress" -> Color(0xFF1E40AF)
        "done" -> Color(0xFF10B981)
        else -> Color(0xFF64748B)
    }
    val statusText = when (job.status) {
        "pending" -> "รอดำเนินการ"
        "in_progress" -> "กำลังซ่อม"
        "done" -> "เสร็จแล้ว"
        else -> job.status
    }

    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(Modifier.padding(12.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("${job.flno} · ${job.chassis}", fontWeight = FontWeight.Bold)
                    Text(job.dept, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Surface(color = statusColor.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
                    Text(statusText, color = statusColor, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            Spacer(Modifier.height(4.dp))
            Text("📌 ${job.task}", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(2.dp))
            Row {
                Text("📅 ${job.date}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.width(16.dp))
                Text("👤 ${job.assignedTo}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (job.type == "ซ่อมฉุกเฉิน") {
                Spacer(Modifier.height(4.dp))
                Surface(color = Color(0xFFDC2626).copy(alpha = 0.1f), shape = MaterialTheme.shapes.small) {
                    Text("⚠️ ซ่อมฉุกเฉิน", color = Color(0xFFDC2626), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
        }
    }
}
