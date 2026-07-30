// 📁 ui/components/AppModal.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * 📋 App Modal — Reusable Dialog
 *
 * @Composable
 * fun AppModal(
 *     title: String,
 *     message: String,
 *     onConfirm: () -> Unit,
 *     onDismiss: () -> Unit,
 *     confirmText: String = "ยืนยัน",
 *     dismissText: String = "ยกเลิก",
 *     content: (@Composable ColumnScope.() -> Unit)? = null,
 * ) {
 *     AlertDialog(
 *         onDismissRequest = onDismiss,
 *         title = { Text(title, style = MaterialTheme.typography.titleLarge) },
 *         text = {
 *             if (content != null) content()
 *             else Text(message)
 *         },
 *         confirmButton = { TextButton(onClick = onConfirm) { Text(confirmText) } },
 *         dismissButton = { TextButton(onClick = onDismiss) { Text(dismissText) } },
 *     )
 * }
 */
object AppModal {
    // TODO: implement
}
