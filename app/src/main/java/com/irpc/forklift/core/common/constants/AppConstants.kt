// 📁 core/common/constants/AppConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * 📱 App Constants
 */
object AppConstants {
    // Firestore Collection Names
    const val COLLECTION_USERS = "users"
    const val COLLECTION_VEHICLES = "vehicles"
    const val COLLECTION_CHECKSHEETS = "daily_checksheets"
    const val COLLECTION_MAINTENANCE = "maintenance_logs"
    const val COLLECTION_DEPARTMENTS = "departments"
    const val COLLECTION_CHECKLIST_TEMPLATES = "checklist_templates"

    // Firestore Query Limits
    const val QUERY_LIMIT_DEFAULT = 50
    const val QUERY_LIMIT_CHECKSHEETS = 200

    // Offline Sync
    const val SYNC_INTERVAL_MINUTES = 15L
    const val CACHE_EXPIRY_HOURS = 24L

    // App
    const val APP_NAME_TH = "IRPC Forklift Management"
    const val COMPANY_NAME = "IRPC"
}
