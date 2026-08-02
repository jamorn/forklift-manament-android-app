// 📁 core/data/repository/SessionRepository.kt
package com.irpc.forklift.core.data.repository

import com.irpc.forklift.core.domain.model.UserProfile
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🔐 Session Repository (คล้าย sessionStorage ของ browser)
 *
 * เก็บข้อมูล current session ไว้ใน memory (ทั้งแอป share ผ่าน @Singleton)
 * ตอน login → ตั้ง profile ไว้ที่นี่ แล้ว VM/UseCase ใดขอใช้ได้เลย
 * (ไม่ต้อง re-query auth ซ้ำ)
 *
 * ⚠️ ข้อมูลจะหายเมื่อปิดแอป (process ตาย) — เหมือน sessionStorage
 */
@Singleton
class SessionRepository
    @Inject
    constructor() {
        private var currentProfile: UserProfile? = null

        /** ตั้ง current profile (login / logout) */
        fun setProfile(profile: UserProfile?) {
            currentProfile = profile
        }

        /** ดึง profile ของ user ที่ login อยู่ */
        fun getProfile(): UserProfile? = currentProfile

        /** ล้างข้อมูล session (logout) */
        fun clear() {
            currentProfile = null
        }
    }
