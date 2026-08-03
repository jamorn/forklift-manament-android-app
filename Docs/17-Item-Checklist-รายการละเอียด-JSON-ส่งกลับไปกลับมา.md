# 17 — Item Checklist: รายการละเอียด + JSON + การส่งค่ากลับไปกลับมา

> 📋 เอกสารนี้สรุป **รายการ item checklist ทั้งหมด** + โครงสร้าง `order`/`enabled`
> + ตัวอย่าง JSON + ตัวอย่างการ **ส่งกลับไปกลับมา (form ↔ firebase)**
> เพื่อให้ frontend/backend ทำงานตรงกัน (field, key, order ชัดเจน)
>
> **สถานะ:** ⬜ รายการปัจจุบันยัง "จำลอง" ก่อน (รอข้อมูลบริษัทให้เช่ารถขยาย/แก้ category)
> โครงสร้าง `order`/`enabled` เป็น design ที่ตกลงไว้ (Doc 16) ยังไม่ implement ทั้งหมด

---

## 1. โครงสร้าง Every Item Checklist

```kotlin
data class ChecklistItem(
    val id: String,          // key โยงกับ results/remarks (firestore เก็บ map ด้วย id)
    val label: String,       // ชื่อแสดงผล (user เห็น)
    val category: String,    // หมวดหมู่
    val order: Int,          // ✅ ลำดับเรียง (1,2,3...) — source ของลำดับการแสดงผลเดียว
    val enabled: Boolean,    // ✅ true=ใช้งาน, false=รายการที่ถูกยกเลิก/ซ่อนในอนาคต
)
```

**คำนิยาม (สำคัญ):**
| field | ความหมาย | ข้อควรจำ |
|-------|---------|---------|
| `id` | รหัส item (ไม่ซ้ำ) | ใช้เป็น key ใน `results`/`remarks` |
| `label` | ชื่อที่ user เห็น | ไม่เปลี่ยนตอนแสดง |
| `category` | หมวดหมู่ | ใช้จัดกลุ่ม UI |
| `order` | ลำดับเรียง | **source เดียวของลำดับ** → ห้ามสลับไปสลับมา |
| `enabled` | ใช้งานอยู่ไหม | `false` = รายการถูกยกเลิก/ซ่อนในอนาคต |

**หลักการ `order` / `enabled`:**
- ลำดับการแสดงผล มาจาก **template ตาม `order`** เสมอ (ไม่ render จาก Map ตรง ๆ — Map ไม่การันตีลำดับ)
- `enabled=false` → **ไม่โชว์ให้กรอก** แต่ผลเก่าใน history (ถ้ามี) ยังอ่านได้จาก `id` → ป้องกันข้อมูลย้อนหลังเพี้ยน
- เพิ่ม/ลด/แก้ item ในอนาคต = แก้ที่ template (1 ที่เดียว) ไม่กระทบ data เก่า

---

## 2. รายการ Item ทั้งหมด (ปัจจุบัน — 29 รายการ / 6 หมวด)

> 🔸 `order` ในตารางนี้เรียงตามลำดับใน MockData ปัจจุบัน (ยังไม่ตายตัวสุด — รอข้อมูลบริษัทเช่า)
> 🔸 `enabled` ทุกรายการปัจจุบัน = `true` (ยังไม่มีรายการที่ยกเลิก)

### หมวด 1 — ระบบไฟ
| order | id | label |
|-------|----|-------|
| 1 | `light-head` | ไฟหน้า |
| 2 | `light-tail` | ไฟท้าย |
| 3 | `light-turn` | ไฟเลี้ยว |
| 4 | `light-brake` | ไฟเบรก |
| 5 | `light-warning` | ไฟฉุกเฉิน |
| 6 | `horn` | แตร |

### หมวด 2 — โครงสร้าง
| order | id | label |
|-------|----|-------|
| 7 | `seat-belt` | เข็มขัดนิรภัย |
| 8 | `seat` | ที่นั่ง |
| 9 | `mirror` | กระจกมองหลัง |
| 10 | `frame` | โครงรถ/รอยรั่ว |
| 11 | `engine-oil` | น้ำมันเครื่อง |
| 12 | `hydraulic-oil` | น้ำมันไฮดรอลิก |

