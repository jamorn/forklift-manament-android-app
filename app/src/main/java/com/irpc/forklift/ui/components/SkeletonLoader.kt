// 📁 ui/components/SkeletonLoader.kt
package com.irpc.forklift.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * 💀 Skeleton Loader — Placeholder ขณะโหลดข้อมูล
 *
 * @param modifier Modifier
 * @param height ความสูง (dp)
 * @param width ความกว้าง (dp, null = fillMaxWidth)
 */
@Composable
fun SkeletonLoader(
    modifier: Modifier = Modifier,
    height: Int = 16,
    width: Int? = null,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
    )

    Box(
        modifier =
            modifier
                .then(
                    if (width != null) {
                        Modifier.width(width.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    },
                ).height(height.dp)
                .clip(MaterialTheme.shapes.small)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
                ),
    )
}
