// feature/checklist/components/ChecklistForm.kt
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

@Composable
fun ChecklistForm(
    vehicle: Vehicle,
    previousChecksheet: DailyChecksheet?,
    results: Map<String, String>,
    remarks: Map<String, String>,
    mainRemark: String,
    manhourMeter: String,
    onItemChecked: (String, String) -> Unit,
    onItemRemark: (String, String) -> Unit,
    onMainRemarkChange: (String) -> Unit,
    onManhourMeterChange: (String) -> Unit,
    onPassAll: () -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
    ) {
        // Copy-Forward Banner
        if (previousChecksheet != null) {
            CopyForwardBanner(
                date = previousChecksheet.date,
                shift = previousChecksheet.shift,
            )
            Spacer(Modifier.height(12.dp))
        }

        // PASS All Button
        OutlinedButton(
            onClick = onPassAll,
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text("✅ PASS All — ตั้งค่าทุกข้อเป็นปกติ", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        // Category Sections
        MockData.categories.forEach { category ->
            val catItems = MockData.checklistItems.filter { it.category == category }
            if (catItems.isNotEmpty()) {
                CategorySection(
                    title = category,
                    items = catItems,
                    results = results,
                    remarks = remarks,
                    onItemChecked = onItemChecked,
                    onItemRemark = onItemRemark,
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        // Manhour Meter
        ManhourMeterInput(
            value = manhourMeter,
            onValueChange = onManhourMeterChange,
        )

        Spacer(Modifier.height(12.dp))

        // Main Remark
        OutlinedTextField(
            value = mainRemark,
            onValueChange = onMainRemarkChange,
            label = { Text("หมายเหตุเพิ่มเติม (แจ้งกะถัดไป)") },
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
