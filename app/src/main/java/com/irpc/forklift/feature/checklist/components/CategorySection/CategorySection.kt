// components/CategorySection/CategorySection.kt
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
import com.irpc.forklift.core.domain.model.ChecklistComponent
import com.irpc.forklift.core.domain.model.CheckingPoint
import com.irpc.forklift.core.domain.model.pointKey
import com.irpc.forklift.feature.checklist.components.CheckItemRow.CheckItemRow

/**
 * 📂 Component Section — หมวด (component) ตาม redesign
 *
 * แสดงเป็น **Card 1 แผ่นต่อ component** แยกชัด:
 * - Header (พื้นสีเด่น): ชื่อหมวด (component_th) — ไม่มี badge
 * - Child: CheckItemRow แต่ละ checking_point (คั่น Divider + แสดงเลขลำดับ 1..N)
 *
 * ⭐ เลขลำดับที่แสดง = **idx+1 (1..N ภายในหมวด)** — user เห็นเลขเรียง 1,2,3..
 *   **ไม่ใช้ `point.no`** (Step 10) เพราะเลข 10,20,30 นับยาก/งง user
 *   แต่ `no` ยังเป็น key ของผลตรวจ (`pointKey = "<comp>-<no>"`) — เก็บเป็น identity ใน background
 *
 * @param component หมวดตรวจ (component 1-5)
 * @param points รายการตรวจในหมวดนี้ (checking_points ที่ is_active)
 * @param results ผลตรวจ (key "<comp>-<no>" → true/false)
 * @param remarks หมายเหตุ (key → remark)
 */
@Composable
fun CategorySection(
    component: ChecklistComponent,
    points: List<CheckingPoint>,
    results: Map<String, Boolean>,
    remarks: Map<String, String>,
    onItemChecked: (String, Boolean) -> Unit,
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
                    text = component.component_th,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                )
            }

            // ============ Child Items (key = "<comp>-<no>") ============
            points.forEachIndexed { idx, point ->
                val key = component.pointKey(point)
                // ส่ง index = idx+1 (1..N) ให้ CheckItemRow แสดงเลขเรียง user-friendly
                // (ไม่ใช้ point.no ซึ่งเป็น Step 10 — ดู comment ใน CheckItemRow)
                CheckItemRow(
                    index = idx + 1,
                    point = point,
                    result = results[key],
                    remark = remarks[key] ?: "",
                    onChecked = { result -> onItemChecked(key, result) },
                    onRemark = { remark -> onItemRemark(key, remark) },
                )
                // เส้นแบ่งระหว่างรายการ (ยกเว้นรายการสุดท้าย)
                if (idx != points.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                    )
                }
            }
        }
    }
}

