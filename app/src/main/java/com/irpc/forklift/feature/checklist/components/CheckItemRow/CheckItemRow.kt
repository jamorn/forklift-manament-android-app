// 📁 feature/checklist/components/CheckItemRow/CheckItemRow.kt
package com.irpc.forklift.feature.checklist.components.CheckItemRow

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.data.mock.MockData.ChecklistItem

@OptIn(ExperimentalMaterial3Api::class)

/**
 * ✅ Check Item Row — แถวตรวจแต่ละข้อ
 *
 * @param item ข้อมูลรายการ
 * @param result ผลตรวจ ("pass" / "fail" / null = ยังไม่ตรวจ)
 * @param onChecked callback เมื่อเลือก (รับ "pass" หรือ "fail")
 */
@Composable
fun CheckItemRow(
    item: ChecklistItem,
    result: String?,
    onChecked: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
        )
        Row {
            FilterChip(
                selected = result == "pass",
                onClick = { onChecked("pass") },
                label = { Text("✓") },
            )
            Spacer(Modifier.width(4.dp))
            FilterChip(
                selected = result == "fail",
                onClick = { onChecked("fail") },
                label = { Text("✗") },
            )
        }
    }
}

