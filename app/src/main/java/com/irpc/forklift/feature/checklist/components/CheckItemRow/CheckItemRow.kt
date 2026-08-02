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
import com.irpc.forklift.core.data.mock.MockData.ChecklistItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckItemRow(
    index: Int,
    item: ChecklistItem,
    result: String?,
    remark: String,
    onChecked: (String) -> Unit,
    onRemark: (String) -> Unit,
) {
    val isFail = result == "fail"

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
            // หมายเลขลำดับ (index+1) — บอกว่ามีทั้งหมดกี่ items ในหมวดนี้
            Text(
                text = "$index",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 12.dp),
            )
            Text(
                text = item.label,
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
                    selected = result == "pass",
                    onClick = { onChecked("pass") },
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
                    onClick = { onChecked("fail") },
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
