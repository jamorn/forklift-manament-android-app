// 📁 core/data/mock/MockChecklistMaster.kt
package com.irpc.forklift.core.data.mock

import com.irpc.forklift.core.domain.model.ChecklistComponent
import com.irpc.forklift.core.domain.model.CheckingPoint

/**
 * 🧪 Mock Master Checklist — ตาม Docs2 (re-design.md) `forklift_inspection_checklist`
 *
 * ⚠️ หมายเหตุ:
 * - Component 5 (ระบบแก๊ส) มี `is_active: false` — master ซ่อนทั้งหมวด
 * - `no` = `order` (Step 10) — identity ผูกกับลำดับ Step 10 (freeze)
 * - ยังเป็น mock (ไม่ผูก firestore) ตามข้อตกลง — ใช้ seed นี้ก่อน
 */
object MockChecklistMaster {
    val components: List<ChecklistComponent> =
        listOf(
            // ── Component 1 — ระบบเครื่องยนต์ (ENGINE SYSTEM) ──
            ChecklistComponent(
                component_id = 1,
                component_th = "ระบบเครื่องยนต์",
                component_en = "ENGINE SYSTEM",
                is_active = true,
                order = 10,
                checking_points =
                    listOf(
                        CheckingPoint(10, "กรองอากาศ", "AIR FILTER", true, 10),
                        CheckingPoint(20, "กรองเชื้อเพลิง", "FUEL FILTER", true, 20),
                        CheckingPoint(30, "กรองเครื่อง", "OIL FILTER", true, 30),
                        CheckingPoint(40, "น้ำมันเครื่อง", "ENGINE OIL", true, 40),
                        CheckingPoint(50, "ปั๊มน้ำ", "WATER PUMP", true, 50),
                        CheckingPoint(60, "หม้อน้ำ", "BOILER", true, 60),
                        CheckingPoint(70, "หม้อพักน้ำ", "WATER TANK", true, 70),
                        CheckingPoint(80, "ท่อไอเสีย", "EXHAUST PIPE", true, 80),
                        CheckingPoint(90, "ยางแท่นเครื่อง", "MACHINE BASE RUBBER", true, 90),
                        CheckingPoint(100, "สายพาน", "BELT", true, 100),
                        CheckingPoint(110, "ใบพัดลม", "FAN BLADES", true, 110),
                    ),
            ),

            // ── Component 2 — ระบบขับเคลื่อน และ ระบบเกียร์ (DRIVE & TRANSMISSION SYSTEM) ──
            ChecklistComponent(
                component_id = 2,
                component_th = "ระบบขับเคลื่อน และ ระบบเกียร์",
                component_en = "DRIVE & TRANSMISSION SYSTEM",
                is_active = true,
                order = 20,
                checking_points =
                    listOf(
                        CheckingPoint(10, "จานกดครัช", "CLUTCH PRESSURE PLATE", true, 10),
                        CheckingPoint(20, "แผ่นครัช", "CLUTCH DISE", true, 20),
                        CheckingPoint(30, "ลูกปืน", "BEARING", true, 30),
                        CheckingPoint(40, "ระบบเกียร์ธรรมดา", "TRANSMISSION SYSTEM", true, 40),
                        CheckingPoint(50, "ระบบเกียร์ออโต", "AUTO TRANSMISSION SYSTEM", true, 50),
                        CheckingPoint(60, "ลูกหมากคันเกียร์", "BALL JOINT", true, 60),
                        CheckingPoint(70, "สวิทช์คันเกียร์", "SWITCHGEAR", true, 70),
                        CheckingPoint(80, "แม่ปั๊มครัชบน", "CLUTCH MASTER CYLINDER", true, 80),
                        CheckingPoint(90, "แม่ปั๊มครัชล่าง", "CLUTCH SLAVE CYLINDER", true, 90),
                        CheckingPoint(100, "น้ำมันเฟืองท้าย", "GEAR OIL", true, 100),
                        CheckingPoint(110, "น็อตล้อ/น็อตกระทะล้อ", "WHEEL NUTS/HUB BOLTS", true, 110),
                        CheckingPoint(120, "ยาง", "TIRE", true, 120),
                    ),
            ),

            // ── Component 3 — ระบบไฮดรอลิค (HYDRAULIC SYSTEM) ──
            ChecklistComponent(
                component_id = 3,
                component_th = "ระบบไฮดรอลิค",
                component_en = "HYDRAULIC SYSTEM",
                is_active = true,
                order = 30,
                checking_points =
                    listOf(
                        CheckingPoint(10, "กระบอกเพาเวอร์", "POWER CYLINDER", true, 10),
                        CheckingPoint(20, "กระปุกพวงมาลัย", "STEERING GEAR BOX", true, 20),
                        CheckingPoint(30, "กระบอกยก-หงาย", "TILT CYLINDER", true, 30),
                        CheckingPoint(40, "กระบอกขึ้น-ลง", "LIFT CYLINDER", true, 40),
                        CheckingPoint(50, "กล่องคันโยกไฮดรอลิค", "CONTROL VALVE", true, 50),
                        CheckingPoint(60, "ปั๊มไฮดรอลิค", "HYDRAULIC PUMP", true, 60),
                        CheckingPoint(70, "ออยล์คูลเลอร์เกียร์", "OIL COOLING", true, 70),
                        CheckingPoint(80, "สายไฮดรอลิค", "HYDRAULIC HOSE", true, 80),
                        CheckingPoint(90, "กรองไฮดรอลิค", "FILTER", true, 90),
                        CheckingPoint(100, "น้ำมันไฮดรอลิค", "HYDRAULIC OIL", true, 100),
                    ),
            ),

            // ── Component 4 — ระบบไฟและอุปกรณ์เสริม (ELECTRICAL SYSTEM AND ASSESSORY) ──
            ChecklistComponent(
                component_id = 4,
                component_th = "ระบบไฟและอุปกรณ์เสริม",
                component_en = "ELECTRICAL SYSTEM AND ASSESSORY",
                is_active = true,
                order = 40,
                checking_points =
                    listOf(
                        CheckingPoint(10, "ไฟหน้า", "HEADLIGHT", true, 10),
                        CheckingPoint(20, "ไฟเลี้ยว", "FLASHER LIGHT", true, 20),
                        CheckingPoint(30, "ไฟเบรค", "STOP LIGHT", true, 30),
                        CheckingPoint(40, "ไฟถอย", "REVERSE LIGHT", true, 40),
                        CheckingPoint(50, "กระจก", "MIRROR", true, 50),
                        CheckingPoint(60, "สัญญาณไฟเตือน", "WARNING LIGHT", true, 60),
                        CheckingPoint(70, "แตร", "HORN", true, 70),
                        CheckingPoint(80, "เบาะนั่ง", "DRIVER SEAT", true, 80),
                        CheckingPoint(90, "หลังคา", "HEAD GUARD", true, 90),
                    ),
            ),

            // ── Component 5 — ระบบแก๊ส (GAS SYSTEM) — is_active:false (master ซ่อน) ──
            ChecklistComponent(
                component_id = 5,
                component_th = "ระบบแก๊ส",
                component_en = "GAS SYSTEM",
                is_active = false,
                order = 50,
                checking_points =
                    listOf(
                        CheckingPoint(10, "ลิ้นนิรภัย", "SAFETY VALVE", true, 10),
                        CheckingPoint(20, "ไส้กรองแก๊ส", "GAS FILTER", true, 20),
                        CheckingPoint(30, "หม้อต้มแก๊ส", "LPG VAPORIZER", true, 30),
                        CheckingPoint(40, "ชุดสายไฟหม้อต้มแก๊ส", "LPG / CNG WIRE HARNESS", true, 40),
                        CheckingPoint(50, "หัวคัปปลิ้งแก๊ส", "GAS COUPLING", true, 50),
                    ),
            ),
        )

    /** เฉพาะ component ที่ master เปิด (is_active) — ตาม Filter Pipeline ขั้น [1] */
    val activeComponents: List<ChecklistComponent>
        get() = components.filter { it.is_active }
}
