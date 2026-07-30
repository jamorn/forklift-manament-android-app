// 📁 core/common/constants/StatusConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * 📌 Status Constants
 */
object StatusConstants {
    // Vehicle Status
    const val VEHICLE_ACTIVE = "active"
    const val VEHICLE_MAINTENANCE = "maintenance"
    const val VEHICLE_INACTIVE = "inactive"

    // Checksheet Status
    const val CS_NORMAL = "normal"
    const val CS_UNSAFE = "unsafe"

    // User Status
    const val USER_ACTIVE = "active"
    const val USER_PENDING = "pending"
    const val USER_INACTIVE = "inactive"

    // Maintenance Log Status
    const val LOG_PENDING = "pending"
    const val LOG_COMPLETED = "completed"
}
