// 📁 core/common/utils/DateUtils.kt
package com.irpc.forklift.core.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * 📅 Date Utility Functions
 * เทียบกับ DateUtils.ts ในเว็บ
 */
object DateUtils {
    private val dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val thaiFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("th", "TH"))

    /** today() ในเว็บ */
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
}
