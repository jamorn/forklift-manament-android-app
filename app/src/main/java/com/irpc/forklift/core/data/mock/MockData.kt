// 📁 core/data/mock/MockData.kt
package com.irpc.forklift.core.data.mock

import com.irpc.forklift.core.domain.model.*

/**
 * 🧪 Mock Data — Bagging (ไม่รวม SASB)
 */
object MockData {
    data class ChecklistItem(
        val id: String,
        val label: String,
        val category: String,
    )

    val departments =
        listOf(
            Department(id = "dept-bagging-pp12", name = "PP12 Bagging", parentGroup = "Bagging", sortOrder = 1),
            Department(id = "dept-bagging-pp3", name = "PP3 Bagging", parentGroup = "Bagging", sortOrder = 2),
            Department(id = "dept-bagging-ppe", name = "PPE Bagging", parentGroup = "Bagging", sortOrder = 3),
            Department(id = "dept-bagging-ppc", name = "PPC Bagging", parentGroup = "Bagging", sortOrder = 4),
            Department(id = "dept-bagging-hd", name = "HD Bagging", parentGroup = "Bagging", sortOrder = 5),
            Department(id = "dept-sealroom", name = "Seal Room", parentGroup = "Bagging", sortOrder = 6),
        )

    val vehicles =
        listOf(
            Vehicle(
                chassis_no = "FK-001",
                current_flno = "FL-1201",
                department_id = "dept-bagging-pp12",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-01-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-002",
                current_flno = "FL-1202",
                department_id = "dept-bagging-pp12",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-01-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-003",
                current_flno = "FL-1203",
                department_id = "dept-bagging-pp3",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-03-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-004",
                current_flno = "FL-1204",
                department_id = "dept-bagging-ppe",
                vehicle_type = "Nissan HBF25",
                status = "maintenance",
                is_active = true,
                lease_start = "2025-06-01",
                lease_end = "2027-12-31",
                rental_price = 22000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-005",
                current_flno = "FL-1205",
                department_id = "dept-bagging-ppc",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-02-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-006",
                current_flno = "FL-1206",
                department_id = "dept-bagging-hd",
                vehicle_type = "Nissan HBF25",
                status = "active",
                is_active = true,
                lease_start = "2025-04-01",
                lease_end = "2027-12-31",
                rental_price = 22000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-007",
                current_flno = "FL-1207",
                department_id = "dept-sealroom",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-05-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
            Vehicle(
                chassis_no = "FK-008",
                current_flno = "FL-1208",
                department_id = "dept-bagging-pp12",
                vehicle_type = "TOYOTA 8FBN25",
                status = "active",
                is_active = true,
                lease_start = "2025-07-01",
                lease_end = "2027-12-31",
                rental_price = 25000,
                flno_history = emptyList(),
            ),
        )

    val checklistItems =
        listOf(
            ChecklistItem("light-head", "ไฟหน้า", "ระบบไฟ"),
            ChecklistItem("light-tail", "ไฟท้าย", "ระบบไฟ"),
            ChecklistItem("light-turn", "ไฟเลี้ยว", "ระบบไฟ"),
            ChecklistItem("light-brake", "ไฟเบรก", "ระบบไฟ"),
            ChecklistItem("light-warning", "ไฟฉุกเฉิน", "ระบบไฟ"),
            ChecklistItem("horn", "แตร", "ระบบไฟ"),
            ChecklistItem("seat-belt", "เข็มขัดนิรภัย", "โครงสร้าง"),
            ChecklistItem("seat", "ที่นั่ง", "โครงสร้าง"),
            ChecklistItem("mirror", "กระจกมองหลัง", "โครงสร้าง"),
            ChecklistItem("frame", "โครงรถ/รอยรั่ว", "โครงสร้าง"),
            ChecklistItem("engine-oil", "น้ำมันเครื่อง", "โครงสร้าง"),
            ChecklistItem("hydraulic-oil", "น้ำมันไฮดรอลิก", "โครงสร้าง"),
            ChecklistItem("tire-front-left", "ยางหน้าซ้าย", "ล้อ/ยาง"),
            ChecklistItem("tire-front-right", "ยางหน้าขวา", "ล้อ/ยาง"),
            ChecklistItem("tire-rear-left", "ยางหลังซ้าย", "ล้อ/ยาง"),
            ChecklistItem("tire-rear-right", "ยางหลังขวา", "ล้อ/ยาง"),
            ChecklistItem("tire-pressure", "แรงดันลมยาง", "ล้อ/ยาง"),
            ChecklistItem("brake-foot", "เบรกเท้า", "เบรก"),
            ChecklistItem("brake-hand", "เบรกมือ", "เบรก"),
            ChecklistItem("brake-pedal", "pedal เบรก", "เบรก"),
            ChecklistItem("steering", "พวงมาลัย", "ระบบควบคุม"),
            ChecklistItem("accelerator", "คันเร่ง", "ระบบควบคุม"),
            ChecklistItem("lift-chain", "โซ่ยก", "ระบบควบคุม"),
            ChecklistItem("fork", "ส้อม/งา", "ระบบควบคุม"),
            ChecklistItem("mast", "เสาไฮดรอลิก", "ระบบควบคุม"),
            ChecklistItem("fire-extinguisher", "ถังดับเพลิง", "อื่นๆ"),
            ChecklistItem("reverse-alarm", "สัญญาณถอยหลัง", "อื่นๆ"),
            ChecklistItem("hour-meter", "มาตรวัดชั่วโมง", "อื่นๆ"),
            ChecklistItem("data-plate", "ป้ายข้อมูล", "อื่นๆ"),
        )

    val categories = listOf("ระบบไฟ", "โครงสร้าง", "ล้อ/ยาง", "เบรก", "ระบบควบคุม", "อื่นๆ")
}
