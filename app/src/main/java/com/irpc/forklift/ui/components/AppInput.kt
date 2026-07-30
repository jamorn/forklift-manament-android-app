// 📁 ui/components/AppInput.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ⌨️ App Input — Reusable TextField
 *
 * @Composable
 * fun AppInput(
 *     value: String,
 *     onValueChange: (String) -> Unit,
 *     label: String,
 *     modifier: Modifier = Modifier,
 *     enabled: Boolean = true,
 *     isError: Boolean = false,
 *     errorMessage: String? = null,
 *     singleLine: Boolean = true,
 * ) {
 *     Column(modifier = modifier.fillMaxWidth()) {
 *         OutlinedTextField(
 *             value = value,
 *             onValueChange = onValueChange,
 *             label = { Text(label) },
 *             modifier = Modifier.fillMaxWidth(),
 *             enabled = enabled,
 *             isError = isError,
 *             singleLine = singleLine,
 *             shape = MaterialTheme.shapes.medium,
 *         )
 *         if (isError && errorMessage != null) {
 *             Text(errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
 *         }
 *     }
 * }
 */
object AppInput {
    // TODO: implement
}
