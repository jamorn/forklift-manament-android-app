@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift.feature.report

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

@Composable
fun ReportScreen(onBack: () -> Unit) {
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("📊 รายงานประจำเดือน", "📈 สถิติรายวัน", "📑 สรุปประจำปี")

    Scaffold(
        topBar = { TopAppBar(title = { Text("📈 รายงาน") }, navigationIcon = { TextButton(onClick = onBack) { Text("← กลับ") } }) },
    ) { pad ->
        Column(Modifier.fillMaxSize().padding(pad)) {
            TabRow(selectedTabIndex = tab) {
                tabs.forEachIndexed { i, t -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t, fontSize = 13.sp) }) }
            }
            when (tab) {
                0 -> MonthlyReportTab()
                1 -> DailyStatsTab()
                2 -> YearlySummaryTab()
            }
        }
    }
}

@Composable
fun MonthlyReportTab() {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E40AF).copy(alpha = 0.1f))) {
                Column(Modifier.padding(16.dp)) {
                    Text("📊 รายงานประจำเดือน กรกฎาคม 2568", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("🚛 รถทั้งหมด", "8 คัน", Modifier.weight(1f))
                        StatItem("✅ ตรวจเช็ค", "85%", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("🔧 ซ่อมบำรุง", "12 ครั้ง", Modifier.weight(1f))
                        StatItem("⚠️ ฉุกเฉิน", "3 ครั้ง", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("⏱ หยุดซ่อมเฉลี่ย", "3.2 ชม.", Modifier.weight(1f))
                        StatItem("💰 ค่าใช้จ่าย", "45,200 บ.", Modifier.weight(1f))
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
        item {
            Text("รายละเอียดรายแผนก", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
        }

        items(
            listOf(
                "PP12 Bagging" to "ตรวจ 95% · ซ่อม 4 ครั้ง · ฉุกเฉิน 1",
                "PP3 Bagging" to "ตรวจ 78% · ซ่อม 2 ครั้ง · ฉุกเฉิน 1",
                "PPE Bagging" to "ตรวจ 100% · ซ่อม 1 ครั้ง · ฉุกเฉิน 1",
                "PPC Bagging" to "ตรวจ 88% · ซ่อม 1 ครั้ง · ฉุกเฉิน 0",
                "HD Bagging" to "ตรวจ 65% · ซ่อม 2 ครั้ง · ฉุกเฉิน 0",
                "Seal Room" to "ตรวจ 100% · ซ่อม 2 ครั้ง · ฉุกเฉิน 0",
            ),
        ) { (dept, detail) ->
            Card(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(dept, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(detail, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("→", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* TODO: export */ }, modifier = Modifier.fillMaxWidth()) { Text("📥 ดาวน์โหลดรายงาน PDF") }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    mod: Modifier,
) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small, modifier = mod) {
        Column(Modifier.padding(8.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun DailyStatsTab() {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("📈 สถิติการตรวจเช็ครายวัน — ก.ค. 68", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
        }

        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("อัตราการตรวจเช็ค (%)", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("จ.1" to 80, "อ.2" to 95, "พ.3" to 70, "พฤ.4" to 100, "ศ.5" to 85, "ส.6" to 0, "อา.7" to 0).forEach { (d, p) ->
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    Modifier.height(if (p > 0) (p * 1.8f).dp else 6.dp).width(24.dp).background(
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
                                    Text(
                                        "$p%",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 9.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
        item {
            Text("รายละเอียดรายวันล่าสุด", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
        }

        items(
            listOf(
                "28/07/68 (จ.)" to "ตรวจ 7/8 คัน · ผ่าน 95% · ซ่อม 1 ครั้ง",
                "27/07/68 (อา.)" to "วันหยุด - ไม่มีการตรวจ",
                "26/07/68 (ส.)" to "ตรวจ 3/8 คัน · ผ่าน 100%",
                "25/07/68 (ศ.)" to "ตรวจ 8/8 คัน · ผ่าน 100%",
                "24/07/68 (พฤ.)" to "ตรวจ 8/8 คัน · ผ่าน 88% · พบปัญหา 1 รายการ",
            ),
        ) { (day, detail) ->
            Card(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(day, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(detail, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun YearlySummaryTab() {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("📑 สรุปประจำปี 2568", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
        }

        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("ภาพรวมทั้งปี", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("🚛 รถทั้งหมด", "8 คัน", Modifier.weight(1f))
                        StatItem("✅ อัตราตรวจ", "87%", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("🔧 ซ่อมทั้งหมด", "78 ครั้ง", Modifier.weight(1f))
                        StatItem("⚠️ ฉุกเฉิน", "15 ครั้ง", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatItem("💰 ค่าใช้จ่ายรวม", "285,600 บ.", Modifier.weight(1f))
                        StatItem("⏱ MTBF", "245 ชม.", Modifier.weight(1f))
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Monthly Trend", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    // Monthly bars
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(
                            "ม.ค." to 82,
                            "ก.พ." to 85,
                            "มี.ค." to 88,
                            "เม.ย." to 80,
                            "พ.ค." to 85,
                            "มิ.ย." to 90,
                            "ก.ค." to 85,
                        ).forEach { (m, p) ->
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    Modifier.height((p * 0.8f).dp).width(28.dp).background(
                                        when {
                                            p >= 88 -> Color(0xFF10B981)
                                            p >= 80 -> Color(0xFFF59E0B)
                                            else -> Color(0xFFEF4444)
                                        },
                                        MaterialTheme.shapes.small,
                                    ),
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(m, style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                                Text("$p%", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, fontSize = 8.sp)
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))) {
                Column(Modifier.padding(16.dp)) {
                    Text("🚀 เป้าหมายปี 2569", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                    Spacer(Modifier.height(8.dp))
                    Text("• เพิ่มอัตราการตรวจเช็คเป็น ≥ 95%")
                    Text("• ลดการซ่อมฉุกเฉินลง 50%")
                    Text("• ลดค่าใช้จ่ายซ่อมบำรุง 10%")
                    Text("• MTBF ≥ 300 ชั่วโมง")
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* TODO: export */ }, modifier = Modifier.fillMaxWidth()) { Text("📥 ดาวน์โหลดรายงานสรุปปี") }
            Spacer(Modifier.height(24.dp))
        }
    }
}
