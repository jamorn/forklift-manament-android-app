// 📁 core/data/local/CheckedVehicleStore.kt
package com.irpc.forklift.core.data.local

import android.content.SharedPreferences
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🚛 เก็บสถานะ "รถที่ตรวจแล้ว" ในแต่ละวัน (persist ข้าม restart)
 *
 * ใช้ SharedPreferences เก็บ Map<chassisNo, info> เป็น JSON
 * โดย key = วันที่ (เช่น "2026-07-30") → value = JSON ของ map รถที่ตรวจแล้ว
 *
 * แก้ bug: เดิมใช้ `remember { mutableStateMapOf() }` ใน MainActivity
 * ที่ไม่ persist — ปิด/หมุนจอข้อมูลหาย
 *
 * โครงสร้างข้อมูล:
 *   date "2026-07-30" → { "FOK-001": "08:45 โดย wiroj", "FOK-002": "09:10 โดย wiroj" }
 */
@Singleton
class CheckedVehicleStore
    @Inject
    constructor(
        private val prefs: SharedPreferences,
    ) {
        /**
         * ดึง map รถที่ตรวจแล้วของวันหนึ่ง (chassisNo → info)
         * ถ้ายังไม่มีวันนั้น → คืน empty map
         */
        fun getCheckedVehicles(date: String): Map<String, String> {
            val raw = prefs.getString(date, null) ?: return emptyMap()
            return try {
                val json = JSONObject(raw)
                val result = mutableMapOf<String, String>()
                json.keys().forEach { key ->
                    result[key] = json.getString(key)
                }
                result
            } catch (e: Exception) {
                emptyMap() // corrupt data → เริ่มใหม่
            }
        }

        /** บันทึก/อัปเดตข้อมูลรถคันหนึ่ง (chassisNo) ในวันหนึ่ง */
        fun setCheckedVehicle(
            date: String,
            chassisNo: String,
            info: String,
        ) {
            val current = getCheckedVehicles(date).toMutableMap()
            current[chassisNo] = info

            val json = JSONObject()
            current.forEach { (k, v) -> json.put(k, v) }
            prefs
                .edit()
                .putString(date, json.toString())
                .apply()
        }

        /** ลบสถานะรถคันหนึ่ง / ใช้เมื่อต้องการ uncheck */
        fun removeCheckedVehicle(
            date: String,
            chassisNo: String,
        ) {
            val current = getCheckedVehicles(date).toMutableMap()
            current.remove(chassisNo)

            val json = JSONObject()
            current.forEach { (k, v) -> json.put(k, v) }
            prefs
                .edit()
                .putString(date, json.toString())
                .apply()
        }
    }
