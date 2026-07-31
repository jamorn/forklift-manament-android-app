// 📁 core/data/repository/AuthRepositoryImpl.kt
package com.irpc.forklift.core.data.repository

import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.core.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🔐 Auth Repository Implementation
 *
 * - Real mode: Firebase Auth + Firestore
 * - Mock mode: return fake profile (dev)
 */
@Singleton
class AuthRepositoryImpl
    @Inject
    constructor(
        // private val auth: FirebaseAuth,
        // private val firestore: FirebaseFirestore,
    ) : AuthRepository {
        override suspend fun signInWithGoogle(): Result<UserProfile> =
            try {
                // val credential = auth.signInWithCredential(googleCredential).await()
                // val profile = loadProfileFromFirestore(credential.user?.email ?: "")
                // Result.success(profile)
                Result.failure(Exception("Not implemented"))
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun mockLogin(email: String): Result<UserProfile> {
            // Dev mock — return จาก MOCK_USERS
            val mockProfile =
                MOCK_USERS[email.lowercase()]
                    ?: return Result.failure(Exception("Mock user not found: $email"))
            return Result.success(mockProfile)
        }

        override suspend fun signOut() {
            // auth.signOut()
        }

        override suspend fun getCurrentProfile(): UserProfile? {
            // val user = auth.currentUser ?: return null
            // return loadProfileFromFirestore(user.email ?: "")
            return null
        }

        override fun isLoggedIn(): Boolean {
            // return auth.currentUser != null
            return false
        }

        companion object {
            // Mock users data — subset สำหรับ login
            val MOCK_USERS =
                mapOf(
                    "jamorn@irpc.co.th" to
                        UserProfile(
                            email = "jamorn@irpc.co.th",
                            displayName = "จำเริญ",
                            position = "System Administrator",
                            employmentType = "permanent",
                            companyName = "IRPC",
                            status = listOf("active"),
                            roles =
                                com.irpc.forklift.core.domain.model
                                    .ForkliftRoles(role = "sa", scope = emptyList()),
                            mailto = listOf("maintenance-report", "daily-plan"),
                            createdAt = "2026-01-01T00:00:00Z",
                            lastLoginAt = "2026-08-05T10:00:00Z",
                            lastUpdatedAt = "2026-08-05T10:00:00Z",
                        ),
                    "supv-pl@irpc.co.th" to
                        UserProfile(
                            email = "supv-pl@irpc.co.th",
                            displayName = "สมชาย",
                            position = "Forklift Supervisor",
                            employmentType = "permanent",
                            companyName = "IRPC",
                            status = listOf("active"),
                            roles =
                                com.irpc.forklift.core.domain.model.ForkliftRoles(
                                    role = "admin",
                                    scope =
                                        listOf(
                                            "dept-bagging-pp12",
                                            "dept-bagging-pp3",
                                            "dept-bagging-ppe",
                                            "dept-bagging-ppc",
                                            "dept-bagging-hd",
                                            "dept-sealroom",
                                        ),
                                ),
                            mailto = listOf("maintenance-report", "daily-plan"),
                            createdAt = "2026-02-15T00:00:00Z",
                            lastLoginAt = "2026-08-05T08:30:00Z",
                            lastUpdatedAt = "2026-08-05T08:30:00Z",
                        ),
                    "wiroj@abc-logistics.co.th" to
                        UserProfile(
                            email = "wiroj@abc-logistics.co.th",
                            displayName = "วิโรจน์ ขยัน",
                            position = "Forklift Driver",
                            employmentType = "contractor",
                            companyName = "ABC Logistics",
                            status = listOf("active"),
                            roles =
                                com.irpc.forklift.core.domain.model.ForkliftRoles(
                                    role = "operator",
                                    scope =
                                        listOf(
                                            "dept-bagging-pp12",
                                            "dept-bagging-pp3",
                                            "dept-bagging-ppe",
                                            "dept-bagging-ppc",
                                            "dept-bagging-hd",
                                            "dept-sealroom",
                                        ),
                                ),
                            mailto = emptyList(),
                            createdAt = "2026-03-10T00:00:00Z",
                            lastLoginAt = "2026-08-05T06:00:00Z",
                            lastUpdatedAt = "2026-08-05T06:00:00Z",
                        ),
                )
        }
    }
