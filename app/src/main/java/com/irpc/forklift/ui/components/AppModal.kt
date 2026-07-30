// 📁 ui/components/AppModal.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * 📋 App Modal — Reusable Dialog
 *
 * @param title หัวข้อ
 * @param message ข้อความ (ใช้เมื่อ content = null)
 * @param onConfirm callback ยืนยัน
 * @param onDismiss callback ยกเลิก
 * @param confirmText ข้อความปุ่มยืนยัน (default: "ยืนยัน")
 * @param dismissText ข้อความปุ่มยกเลิก (default: "ยกเลิก")
 * @param content composable content แทน message (optional)
 */
@Composable
fun AppModal(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "ยืนยัน",
    dismissText: String = "ยกเลิก",
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
                if (content != null) {
                    content()
                } else {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(dismissText)
                    }
                    TextButton(onClick = onConfirm) {
                        Text(confirmText)
                    }
                }
            }
        }
    }
}

