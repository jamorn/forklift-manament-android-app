// 📁 ui/components/AppInput.kt
package com.irpc.forklift.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ⌨️ App Input — Reusable TextField
 *
 * @param value ค่าปัจจุบัน
 * @param onValueChange callback เมื่อค่าเปลี่ยน
 * @param label label ของช่องกรอก
 * @param modifier Modifier
 * @param enabled เปิด/ปิดการใช้งาน
 * @param isError แสดงสถานะ error
 * @param errorMessage ข้อความ error (แสดงเมื่อ isError = true)
 * @param singleLine บรรทัดเดียวหรือหลายบรรทัด
 */
@Composable
fun AppInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            isError = isError,
            singleLine = singleLine,
            shape = MaterialTheme.shapes.medium,
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp),
            )
        }
    }
}

