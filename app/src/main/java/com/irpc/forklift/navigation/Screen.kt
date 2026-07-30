// 📁 navigation/Screen.kt
package com.irpc.forklift.navigation

/**
 * 🧭 Navigation Routes — Sealed Class
 *
 * sealed class Screen(val route: String) {
 *     object Login : Screen("login")
 *     object Checklist : Screen("checklist")
 *     object Dashboard : Screen("dashboard")
 *     object Maintenance : Screen("maintenance")
 *     object Reports : Screen("reports")
 *     object Admin : Screen("admin")
 * }
 */
object Screen {
    const val LOGIN = "login"
    const val CHECKLIST = "checklist"
    const val DASHBOARD = "dashboard"
    const val MAINTENANCE = "maintenance"
    const val REPORTS = "reports"
    const val ADMIN = "admin"
}
