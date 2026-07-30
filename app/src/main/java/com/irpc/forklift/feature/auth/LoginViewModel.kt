// 📁 feature/auth/LoginViewModel.kt
package com.irpc.forklift.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
 * @HiltViewModel
 * class LoginViewModel @Inject constructor(
 *     private val authRepository: AuthRepository,
 * ) : ViewModel() {
 *
 *     private val _uiState = MutableStateFlow(LoginUiState())
 *     val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
 *
 *     fun loginWithGoogle() {
 *         viewModelScope.launch {
 *             _uiState.value = _uiState.value.copy(isLoading = true)
 *             val result = authRepository.signInWithGoogle()
 *             result.onSuccess {
 *                 _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
 *             }.onFailure {
 *                 _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
 *             }
 *         }
 *     }
 *
 *     fun mockLogin(email: String) {
 *         viewModelScope.launch {
 *             val result = authRepository.mockLogin(email)
 *             result.onSuccess {
 *                 _uiState.value = _uiState.value.copy(isLoggedIn = true)
 *             }
 *         }
 *     }
 * }
 */
object LoginViewModel {
    // TODO: implement ViewModel
}
