// 📁 core/domain/model/ChecklistMaster.kt
package com.irpc.forklift.core.domain.model

/**
 * 📋 Master Checklist — เทียบ `forklift_inspection_checklist` ใน Docs2 (re-design.md)
 *
 * SAP-style:
 * - `component` (1-5) เป็นหมวดใหญ่ (ระบุด้วย component_id + order)
 * - `checking_point` แต่ละรายการ ระบุด้วย `no` (Step 10: 10,20,30…) + `order` + `is_active`
 *
 * ⭐ Key ของ results/remarks = `"<component_id>-<no>"` (เช่น "1-10", "3-50")
 *    — unique ทั้งระบบ แม้ no จะซ้ำกันข้าม component
 */
data class ChecklistComponent(
    val component_id: Int,       // identity หมวด (1-5) — freeze
    val component_th: String,    // ชื่อหมวด ไทย
    val component_en: String,    // ชื่อหมวด อังกฤษ
    val is_active: Boolean,      // เปิด/ปิดทั้งหมวด (ซ่อน ≠ ลบ)
    val order: Int,              // ลำดับหมวด (Step 10: 10,20,30…)
    val checking_points: List<CheckingPoint>,
)

data class CheckingPoint(
    val no: Int,             // identity รายการ (ใช้ร่วมกับ component_id เป็น key) — freeze
    val item_th: String,     // ชื่อรายการ ไทย
    val item_en: String,     // ชื่อรายการ อังกฤษ
    val is_active: Boolean,  // เปิด/ปิดรายการ (ซ่อน ≠ ลบ)
    val order: Int,          // ลำดับรายการ (Step 10: 10,20,30…)
)

/** Key ของผลตรวจ = "<component_id>-<no>" (เช่น "1-10") */
fun ChecklistComponent.pointKey(point: CheckingPoint): String = "$component_id-${point.no}"

/** คำนวณ key ของทุก checking_point (ที่ is_active) ของ component นี้ */
fun ChecklistComponent.activePointKeys(): List<String> =
    checking_points
        .filter { it.is_active }
        .map { pointKey(it) }
