// 📁 core/data/mapper/ChecksheetMapper.kt
package com.irpc.forklift.core.data.mapper

import com.irpc.forklift.core.data.local.entity.ChecksheetEntity
import com.irpc.forklift.core.domain.model.ChecksheetStatus
import com.irpc.forklift.core.domain.model.DailyChecksheet
import org.json.JSONObject

/**
 * 📋 Checksheet Mapper — Entity ↔ Domain
 */
object ChecksheetMapper {
    fun entityToDomain(entity: ChecksheetEntity): DailyChecksheet =
        DailyChecksheet(
            id = entity.id.toString(),
            date = entity.date,
            shift = entity.shift,
            shift_order = 0,
            chassis_no = entity.chassis_no,
            flno_at_time = entity.flno_at_time,
            operator_uid = entity.operator_uid,
            results = parseBooleanMap(entity.results_json),
            remarks = parseStringMap(entity.remarks_json),
            main_remark = entity.main_remark,
            manhourMeter = entity.manhourMeter,
            status = ChecksheetStatus.fromValue(entity.status) ?: ChecksheetStatus.NORMAL,
            created_at = entity.created_at,
            updated_at = entity.updated_at,
        )

    fun domainToEntity(
        domain: DailyChecksheet,
        synced: Boolean = false,
    ): ChecksheetEntity =
        ChecksheetEntity(
            date = domain.date,
            shift = domain.shift,
            chassis_no = domain.chassis_no,
            flno_at_time = domain.flno_at_time,
            operator_uid = domain.operator_uid,
            results_json = toJson(domain.results),
            remarks_json = toJson(domain.remarks),
            main_remark = domain.main_remark,
            manhourMeter = domain.manhourMeter,
            status = domain.status.value,
            created_at = domain.created_at,
            updated_at = domain.updated_at,
            synced = synced,
        )

    private fun parseBooleanMap(json: String): Map<String, Boolean> {
        if (json.isBlank()) return emptyMap()
        val obj = JSONObject(json)
        return obj.keys().asSequence().associateWith { key -> obj.optBoolean(key) }
    }

    private fun parseStringMap(json: String): Map<String, String> {
        if (json.isBlank()) return emptyMap()
        val obj = JSONObject(json)
        return obj.keys().asSequence().associateWith { obj.optString(it) }
    }

    private fun toJson(map: Map<String, Any?>): String = JSONObject(map).toString()
}
