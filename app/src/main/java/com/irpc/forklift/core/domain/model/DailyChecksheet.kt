// 📁 core/domain/model/DailyChecksheet.kt" 
package com.irpc.forklift.core.domain.model

/**
 * 📋 Daily Checksheet — เก็บชื่อ class เดิม แต่ไส้ใน (schema) ปรับเป็น `inspections` ตาม Docs2
 *
 * ⚙️ ที่เปลี่ยน (ตาม redesign):
 * - `results: Map<String, Boolean>` — true=ปกติ/ผ่าน, false=ชำรุด (เดิม "pass"/"fail")
 * - key ของ results/remarks = `"<component_id>-<no>"` (เช่น "1-10")
 * - เพิ่ม denormalized: `defect_count` / `defect_keys` / `has_defect`
 * - `status` ใช้ enum `ChecksheetStatus` (เดิม string "normal"/"unsafe")
 * - เพิ่ม audit: `created_by` / `updated_by` (identity = email)
 */
data class DailyChecksheet(
    val id: String = "", // document ID (Firestore generated)
    val date: String, // "2026-08-02" (work date)
    val shift: String, // "M" | "E" | "N"
    val shift_order: Int,
    val chassis_no: String,
    val flno_at_time: String,
    val operator_uid: String, // email operator (คนสร้างใบ)
    val manhourMeter: String, // เลขไมล์ (ชั่วโมงทำงาน)
    val results: Map<String, Boolean>, // "<comp>-<no>" → true=ปกติ, false=ชำรุด
    val remarks: Map<String, String>, // "<comp>-<no>" → ข้อความ (เฉพาะ false ที่มีคำอธิบาย)
    val main_remark: String, // หมายเหตุรวม
    // ── Denormalized (คำนวณจาก results — recalc atomic) ──
    val defect_count: Int = 0, // นับ false ใน results
    val defect_keys: List<String> = emptyList(), // array ของ key ที่เป็น false
    val has_defect: Boolean = false, // มี false ≥ 1
    // ── Status (supervisor control) ──
    val status: ChecksheetStatus = ChecksheetStatus.NORMAL,
    val locked_at: Long? = null, // Timestamp (millis) — supervisor
    val locked_by: String? = null, // email supervisor
    val unlocked_at: Long? = null, // Timestamp (millis)
    val unlocked_by: String? = null, // email supervisor
    // ── Audit trail (ผู้สร้าง/ผู้แก้ล่าสุด) ──
    val created_at: String = "", // ISO 8601 (mock) — จะเป็น Timestamp เมื่อผูก firestore
    val created_by: String = "", // email ผู้สร้าง (operator)
    val updated_at: String = "", // ISO 8601 (mock)
    val updated_by: String = "", // email ผู้แก้ล่าสุด
)

/** มีจุดชำรุด (has_defect) */
fun DailyChecksheet.isUnsafe(): Boolean = has_defect

/** ตรวจผ่านปกติ 100% */
fun DailyChecksheet.isNormal(): Boolean = !has_defect && status != ChecksheetStatus.LOCKED && status != ChecksheetStatus.MAINTENANCE

/** จำนวนจุดชำรุด (= defect_count) */
fun DailyChecksheet.failCount(): Int = defect_count

