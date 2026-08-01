// 📁 feature/menu/MainMenuScreen.kt
@file:OptIn(ExperimentalMaterial3Api::class)

package com.irpc.forklift.feature.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =====================================================================
// 🏠 MAIN MENU
// =====================================================================
@Composable
fun MainMenuScreen(
    onGoChecklist: () -> Unit,
    onGoDashboard: () -> Unit,
    onGoMaintenance: () -> Unit,
    onGoReport: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("🚛 IRPC Forklift") }) },
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("🚛", fontSize = 64.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "IRPC Forklift Management",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "ระบบตรวจสอบรถโฟร์คลิฟท์",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Spacer(Modifier.height(40.dp))

            // Menu buttons
            MenuButton("📋 ทำ Checklist", "เข้าสู่ระบบตรวจเช็ครถ", Color(0xFF1E40AF), onClick = onGoChecklist)
            Spacer(Modifier.height(12.dp))
            MenuButton("📊 Supervisor Dashboard", "ดูภาพรวม สถิติ รถค้าง", Color(0xFF059669), onClick = onGoDashboard)
            Spacer(Modifier.height(12.dp))
            MenuButton("🔧 ซ่อมบำรุง", "ตารางงานซ่อมและการดูแลรักษา", Color(0xFFB45309), onClick = onGoMaintenance)
            Spacer(Modifier.height(12.dp))
            MenuButton("📈 รายงาน", "สถิติและรายงานประจำเดือน", Color(0xFF6D28D9), onClick = onGoReport)

            Spacer(Modifier.height(40.dp))
            Text("v1.0.0-dev", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun MenuButton(
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(72.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = color),
    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.85f),
            )
        }
    }
}