### หมวด 3 — ล้อ/ยาง
| order | id | label |
|-------|----|-------|
| 13 | `tire-front-left` | ยางหน้าซ้าย |
| 14 | `tire-front-right` | ยางหน้าขวา |
| 15 | `tire-rear-left` | ยางหลังซ้าย |
| 16 | `tire-rear-right` | ยางหลังขวา |
| 17 | `tire-pressure` | แรงดันลมยาง |

### หมวด 4 — เบรก
| order | id | label |
|-------|----|-------|
| 18 | `brake-foot` | เบรกเท้า |
| 19 | `brake-hand` | เบรกมือ |
| 20 | `brake-pedal` | pedal เบรก |

### หมวด 5 — ระบบควบคุม
| order | id | label |
|-------|----|-------|
| 21 | `steering` | พวงมาลัย |
| 22 | `accelerator` | คันเร่ง |
| 23 | `lift-chain` | โซ่ยก |
| 24 | `fork` | ส้อม/งา |
| 25 | `mast` | เสาไฮดรอลิก |

### หมวด 6 — อื่นๆ
| order | id | label |
|-------|----|-------|
| 26 | `fire-extinguisher` | ถังดับเพลิง |
| 27 | `reverse-alarm` | สัญญาณถอยหลัง |
| 28 | `hour-meter` | มาตรวัดชั่วโมง |
| 29 | `data-plate` | ป้ายข้อมูล |

> ⚠️ **ยังไม่ครอบคลุม** — รอข้อมูลบริษัทให้เช่ารถ เพื่อ เพิ่ม/ลด/แก้ category + item
> (เช่น ระบบไฮดรอลิก/ยก, แบตเตอรี่ และ EV, น้ำมันเบรก, ระบบส่งกำลัง ฯลฯ — แล้วแต่บริษัทกำหนด)

---

## 3. ตัวอย่าง JSON — Collection Template (firestore: `checklist_items`)

Template เก็บที่ firestore เป็น "ตัวตั้งของลำดับ + enable" เดียว (ยกตัวอย่าง 5 รายการครบโครงสร้าง)

```json
[
  { "id": "light-head",    "label": "ไฟหน้า",    "category": "ระบบไฟ",    "order": 1,  "enabled": true },
  { "id": "light-tail",    "label": "ไฟท้าย",    "category": "ระบบไฟ",    "order": 2,  "enabled": true },
  { "id": "tire-front-left","label": "ยางหน้าซ้าย","category": "ล้อ/ยาง", "order": 13, "enabled": true },
  { "id": "old-item-xyz",  "label": "รายการยกเลิก","category": "อื่นๆ",   "order": 100,"enabled": false }
]
```

**UI ควรอ่าน template เรียงตาม `order`** แล้ว render → `enabled=false` ข้ามไป:

```kotlin
checklistItems
    .filter { it.enabled }              // ข้ามรายการที่ถูกยกเลิก
    .sortedBy { it.order }              // เรียงตาม order (source เดียว)
    .forEach { item ->
        val result = results[item.id]   // ดึงผลจาก map ตาม key
        render(item, result)
    }
```

---

## 4. ตัวอย่าง JSON — ผลตรวจ operator ส่งไป Firebase (collection: `daily_checksheets`)

Operator กะ M ตรวจ `Y1F2-402504` (PLBG-01) พบ 2 รายการ not ok กรอก remarks

```json
{
  "id": "dXkPq9nU2m4",                        // ⭐ firestore auto-gen (ครั้งแรก)
  "date": "2026-08-02",
  "shift": "M",
  "shift_order": 1,
  "chassis_no": "Y1F2-402504",
  "flno_at_time": "PLBG-01",
  "operator_uid": "wiroj",
  "results": {                                 // Map<itemId, "pass"|"fail">
    "light-head": "pass",
    "light-tail": "pass",
    "horn": "pass",
    "tire-front-left": "pass",
    "tire-rear-right": "pass",
    "tire-pressure": "pass",
    "brake-foot": "fail",
    "steering": "pass",
    "fork": "fail",
    "fire-extinguisher": "pass",
    "hour-meter": "pass",
    "data-plate": "pass"
  },
  "remarks": {                                 // ข้อความเฉพาะ item ที่ fail/มีปัญหา
    "brake-foot": "เหยียบแล้วลึกผิดปกติ ต้องไล่วงลม",
    "fork": "งามีร่องรอยสึกหน้า"
  },
  "main_remark": "พบเบรกเท้าลึก + งาสึก แจ้งกะถัดไปเฝ้าระวัง",
  "manhourMeter": "12450",
  "status": "unsafe",                          // เพราะมี fail ใน results
  "created_at": "2026-08-02 06:35:00 +07:00",
  "updated_at": "2026-08-02 06:35:00 +07:00"   // (ครั้งแรก = created_at)
}
```

