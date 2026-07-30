// 📁 feature/auth/LoginScreen.kt
package com.irpc.forklift.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.feature.auth.components.MockUserSelector

/**
 * 🔐 Login Screen — Jetpack Compose
 *
 * - Dev mode: แสดง MockUserSelector สำหรับทดสอบ
 * - Production: จะใช้ Google login (Coming Soon)
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (UserProfile) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    // Auto-navigate when login succeeds
    LaunchedEffect(uiState.isLoggedIn) {
        val profile = uiState.profile
        if (uiState.isLoggedIn && profile != null) {
            onLoginSuccess(profile)
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo emoji placeholder
            Text("🚛", fontSize = 72.sp)
            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                "IRPC Forklift Management",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "ระบบตรวจสอบรถโฟร์คลิฟท์",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(40.dp))

            // Error message
            if (uiState.error != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = uiState.error ?: "",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
            }

            // Dev Mode — Mock User Selector
            if (uiState.isDevMode) {
                Text(
                    "🛠️ DEV MODE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(8.dp))
                MockUserSelector(
                    users = uiState.mockUsers,
                    onUserSelected = { email -> viewModel.mockLogin(email) },
                )
            }
        }
    }
}

