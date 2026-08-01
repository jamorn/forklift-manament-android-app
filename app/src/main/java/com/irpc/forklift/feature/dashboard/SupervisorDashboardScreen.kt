@file:OptIn(ExperimentalMaterial3Api::class)

// 📁 feature/dashboard/SupervisorDashboardScreen.kt

package com.irpc.forklift.feature.dashboard

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DeptSummary(
    val department: String,
    val total: Int,
    val checked: Int,
    val missing: Int,
)

@Composable
fun SupervisorDashboardScreen(onBack: () -> Unit) {
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("ภาพรวม", "รถที่ค้าง", "ซ่อมบำรุง", "สถิติ")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📊 Supervisor Dashboard") },
                navigationIcon = { TextButton(onClick = onBack) { Text("← กลับ") } },
            )
        },
    ) { pad ->
        Column(Modifier.fillMaxSize().padding(pad)) {
            TabRow(
                selectedTabIndex = tab,
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                tabs.forEachIndexed { i, t ->
                    Tab(
                        selected = tab == i,
                        onClick = { tab = i },
                        text = {
                            Text(
                                t,
                                fontSize = 13.sp,
                                color =
                                    if (tab == i) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                fontWeight = if (tab == i) FontWeight.Bold else FontWeight.Normal,
                            )
                        },
                    )
                }
            }
            when (tab) {
                0 -> OverviewTab()
                1 -> MissingTab()
                2 -> MaintenanceTab()
                3 -> StatsTab()
            }
        }
    }
}

@Composable
fun OverviewTab() {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("🚛 รวม", "8 คัน", Color(0xFF1E40AF), Modifier.weight(1f))
                StatCard("✅ ตรวจแล้ว", "5 คัน", Color(0xFF10B981), Modifier.weight(1f))
                StatCard("❌ ค้าง", "2 คัน", Color(0xFFEF4444), Modifier.weight(1f))
                StatCard("🔧 ซ่อม", "1 คัน", Color(0xFFF59E0B), Modifier.weight(1f))
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFB45309))) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🕐", fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("กะเช้า • 28 ก.ค. 68", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("ผู้ปฏิบัติงาน: สมชาย, สมหญิง, สมศักดิ์", color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text("สถานะรายแผนก", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text("แผนก", Modifier.weight(2f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                        Text(
                            "รวม",
                            Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            "ตรวจ",
                            Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            "ค้าง",
                            Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Divider(Modifier.padding(vertical = 4.dp))
                    listOf(
                        "PP12 Bagging" to "3/3/0",
                        "PP3 Bagging" to "1/0/1",
                        "PPE Bagging" to "1/1/0",
                        "PPC Bagging" to "1/1/0",
                        "HD Bagging" to "1/0/1",
                        "Seal Room" to "1/1/0",
                    ).forEach { (dept, data) ->
                        val p = data.split("/")
                        Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                            Text(dept, Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                            Text(p[0], Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                            Text(
                                p[1],
                                Modifier.weight(1f),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color =
                                    if (p[1] ==
                                        p[0]
                                    ) {
                                        Color(0xFF10B981)
                                    } else {
                                        Color(0xFFF59E0B)
                                    },
                            )
                            Text(
                                p[2],
                                Modifier.weight(1f),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color =
                                    if (p[2] !=
                                        "0"
                                    ) {
                                        Color(0xFFEF4444)
                                    } else {
                                        Color.Unspecified
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    c: Color,
    mod: Modifier,
) {
    Card(modifier = mod, colors = CardDefaults.cardColors(containerColor = c)) {
        Column(Modifier.padding(8.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun MissingTab() {
    data class Mv(
        val flno: String,
        val chassis: String,
        val dept: String,
    )
    val list = listOf(Mv("FL-1203", "FK-003", "PP3 Bagging"), Mv("FL-1206", "FK-006", "HD Bagging"))
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFDC2626))) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("❌", fontSize = 24.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("รถที่ยังไม่ตรวจเช็ค", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("จำนวน ${list.size} คัน", color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        items(list) { m ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(m.flno, fontWeight = FontWeight.Bold)
                        Text("${m.chassis} · ${m.dept}", style = MaterialTheme.typography.bodySmall)
                    }
                    Text("กะเช้า", style = MaterialTheme.typography.labelSmall, color = Color(0xFFEF4444))
                }
            }
        }
    }
}

@Composable
fun MaintenanceTab() {
    data class Mt(
        val flno: String,
        val chassis: String,
        val dept: String,
        val task: String,
        val date: String,
        val by: String,
    )
    val list =
        listOf(
            Mt("FL-1204", "FK-004", "PPE Bagging", "เปลี่ยนยางหน้า 2 เส้น", "28/07/68", "อู่เจริญยนต์"),
            Mt("FL-1202", "FK-002", "PP12 Bagging", "เช็คระยะ 500 ชม.", "29/07/68", "แผนกซ่อม"),
            Mt("FL-1207", "FK-007", "Seal Room", "เปลี่ยนน้ำมันไฮดรอลิก", "30/07/68", "อู่เจริญยนต์"),
        )
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("📅 ตารางซ่อมบำรุง", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
        }
        items(list) { m ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(m.flno, fontWeight = FontWeight.Bold)
                        Text(m.dept, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(4.dp))
                        Text("งาน: ${m.task}", style = MaterialTheme.typography.bodySmall)
                        Text(
                            "${m.date} · ${m.by}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Badge(m = "ซ่อมบำรุง", c = Color(0xFFF59E0B))
                }
            }
        }
    }
}

@Composable
fun Badge(
    m: String,
    c: Color,
) {
    Surface(color = c, shape = MaterialTheme.shapes.small) {
        Text(
            text = m,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@Composable
fun StatsTab() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(Modifier.height(16.dp))
        Text("📈 สถิติกรกฎาคม 2568", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("อัตราการตรวจเช็ครายวัน", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("ส.1" to 80, "อ.2" to 95, "พ.3" to 70, "พฤ.4" to 100, "ศ.5" to 85, "ส.6" to 0, "อา.7" to 0).forEach { (d, p) ->
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                Modifier.height(if (p > 0) (p * 1.5f).dp else 6.dp).width(20.dp).background(
                                    when {
                                        p >= 90 -> Color(0xFF10B981)
                                        p >= 50 -> Color(0xFFF59E0B)
                                        else -> Color(0xFFEF4444)
                                    },
                                    MaterialTheme.shapes.small,
                                ),
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(d, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp)
                            if (p >
                                0
                            ) {
                                Text("$p%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("ตัวชี้วัดสำคัญ", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                KPI("อัตราการตรวจเช็ค", "85%", Color(0xFF10B981), "▲ +5% จากเดือนก่อน")
                KPI("รถเสียเฉลี่ย/วัน", "1.2 คัน", Color(0xFFF59E0B), "→ เท่าเดิม")
                KPI("เวลาซ่อมเฉลี่ย", "3.5 ชม.", Color(0xFFF59E0B), "▼ -0.5 ชม.")
                KPI("ค่าใช้จ่ายซ่อมบำรุง", "45,000 บ.", Color(0xFF1E40AF), "▲ +8%")
            }
        }
    }
}

@Composable
fun KPI(
    label: String,
    value: String,
    c: Color,
    change: String,
) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodySmall)
            Text(value, fontWeight = FontWeight.Bold, color = c, fontSize = 18.sp)
            Text(change, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
