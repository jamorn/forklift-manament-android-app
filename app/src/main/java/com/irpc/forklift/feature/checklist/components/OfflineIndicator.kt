// 📁 feature/checklist/components/OfflineIndicator.kt
package com.irpc.forklift.feature.checklist.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 📡 Offline Indicator
 *
 * แสดงสถานะ offline เมื่อไม่มี internet connection
 *
 * @param isOffline true = offline, false = ซ่อน
 */
@Composable
fun OfflineIndicator(isOffline: Boolean) {
    if (isOffline) {
        Surface(
            color = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("📡", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = " คุณกำลังทำงานในโหมด Offline",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }
    }
}
