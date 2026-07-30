// 📁 core/common/utils/CurrencyUtils.kt
package com.irpc.forklift.core.common.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * 💰 Currency Formatting
 */
object CurrencyUtils {
    private val thbFormat = NumberFormat.getCurrencyInstance(Locale("th", "TH"))

    fun formatThaiBaht(amount: Double): String {
        return thbFormat.format(amount)
    }

    fun formatThaiBaht(amount: Int): String {
        return thbFormat.format(amount.toDouble())
    }

    /** แบบย่อ: 1,500 → "1,500" ไม่มีสกุลเงิน */
    fun formatCompact(amount: Int): String {
        return NumberFormat.getNumberInstance(Locale("th", "TH")).format(amount)
    }
}
