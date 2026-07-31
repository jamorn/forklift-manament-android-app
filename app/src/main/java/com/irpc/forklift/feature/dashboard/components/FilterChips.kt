// 📁 feature/dashboard/components/FilterChips.kt
package com.irpc.forklift.feature.dashboard.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 🏷️ Filter Chips — กรองตาม department
 *
 * @param departments รายการแผนก (id → name)
 * @param selectedId id ที่เลือกอยู่ (null = ทั้งหมด)
 * @param onSelected callback เมื่อเลือก
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    departments: List<Pair<String, String>>,
    selectedId: String?,
    onSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterChip(
            selected = selectedId == null,
            onClick = { onSelected(null) },
            label = { Text("ทั้งหมด") },
        )
        departments.forEach { (id, name) ->
            FilterChip(
                selected = selectedId == id,
                onClick = { onSelected(id) },
                label = { Text(name) },
            )
        }
    }
}
