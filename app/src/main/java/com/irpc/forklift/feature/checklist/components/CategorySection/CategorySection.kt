// 📁 feature/checklist/components/CategorySection/CategorySection.kt
package com.irpc.forklift.feature.checklist.components.CategorySection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.data.mock.MockData.ChecklistItem
import com.irpc.forklift.feature.checklist.components.CheckItemRow.CheckItemRow

/**
 * 📂 Category Section — หมวดหมู่ในการตรวจเช็ค
 *
 * แสดงเป็น **Card 1 แผ่นต่อหมวด** แยกชัด:
 * - Header (พื้นสีเด่น): ชื่อหมวดข้อมูลอย่างเดียว (ไม่มี badge)
 * - Child: CheckItemRow แต่ละรายการ คั่นด้วย Divider + มีหมายเลขลำดับ (index+1)
 *
 * @param title ชื่อหมวดหมู่
 * @param items รายการตรวจในหมวดนี้
 * @param results ผลตรวจ (itemId → "pass"/"fail")
 * @param remarks หมายเหตุ (itemId → remark)
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
    Card(
        modifier = Modifier.padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column {
            // ============ Header ============
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                )
            }

            // ============ Child Items (มีหมายเลขลำดับ index+1) ============
            items.forEachIndexed { idx, item ->
                CheckItemRow(
                    index = idx + 1,
                    item = item,
                    result = results[item.id],
                    remark = remarks[item.id] ?: "",
                    onChecked = { result -> onItemChecked(item.id, result) },
                    onRemark = { remark -> onItemRemark(item.id, remark) },
                )
                // เส้นแบ่งระหว่างรายการ (ยกเว้นรายการสุดท้าย)
                if (idx != items.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                    )
                }
            }
        }
    }
}
