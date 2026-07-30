// 📁 feature/auth/LoginScreen.kt
package com.irpc.forklift.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 🔐 Login Screen — Jetpack Compose
 *
 * @Composable
 * fun LoginScreen(
 *     onLoginSuccess: () -> Unit,
 *     viewModel: LoginViewModel = hiltViewModel(),
 * ) {
 *     val uiState by viewModel.uiState.collectAsState()
 *
 *     Scaffold {
 *         Column(
 *             modifier = Modifier.fillMaxSize().padding(24.dp),
 *             horizontalAlignment = Alignment.CenterHorizontally,
 *             verticalArrangement = Arrangement.Center,
 *         ) {
 *             // Logo + Title
 *             Icon(imageVector = Icons.Default.Shield, ...)
 *             Text("IRPC Forklift Management", style = MaterialTheme.typography.headlineMedium)
 *
 *             Spacer(Modifier.height(24.dp))
 *
 *             // Google Login Button
 *             Button(onClick = { viewModel.loginWithGoogle() }) {
 *                 Text("เข้าสู่ระบบด้วย Google")
 *             }
 *
 *             // Mock User Selector (Dev)
 *             if (uiState.isDevMode) {
 *                 MockUserSelector(
 *                     users = uiState.mockUsers,
 *                     onUserSelected = { viewModel.mockLogin(it) }
 *                 )
 *             }
 *         }
 *     }
 * }
 */
object LoginScreen {
    // TODO: implement Compose UI
}
