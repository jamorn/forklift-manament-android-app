// 📁 ui/components/LoadingSpinner.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * ⏳ Loading Spinner — Full Screen
 *
 * แสดง CircularProgressIndicator กึ่งกลางจอ
 */
@Composable
fun LoadingSpinner(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/**
 * ⏳ Small Spinner — Inline
 *
 * ใช้ในปุ่มหรือพื้นที่เล็ก
 */
@Composable
fun SmallSpinner(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}
