// 📁 core/common/utils/Extensions.kt
package com.irpc.forklift.core.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 🧩 Kotlin Extension Functions
 */

/** String → LocalDate */
fun String.toLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

/** LocalDate → "yyyy-MM-dd" */
fun LocalDate.toDbString(): String =
    this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

/** List ใด ๆ → safe first or null (สำหรับ scope check) */
fun <T> List<T>.firstOrNull(predicate: (T) -> Boolean): T? {
    for (item in this) {
        if (predicate(item)) return item
    }
    return null
}
