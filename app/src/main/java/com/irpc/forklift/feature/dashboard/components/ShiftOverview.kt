// 📁 feature/dashboard/components/ShiftOverview.kt
package com.irpc.forklift.feature.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.domain.model.ShiftCode

/**
 * ⏰ Shift Overview Card
 *
 * แสดงกะปัจจุบัน + จำนวนรถที่ตรวจแล้ว/ทั้งหมด
 *
 * @param shift กะปัจจุบัน
 * @param checkedCount จำนวนที่ตรวจแล้ว
 * @param totalCount จำนวนทั้งหมด
 */
@Composable
fun ShiftOverview(
    shift: ShiftCode,
    checkedCount: Int,
    totalCount: Int,
) {
    val shiftLabel =
        when (shift) {
            ShiftCode.M -> "กะเช้า"
            ShiftCode.E -> "กะบ่าย"
            ShiftCode.N -> "กะดึก"
        }

    val shiftEmoji =
        when (shift) {
            ShiftCode.M -> "🌅"
            ShiftCode.E -> "🌇"
            ShiftCode.N -> "🌙"
        }

    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(shiftEmoji, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = shiftLabel,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "ตรวจแล้ว $checkedCount / $totalCount คัน",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
