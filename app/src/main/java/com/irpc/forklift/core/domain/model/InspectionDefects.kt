// 📁 core/domain/model/InspectionDefects.kt
package com.irpc.forklift.core.domain.model

/**
 * 🧮 ผลลัพธ์ที่ recalc จาก boolean `results` — ตาม Docs2 (denormalized)
 *
 * ทุกครั้งที่เขียน/แก้ results ต้อง recalc 3 ฟิลด์นี้ให้สอดคล้องกัน (atomic)
 * เพราะ Firestore ไม่รองรับ query ภายใน map (results) ตรง ๆ
 */
data class DefectSummary(
    val defect_count: Int,
    val defect_keys: List<String>,
    val has_defect: Boolean,
)

/**
 * คำนวณ defect summary จาก boolean results
 * - `false` = ชำรุด (defect), `true` = ปกติ
 */
fun recalcDefects(results: Map<String, Boolean>): DefectSummary {
    val defectKeys = results.filterValues { it == false }.keys.sorted()
    return DefectSummary(
        defect_count = defectKeys.size,
        defect_keys = defectKeys,
        has_defect = defectKeys.isNotEmpty(),
    )
}
