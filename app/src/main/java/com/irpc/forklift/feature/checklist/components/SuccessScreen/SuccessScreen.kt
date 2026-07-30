// 📁 feature/checklist/components/SuccessScreen/SuccessScreen.kt
package com.irpc.forklift.feature.checklist.components.SuccessScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ✅ Step 3: Success Screen — บันทึกสำเร็จ
 *
 * @param onGoHome callback กลับหน้าแรก
 */
@Composable
fun SuccessScreen(
    onGoHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("✅", fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            "บันทึกข้อมูลสำเร็จ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "ผลการตรวจรถโฟร์คลิฟท์ถูกบันทึกเรียบร้อย",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onGoHome,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text("กลับหน้าแรก", fontWeight = FontWeight.Bold)
        }
    }
}

