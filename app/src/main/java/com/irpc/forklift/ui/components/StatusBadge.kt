// 📁 ui/components/StatusBadge.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 📊 Status Badge — Checksheet/Maintenance Status
 *
 * @Composable
 * fun StatusBadge(
 *     status: String,
 *     modifier: Modifier = Modifier,
 * ) {
 *     val (color, label) = when (status) {
 *         "active" -> Color(0xFF10B981) to "active"
 *         "maintenance" -> Color(0xFFF59E0B) to "ซ่อมบำรุง"
 *         "inactive" -> Color(0xFFEF4444) to "ไม่ใช้งาน"
 *         "normal" -> Color(0xFF10B981) to "ปกติ"
 *         "unsafe" -> Color(0xFFEF4444) to "ไม่ปลอดภัย"
 *         "pending" -> Color(0xFFF59E0B) to "รอดำเนินการ"
 *         "completed" -> Color(0xFF10B981) to "เสร็จแล้ว"
 *         else -> Color(0xFF64748B) to status
 *     }
 *     AppBadge(text = label, color = color, modifier = modifier)
 * }
 */
object StatusBadge {
    // TODO: implement
}
