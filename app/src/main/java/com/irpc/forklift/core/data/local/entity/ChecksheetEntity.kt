// 📁 core/data/local/entity/ChecksheetEntity.kt
package com.irpc.forklift.core.data.local.entity

import com.irpc.forklift.core.domain.model.DailyChecksheet

/**
 * 📋 Checksheet Room Entity (Offline Cache)
 *
 * @Entity(tableName = "checksheet_cache")
 */
data class ChecksheetEntity(
    val id: Long = 0,
    val date: String,
    val shift: String,
    val chassis_no: String,
    val flno_at_time: String,
    val operator_uid: String,
    val results_json: String = "{}",
    val remarks_json: String = "{}",
    val main_remark: String = "",
    val manhourMeter: String = "",
    val status: String = "normal",
    val created_at: String = "",
    val synced: Boolean = false,
)

fun ChecksheetEntity.toDomain(): DailyChecksheet = DailyChecksheet(
    id = id.toString(),
    date = date,
    shift = shift,
    shift_order = 0,
    chassis_no = chassis_no,
    flno_at_time = flno_at_time,
    operator_uid = operator_uid,
    results = emptyMap(),
    remarks = emptyMap(),
    main_remark = main_remark,
    manhourMeter = manhourMeter,
    status = status,
    created_at = created_at,
)
