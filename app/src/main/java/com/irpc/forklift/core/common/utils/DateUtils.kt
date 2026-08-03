// 📁 core/common/utils/DateUtils.kt
package com.irpc.forklift.core.common.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * 📅 Date Utility Functions
 * เทียบกับ DateUtils.ts ในเว็บ
 */
object DateUtils {
    private val dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val thaiFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("th", "TH"))

    /** เขตเวลาเอเชีย/กรุงเทพฯ — ใช้สำหรับการตัด "วันทำงาน" ของกะ */
    private val BANGKOK_ZONE: ZoneId = ZoneId.of("Asia/Bangkok")

    /** รูปแบบ DB มาตรฐาน (yyyy-MM-dd) — ใช้จัด format ภายนอก */
    fun getDbFormatter(): DateTimeFormatter = dbFormat

    /** today() ในเว็บ — วันปฏิทินตามเวลาเครื่อง */
    fun getTodayString(): String = LocalDate.now().format(dbFormat)

    /** daysAgo(n) ในเว็บ */
    fun daysAgo(n: Int): String = LocalDate.now().minusDays(n.toLong()).format(dbFormat)

    /** daysFromNow(n) */
    fun daysFromNow(n: Int): String = LocalDate.now().plusDays(n.toLong()).format(dbFormat)

    /** format เป็นไทยแบบ "28 ก.ค. 2569" */
    fun formatThaiDate(dateStr: String): String {
        val date = LocalDate.parse(dateStr, dbFormat)
        // บวก 543 ปีเป็น พ.ศ.
        val buddhistYear = date.year + 543
        val thaiStr = date.format(DateTimeFormatter.ofPattern("d MMM", Locale("th", "TH")))
        return "$thaiStr $buddhistYear"
    }

    /** Parse จาก String */
    fun parseDate(dateStr: String): LocalDate = LocalDate.parse(dateStr, dbFormat)

    /** format สำหรับ Firestore */
    fun toFirestoreString(date: LocalDate): String = date.format(dbFormat)

    // ──────────────────────────────────────────────────────────────
    //  WORK DATE (วันทำงานของกะ) — รองรับกะข้ามวัน (Docs/13)
    //  กะ N (22:00–06:00) หลังเที่ยงคืนก่อน 06:00 ถือเป็นของวันเริ่มกะ (เมื่อวาน)
    // ──────────────────────────────────────────────────────────────

    /** resolveChecklistDate(): วันทำงานของกะ — ก่อน 06:00 น. (Asia/Bangkok) ให้ย้อนเป็นวันก่อนหน้า */
    fun getWorkDate(now: ZonedDateTime = ZonedDateTime.now(BANGKOK_ZONE)): LocalDate =
        if (now.hour < 6) {
            now.minusDays(1).toLocalDate()   // หลังเที่ยงคืน อยู่ในกะ N → เป็นของวันเริ่มกะ (เมื่อวาน)
        } else {
            now.toLocalDate()                 // ตั้งแต่ 06:00 ขึ้นไป → วันปัจจุบัน
        }

    /** resolveChecklistDateString(): วันทำงานของกะ แบบ yyyy-MM-dd (ใช้เป็น key/บันทึก) */
    fun getWorkDateString(now: ZonedDateTime = ZonedDateTime.now(BANGKOK_ZONE)): String =
        getWorkDate(now).format(dbFormat)
}

