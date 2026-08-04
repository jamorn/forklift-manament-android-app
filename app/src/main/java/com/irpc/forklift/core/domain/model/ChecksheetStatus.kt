// 📁 core/domain/model/ChecksheetStatus.kt
package com.irpc.forklift.core.domain.model

/**
 * 📋 Checksheet Status — เทียบ `ChecksheetStatus` / `CHECKSHEET_STATUS` ใน Docs2
 *
 * supervisor control 100%:
 * - operator ตั้งได้แค่ `normal` / `unsafe` (ตาม has_defect)
 * - `caution` / `locked` / `maintenance` ควบคุมโดย supervisor เท่านั้น
 */
enum class ChecksheetStatus(val value: String) {
    NORMAL("normal"), // ✅ ปกติ / ตรวจผ่าน / ใช้ได้
    UNSAFE("unsafe"), // 🚨 มีจุดผิดปกติ (ต้อง review — ยังทำงานได้ เช่น กระจกแตก)
    CAUTION("caution"), // ⚠️ เฝ้าระวัง
    LOCKED("locked"), // 🔒 สั่งล็อค หยุดใช้งาน
    MAINTENANCE("maintenance"), // 🔧 กำลังซ่อม
    ;

    companion object {
        /** map จาก string ค่า ("normal" ฯลฯ) → enum; unknown → null */
        fun fromValue(value: String?): ChecksheetStatus? =
            entries.firstOrNull { it.value == value }
    }
}
