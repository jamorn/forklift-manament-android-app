// 📁 ui/components/AppBadge.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 🏷️ App Badge — Status/Type Badge
 *
 * Reusable badge component สำหรับแสดงสถานะต่างๆ
 *
 * @param text ข้อความที่แสดง
 * @param color สีของ badge (จะถูกทำ alpha 0.1f อัตโนมัติ)
 * @param modifier Modifier
 */
@Composable
fun AppBadge(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

