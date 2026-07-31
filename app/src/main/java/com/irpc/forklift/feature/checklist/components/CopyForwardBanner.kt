// 📁 feature/checklist/components/CopyForwardBanner.kt
package com.irpc.forklift.feature.checklist.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 🔄 Copy-Forward Banner
 *
 * แสดงข้อความ "อ้างอิงจากกะ X วันที่ Y" ที่ด้านบนฟอร์ม
 *
 * @param date วันที่ของ checksheet ก่อนหน้า
 * @param shift กะของ checksheet ก่อนหน้า
 */
@Composable
fun CopyForwardBanner(
    date: String,
    shift: String,
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("🔄", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "อ้างอิงจากกะ $shift วันที่ $date",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}
