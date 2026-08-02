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
import androidx.compose.ui.unit.sp

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
        color = color,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}
