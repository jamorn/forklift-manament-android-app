// 📁 feature/checklist/components/CheckItemRow/CheckItemRow.kt
package com.irpc.forklift.feature.checklist.components.CheckItemRow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irpc.forklift.core.domain.model.CheckingPoint

/**
 * 📋 Check Item Row — หนึ่งรายการตรวจ (checking_point) ตาม redesign
 *
 * - result เป็น boolean: `null`=ยังไม่เลือก, `true`=ปกติ, `false`=ชำรุด
 * - เลือก "ปกติ (✓)" หรือ "ชำรุด (✗)"
 * - ชำรุด → ต้องกรอก remark (ระบุปัญหา)
 *
 * ⭐ เรื่องเลขลำดับ (index) ที่แสดงด้านซ้าย:
 * - ใช้ **index = 1..N** เรียงตามลำดับภายใน component (ส่งมาจาก CategorySection = idx+1)
 *   **ไม่ใช้ `point.no`** เพราะ no เป็น Step 10 (10,20,30...) ที่ internal/backend ใช้
 *   เป็น identity ของ item (key "<comp>-<no>") — user ควรเห็นเลขเรียง 1,2,3.. ง่ายต่อการนับ
 *   เช่น ระบบเครื่องยนต์ มี 11 item → แสดง 1..11 (ไม่ใช่ 10,20,30...110)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckItemRow(
    index: Int,
    point: CheckingPoint,
    result: Boolean?,
    remark: String,
    onChecked: (Boolean) -> Unit,
    onRemark: (String) -> Unit,
) {
    val isFail = result == false

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ลำดับที่เห็น = index 1..N (เรียงภายในหมวด) — ไม่ใช้ point.no (Step 10)
            // เหตุผล: เลข 10,20,30 ตอนแสดงงง / นับยาก → user ควรเห็น 1,2,3...
            // (index ส่งมาจาก CategorySection ด้วย idx+1)
            Text(
                text = "$index",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 12.dp),
            )
            Text(
                text = point.item_th,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color =
                    if (isFail) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
            Row {
                FilterChip(
                    selected = result == true,
                    onClick = { onChecked(true) },
                    label = { Text("✓") },
                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF16A34A),
                            selectedLabelColor = Color.White,
                        ),
                )
                Spacer(Modifier.width(4.dp))
                FilterChip(
                    selected = isFail,
                    onClick = { onChecked(false) },
                    label = { Text("✗") },
                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFDC2626),
                            selectedLabelColor = Color.White,
                        ),
                )
            }
        }
        if (isFail) {
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = remark,
                onValueChange = onRemark,
                label = { Text("ระบุปัญหา", fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFEF4444),
                        unfocusedBorderColor = Color(0xFFEF4444).copy(alpha = 0.5f),
                    ),
            )
        }
    }
}
