// 📁 ui/components/AppButton.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 🔘 App Button — Reusable Primary/Secondary/Danger Button
 *
 * @Composable
 * fun AppButton(
 *     text: String,
 *     onClick: () -> Unit,
 *     modifier: Modifier = Modifier,
 *     variant: ButtonVariant = ButtonVariant.Primary,
 *     enabled: Boolean = true,
 *     isLoading: Boolean = false,
 * ) {
 *     val colors = when (variant) {
 *         ButtonVariant.Primary -> ButtonDefaults.buttonColors(
 *             containerColor = MaterialTheme.colorScheme.primary
 *         )
 *         ButtonVariant.Danger -> ButtonDefaults.buttonColors(
 *             containerColor = MaterialTheme.colorScheme.error
 *         )
 *         ButtonVariant.Outline -> ButtonDefaults.outlinedButtonColors()
 *     }
 *
 *     Button(
 *         onClick = onClick,
 *         modifier = modifier.height(48.dp),
 *         colors = colors,
 *         enabled = enabled,
 *         shape = MaterialTheme.shapes.medium,
 *     ) {
 *         if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
 *         else Text(text, style = MaterialTheme.typography.labelLarge)
 *     }
 * }
 *
 * enum class ButtonVariant { Primary, Danger, Outline }
 */
object AppButton {
    // TODO: implement
}
