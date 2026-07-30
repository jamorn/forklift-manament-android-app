// 📁 feature/checklist/ChecklistEvent.kt
package com.irpc.forklift.feature.checklist

/**
 * 📋 Checklist User Events (Sealed Class)
 *
 * sealed class ChecklistEvent {
 *     data class SelectVehicle(val chassisNo: String) : ChecklistEvent()
 *     data class CheckItem(val itemId: String, val result: String) : ChecklistEvent()
 *     data class RemarkItem(val itemId: String, val remark: String) : ChecklistEvent()
 *     data class SetManhourMeter(val value: String) : ChecklistEvent()
 *     data class SetMainRemark(val value: String) : ChecklistEvent()
 *     data object Submit : ChecklistEvent()
 *     data object Reset : ChecklistEvent()
 * }
 */
object ChecklistEvent {
    // TODO: implement sealed class
}
