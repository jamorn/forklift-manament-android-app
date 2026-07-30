// 📁 feature/checklist/components/ChecklistForm.kt
package com.irpc.forklift.feature.checklist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.DailyChecksheet
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.feature.checklist.components.CategorySection.CategorySection
import com.irpc.forklift.feature.checklist.components.ManhourMeterInput.ManhourMeterInput

/**
 * 📋 Step 2: Checklist Form
 *
 * แสดง Category Sections + Check Items
 * พร้อม Copy-Forward Banner ถ้ามี checksheet ก่อนหน้า
 *
 * @param vehicle รถที่กำลังตรวจ
 * @param previousChecksheet checksheet ก่อนหน้า (สำหรับ Copy-Forward)
 * @param onItemChecked callback (itemId, result)
 * @param onSubmit callback ส่งข้อมูล
 */
@Composable
fun ChecklistForm(
    vehicle: Vehicle,
    previousChecksheet: DailyChecksheet?,
    onItemChecked: (String, String) -> Unit,
    onSubmit: () -> Unit,
) {
    var manhourMeter by remember { mutableStateOf("") }
    var mainRemark by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Copy-Forward Banner
        if (previousChecksheet != null) {
            CopyForwardBanner(
                date = previousChecksheet.date,
                shift = previousChecksheet.shift,
            )
            Spacer(Modifier.height(12.dp))
        }

        // Vehicle Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(Modifier.padding(12.dp)) {
                Text(
                    vehicle.current_flno,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text("ทะเบียน: ${vehicle.chassis_no}")
                Text("ประเภท: ${vehicle.vehicle_type}")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Category Sections
        MockData.categories.forEach { category ->
            val catItems = MockData.checklistItems.filter { it.category == category }
            if (catItems.isNotEmpty()) {
                CategorySection(
                    title = category,
                    items = catItems,
                    results = emptyMap(), // TODO: connect to actual state
                    onItemChecked = onItemChecked,
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        // Manhour Meter
        ManhourMeterInput(
            value = manhourMeter,
            onValueChange = { manhourMeter = it },
        )

        Spacer(Modifier.height(12.dp))

        // Main Remark
        OutlinedTextField(
            value = mainRemark,
            onValueChange = { mainRemark = it },
            label = { Text("หมายเหตุเพิ่มเติม") },
            modifier = Modifier.fillMaxWidth().height(80.dp),
            shape = MaterialTheme.shapes.medium,
        )

        Spacer(Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text("✅ บันทึกผลตรวจ", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))
    }
}

