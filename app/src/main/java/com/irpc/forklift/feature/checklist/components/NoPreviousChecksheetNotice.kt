// 📁 feature/checklist/components/NoPreviousChecksheetNotice.kt
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
 * ℹ️ Floating Label — แจ้งว่า "กะก่อนหน้าไม่ได้ตรวจ"
 *
 * แสดงเมื่อ query firebase (date+shift) ไม่เจอ document ↑
 * → ไม่มีข้อมูลกะก่อนหน้า → ให้ operator ตรวจด้วยสายตาเอง
 *
 * (Doc 18 — กรณีที่ 2: firebase ไม่เจอ = กะก่อนไม่ตรวจ)
 */
@Composable
fun NoPreviousChecksheetNotice() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("⚠️", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "ไม่พบข้อมูลกะก่อนหน้า (กะก่อนไม่ได้ตรวจ) — กรุณากรอกผลตรวจด้วยสายตาจริง",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
