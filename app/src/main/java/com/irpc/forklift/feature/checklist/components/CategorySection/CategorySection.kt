// 📁 feature/checklist/components/CategorySection/CategorySection.kt
package com.irpc.forklift.feature.checklist.components.CategorySection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irpc.forklift.feature.checklist.components.CheckItemRow.CheckItemRow
import com.irpc.forklift.core.data.mock.MockData.ChecklistItem

/**
 * 📂 Category Section — หมวดหมู่ในการตรวจเช็ค
 *
 * @param title ชื่อหมวดหมู่
 * @param items รายการตรวจในหมวดนี้
 * @param results ผลตรวจ (itemId → "pass"/"fail")
 * @param onItemChecked callback (itemId, result)
 */
@Composable
fun CategorySection(
    title: String,
    items: List<ChecklistItem>,
    results: Map<String, String>,
    remarks: Map<String, String>,
    onItemChecked: (String, String) -> Unit,
    onItemRemark: (String, String) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(8.dp))
        items.forEach { item ->
            CheckItemRow(
                item = item,
                result = results[item.id],
                remark = remarks[item.id] ?: "",
                onChecked = { result -> onItemChecked(item.id, result) },
                onRemark = { remark -> onItemRemark(item.id, remark) },
            )
        }
    }
}

