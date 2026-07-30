// 📁 core/data/mapper/ChecksheetMapper.kt
package com.irpc.forklift.core.data.mapper

import com.irpc.forklift.core.data.local.entity.ChecksheetEntity
import com.irpc.forklift.core.domain.model.DailyChecksheet
import org.json.JSONObject

/**
 * 📋 Checksheet Mapper — Entity ↔ Domain
 */
object ChecksheetMapper {
    fun entityToDomain(entity: ChecksheetEntity): DailyChecksheet {
        return DailyChecksheet(
            id = entity.id.toString(),
            date = entity.date,
            shift = entity.shift,
            shift_order = 0,
            chassis_no = entity.chassis_no,
            flno_at_time = entity.flno_at_time,
            operator_uid = entity.operator_uid,
            results = parseMap(entity.results_json),
            remarks = parseMap(entity.remarks_json),
            main_remark = entity.main_remark,
            manhourMeter = entity.manhourMeter,
            status = entity.status,
            created_at = entity.created_at,
        )
    }

    fun domainToEntity(domain: DailyChecksheet, synced: Boolean = false): ChecksheetEntity {
        return ChecksheetEntity(
            date = domain.date,
            shift = domain.shift,
            chassis_no = domain.chassis_no,
            flno_at_time = domain.flno_at_time,
            operator_uid = domain.operator_uid,
            results_json = toJson(domain.results),
            remarks_json = toJson(domain.remarks),
            main_remark = domain.main_remark,
            manhourMeter = domain.manhourMeter,
            status = domain.status,
            created_at = domain.created_at,
            synced = synced,
        )
    }

    private fun parseMap(json: String): Map<String, String> {
        if (json.isBlank()) return emptyMap()
        val obj = JSONObject(json)
        return obj.keys().asSequence().associateWith { obj.getString(it) }
    }

    private fun toJson(map: Map<String, String>): String {
        return JSONObject(map as Map<String, Any?>).toString()
    }
}
