// 📁 feature/checklist/components/ManhourMeterInput/ManhourMeterInput.kt
package com.irpc.forklift.feature.checklist.components.ManhourMeterInput

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.irpc.forklift.ui.components.AppInput

/**
 * 🔢 Manhour Meter Input
 *
 * @param value ค่าปัจจุบัน
 * @param onValueChange callback เมื่อค่าเปลี่ยน
 * @param modifier Modifier
 */
@Composable
fun ManhourMeterInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppInput(
        value = value,
        onValueChange = onValueChange,
        label = "เลขไมล์ (Manhour Meter)",
        modifier = modifier,
    )
}