---

## 5. การส่งกลับไปกลับมา form ↔ firebase

### ขั้นตอนที่ 1 — Create (operator ส่งครั้งแรก)
- **ID:** ยังไม่มี → firestore **auto-generate**
- **created_at:** set = `Timestamp` (ISO +07:00)
- **updated_at:** = created_at (ครั้งแรก)

### ขั้นตอนที่ 2 — Read (firebase ส่งกลับให้ user แก้ไข)
- firebase return document (**มี id เดิม**) กลับมาให้ user เห็นค่าปัจจุบัน

### ขั้นตอนที่ 3 — Update (user แก้ไขแล้วส่งกลับ)
- **ใช้ id เดิม** `docRef.update(...)` — **ไม่สร้างใหม่**
- **updated_at:** อัปเดต = เวลาปัจจุบัน (บันทึก "แก้เมื่อไหร่")

### ตัวอย่างขั้นตอนที่ 3 (user แก้ `brake-foot` เป็น pass + ลบ remark)

```json
{
  "id": "dXkPq9nU2m4",                        // ⭐ id เดิม
  "date": "2026-08-02",
  "shift": "M",
  "shift_order": 1,
  "chassis_no": "Y1F2-402504",
  "flno_at_time": "PLBG-01",
  "operator_uid": "wiroj",
  "results": {
    "light-head": "pass",
    "brake-foot": "pass",                     // ← แก้จาก fail → pass
    "fork": "fail"                            // ← ยัง fail
  },
  "remarks": {
    "fork": "งามีร่องรอยสึกหน้า"               // ← ลบ remark ของ brake-foot ออกแล้ว
  },
  "main_remark": "แก้เบรกแล้ว เหลืองาสึก รอซ่อม",
  "manhourMeter": "12452",
  "status": "unsafe",                         // ยังมี fork=fail
  "created_at": "2026-08-02 06:35:00 +07:00", // ← ไม่เปลี่ยน
  "updated_at": "2026-08-02 07:10:00 +07:00"  // ← เปลี่ยน (ถูกแก้ไข)
}
```

### 🔁 สรุปการส่งกลับไปกลับมา

```
Create ──▶ firebase (auto-gen id) ◀── responses
  │                  ▲
  ▼                  │ Read (ส่งกลับด้วย id เดิม)
Firebase return ◀── user แก้ไข
  │   (id เดิม + updated_at ใหม่) ──▶ Update (docRef.update)
  └── ใช้ id เดิม, ไม่สร้างซ้ำ, updated_at เปลี่ยน
```

---

## 6. Checklist (รอ implement)

- [ ] รอข้อมูลบริษัทให้เช่ารถ → เพิ่ม/ลด/แก้ item + category (+ ปรับ `order`)
- [ ] เพิ่ม `order`/`enabled` ใน model `ChecklistItem` (ตอนนี้ยังไม่มี field)
- [ ] Template item เก็บใน firestore (`checklist_items`) เป็น source ของลำดับ/การเปิดใช้งาน
- [ ] UI render ตาม template (sorted by order, skip enabled=false)
- [ ] `created_at`/`updated_at` (ISO +07:00) + `id` auto-gen/update ตาม id เดิม (ดู Doc 16)

---

> **หมายเหตุ:** เอกสารนี้เป็น **reference ของ item checklist + schema + flow** เพื่อให้คนทำงานตรงกัน
> รายการ item ปัจจุบันยัง "จำลอง" ก่อน รอข้อมูลจริงจากบริษัทให้เช่ารถแล้วค่อยขยาย
