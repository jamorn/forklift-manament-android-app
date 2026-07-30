// 📁 core/domain/repository/AuthRepository.kt
package com.irpc.forklift.core.domain.repository

import com.irpc.forklift.core.domain.model.UserProfile

/**
 * 🔐 Auth Repository Interface
 */
interface AuthRepository {
    /** Login ด้วย Google */
    suspend fun signInWithGoogle(): Result<UserProfile>

    /** Login แบบ Mock (Dev) */
    suspend fun mockLogin(email: String): Result<UserProfile>

    /** Logout */
    suspend fun signOut()

    /** ดึง User Profile ปัจจุบัน */
    suspend fun getCurrentProfile(): UserProfile?

    /** ตรวจสอบสถานะ Auth */
    fun isLoggedIn(): Boolean
}
