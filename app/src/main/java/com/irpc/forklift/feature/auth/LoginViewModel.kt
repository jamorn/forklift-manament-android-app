// 📁 feature/auth/LoginViewModel.kt
package com.irpc.forklift.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irpc.forklift.core.data.repository.AuthRepositoryImpl
import com.irpc.forklift.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🔐 Login ViewModel
 *
 * - mockLogin: login แบบ dev (ใช้ MockUsers)
 * - loginWithGoogle: (placeholder สำหรับ Firebase Auth)
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LoginUiState(mockUsers = AuthRepositoryImpl.MOCK_USERS.values.toList())
    )
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginWithGoogle() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = authRepository.signInWithGoogle()
            result.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            }.onFailure {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    fun mockLogin(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = authRepository.mockLogin(email)
            result.onSuccess { profile ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    profile = profile,
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

