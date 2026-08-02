// 📁 core/common/constants/DepartmentConstants.kt
package com.irpc.forklift.core.common.constants

/**
 * 🏭 Department Constants (Fallback)
 * โฟกัสเฉพาะ Bagging — ข้อมูลหลักมาจาก Firestore
 */
object DepartmentConstants {
    // Group parent (Bagging เท่านั้น)
    const val GROUP_BAGGING = "dept-bagging"

    // Department ID → Display Name (Fallback)
    val DEPARTMENTS =
        mapOf(
            // Bagging
            "dept-bagging-pp12" to "PP12",
            "dept-bagging-pp3" to "PP3",
            "dept-bagging-ppe" to "PPE",
            "dept-bagging-ppc" to "PPC",
            "dept-bagging-hd" to "HD",
            "dept-sealroom" to "Seal Room",
            "dept-bagging-sasb" to "SASB",
        )

    // ══════════════════════ ★ ใหม่ ★ ══════════════════════

    /** ชื่อแผนกแบบเต็ม สำหรับแสดงใน UI (deptId → ชื่อสวย) */
    val deptDisplayNames =
        mapOf(
            "dept-bagging-pp12" to "PP12 Bagging",
            "dept-bagging-pp3" to "PP3 Bagging",
            "dept-bagging-ppe" to "PPE Bagging",
            "dept-bagging-ppc" to "PPC Bagging",
            "dept-bagging-hd" to "HD Bagging",
            "dept-sealroom" to "Seal Room",
            "dept-bagging-sasb" to "SASB Bagging",
        )

    /** emoji icon ตามแผนก */
    val deptIcons =
        mapOf(
            "dept-bagging-pp12" to "🏭",
            "dept-bagging-pp3" to "🏭",
            "dept-bagging-ppe" to "🏭",
            "dept-bagging-ppc" to "🏭",
            "dept-bagging-hd" to "🏭",
            "dept-sealroom" to "🚪",
            "dept-bagging-sasb" to "🏭",
        )

    /** แปลง deptId → ชื่อสวย (fallback generic — ไม่ให้ user เห็น raw id เช่น dept-xxx) */
    fun displayName(deptId: String): String =
        deptDisplayNames[deptId] ?: "แผนกอื่น ๆ"

    /** departmentId → parent group (Bagging เท่านั้น) */
    fun getParentGroup(deptId: String): String =
        if (deptId.startsWith("dept-bagging")) GROUP_BAGGING else deptId

    // ══════════════════════ ★ Bitmask กำหนดสิทธิ์ ★ ══════════════════════
    //
    // ใช้ 1 bit ต่อ 1 แผนกย่อย
    //   Bagging PL (6 แผนก)   → bit 1,2,4,8,16,32  = ผลรวม 63
    //   Bagging SASB (1 แผนก)  → bit 64             = ผลรวม 64
    //
    // ตรวจสิทธิ์: userAccess & deptBit != 0 → เห็นรถแผนกนั้นได้

    // แต่ละแผนกย่อย
    const val BIT_BAG_PP12 = 1
    const val BIT_BAG_PP3 = 2
    const val BIT_BAG_PPE = 4
    const val BIT_BAG_PPC = 8
    const val BIT_BAG_HDPE = 16
    const val BIT_BAG_SEAL = 32
    const val BIT_BAG_SASB = 64

    /** ผลรวม bit ของ Bagging PL (PP12+PP3+PPE+PPC+HDPE+SEAL) */
    const val BAGGING_PL_ALL = BIT_BAG_PP12 + BIT_BAG_PP3 + BIT_BAG_PPE + BIT_BAG_PPC + BIT_BAG_HDPE + BIT_BAG_SEAL

    /** ผลรวม bit ของ Bagging SASB */
    const val BAGGING_SASB_ALL = BIT_BAG_SASB

    /** ผลรวม bit ทั้งหมดของ Bagging (PL + SASB) */
    const val BAGGING_ALL = BAGGING_PL_ALL + BAGGING_SASB_ALL

    /** mask เห็นทุกแผนก (SA) — ทุกบิตเป็น 1 */
    const val ACCESS_ALL = -1

    /** แผนก → bit (deptId → bitmask) */
    val deptBits =
        mapOf(
            "dept-bagging-pp12" to BIT_BAG_PP12,
            "dept-bagging-pp3" to BIT_BAG_PP3,
            "dept-bagging-ppe" to BIT_BAG_PPE,
            "dept-bagging-ppc" to BIT_BAG_PPC,
            "dept-bagging-hd" to BIT_BAG_HDPE,
            "dept-sealroom" to BIT_BAG_SEAL,
            "dept-bagging-sasb" to BIT_BAG_SASB,
        )

    /** แปลง deptId → bit (fallback 0 = ไม่รู้จัก) */
    fun bitOf(deptId: String): Int = deptBits[deptId] ?: 0
}