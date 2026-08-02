// 📁 feature/auth/LoginViewModel.kt
package com.irpc.forklift.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irpc.forklift.core.data.repository.*
import com.irpc.forklift.core.domain.repository.*
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
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val sessionRepository: SessionRepository,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                LoginUiState(mockUsers = AuthRepositoryImpl.MOCK_USERS.values.toList()),
            )
        val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

        fun loginWithGoogle() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val result = authRepository.signInWithGoogle()
                result
                    .onSuccess {
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
                result
                    .onSuccess { profile ->
                        // เซฟ profile ไว้ใน session (login สำเร็จ) — ให้ทุก VM ใช้ต่อได้
                        sessionRepository.setProfile(profile)
                        _uiState.value =
                            _uiState.value.copy(
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

        /** reset state กลับเป็นหน้า login เริ่มต้น — ใช้ตอนออกจากระบบ */
        fun resetState() {
            _uiState.value =
                LoginUiState(mockUsers = AuthRepositoryImpl.MOCK_USERS.values.toList())
        }
    }
