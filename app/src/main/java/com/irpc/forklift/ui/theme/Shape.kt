// 📁 ui/theme/Shape.kt
package com.irpc.forklift.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * 🎨 Forklift Shapes
 *
 * ใช้ rounded corners แบบเดียวกับเว็บ (rounded-xl = 12dp)
 */
val ForkliftShapes = Shapes(
    small = RoundedCornerShape(8.dp),      // rounded-lg
    medium = RoundedCornerShape(12.dp),    // rounded-xl
    large = RoundedCornerShape(16.dp),     // rounded-2xl
    extraLarge = RoundedCornerShape(24.dp),// rounded-3xl
)
