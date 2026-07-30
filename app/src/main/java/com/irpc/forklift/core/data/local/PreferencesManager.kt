// 📁 core/data/local/PreferencesManager.kt
package com.irpc.forklift.core.data.local

/**
 * ⚙️ Preferences Manager (DataStore)
 *
 * เก็บ session token, user email, theme preference
 *
 * class PreferencesManager(private val context: Context) {
 *     private val dataStore = context.dataStore
 *
 *     val userEmail: Flow<String?> = dataStore.data.map { it[USER_EMAIL_KEY] }
 *     val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE_KEY] ?: false }
 *
 *     suspend fun saveUserEmail(email: String) { ... }
 *     suspend fun clearSession() { ... }
 * }
 */
object PreferencesManager {
    // TODO: implement DataStore
}
