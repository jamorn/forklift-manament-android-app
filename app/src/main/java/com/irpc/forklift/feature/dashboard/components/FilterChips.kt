// 📁 feature/dashboard/components/FilterChips.kt
package com.irpc.forklift.feature.dashboard.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 🏷️ Filter Chips — กรองตาม department
 *
 * @Composable
 * fun FilterChips(
 *     departments: List<Pair<String, String>>,  // id → name
 *     selectedId: String?,
 *     onSelected: (String?) -> Unit,
 *     modifier: Modifier = Modifier,
 * ) {
 *     Row(
 *         modifier = modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
 *         horizontalArrangement = Arrangement.spacedBy(8.dp),
 *     ) {
 *         FilterChip(
 *             selected = selectedId == null,
 *             onClick = { onSelected(null) },
 *             label = { Text("ทั้งหมด") },
 *         )
 *         departments.forEach { (id, name) ->
 *             FilterChip(
 *                 selected = selectedId == id,
 *                 onClick = { onSelected(id) },
 *                 label = { Text(name) },
 *             )
 *         }
 *     }
 * }
 */
object FilterChips {
    // TODO: implement
}
