// 📁 feature/auth/LoginUiState.kt
package com.irpc.forklift.feature.auth

import com.irpc.forklift.core.domain.model.UserProfile

/**
 * 🔐 Login UI State
 */
data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isDevMode: Boolean = true,  // BuildConfig.DEBUG
    val error: String? = null,
    val mockUsers: List<UserProfile> = emptyList(),
    val profile: UserProfile? = null,
)
