// 📁 ui/components/ToastHost.kt
package com.irpc.forklift.ui.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 🍞 Toast Host — Snackbar-based Notification
 *
 * ใช้ร่วมกับ SnackbarHostState ใน Scaffold
 *
 * @param snackbarHostState state ของ snackbar
 * @param modifier Modifier
 */
@Composable
fun ToastHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(hostState = snackbarHostState, modifier = modifier)
}
