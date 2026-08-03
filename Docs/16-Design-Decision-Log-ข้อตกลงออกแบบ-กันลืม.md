# 16 — Design Decision Log / ข้อตกลงออกแบบ (กันลืม)

> 📝 เอกสารนี้เก็บ **ข้อสรุป/ข้อตกลง** ที่เราคุยกันไว้ ยัง**ไม่ได้ implement** (บางส่วน)
> เพื่อกันลืม ให้กลับมาอ่านตอนลงมือทำจริง
> สรุปจาก session เกี่ยวกับ checklist / firebase / item list

---

## 1. Checklist Item List — ยังไม่ครอบคลุม (รอข้อมูลจากบริษัทให้เช่ารถ)

### สถานะ
- ใช้ **MockData** ปัจจุบันไปก่อน (29 รายการ / 6 หมวด) — ยังคงจำลองได้
- **รอรายละเอียดจากบริษัทให้เช่ารถ** ก่อนจะขยาย/แก้ category และรายการให้ครบ

### หมวด (categories) ที่มีตอนนี้: `ระบบไฟ, โครงสร้าง, ล้อ/ยาง, เบรก, ระบบควบคุม, อื่นๆ`

### ⭐ Design อนาคต — ทุก item ต้องมี (รอ implement)
แต่ละ ChecklistItem ต้องมี **`order` + `enabled`** เพื่อ:
- **`order`** — บังคับการเรียง (ลำดับตายตัว **ห้ามสลับไปสลับมา**)
- **`enabled`** — enable/disable รายการ (**บางรายการอาจถูกยกเลิกในอนาคต**)

```kotlin
data class ChecklistItem(
    val id: String,
    val label: String,
    val category: String,
    val order: Int,          // ✅ บังคับการเรียง (source ของลำดับ)
    val enabled: Boolean,    // ✅ false = รายการที่ถูกยกเลิก/ซ่อน (อนาคต)
)
```

> ⚠️ **ข้อสำคัญ:**
> - ลำดับการแสดงผลต้องมาจาก `order` (template) เสมอ — **ไม่ render จาก Map ตรง ๆ** (Map ไม่การันตีลำดับ)
> - item `enabled=false` → **ไม่โชว์ให้กรอก** แต่ผลเก่าใน history ยังอ่านได้ (จาก id)
> - (`enabled=false` ≠ ลบข้อมูล) — ป้องกันประวัติย้อนหลังเพี้ยน

---

## 2. ข้อมูลที่จะส่ง Firebase — Field Design

### results (ผลตรวจ) — แยกจาก remarks
```json
{
  "results": {
    "light-head": "pass",
    "light-tail": "fail"
  },
  "remarks": {
    "light-tail": "ไฟท้ายไม่ติด"   // ← เฉพาะ item ที่ fail/มีปัญหา
  }
}
```

| field | เก็บ | type |
|-------|-----|------|
| `results` | ผล pass/fail **ทุก item** | `Map<itemId, "pass"|"fail">` |
| `remarks` | ข้อความ **เฉพาะ item ที่มีปัญหา** | `Map<itemId, String>` |

> ใช้ **`itemId` เป็น key** โยง → รู้ตัว item ชัดเจน (ไม่ต้องเดาลำดับ/ตำแหน่ง)
> ห้ามเก็บเป็น array ตามตำแหน่ง (เดาไม่ได้ว่า index 0 = อะไร)

### "pass"/"fail" (string) vs true/false (boolean)
- **เราไม่ซีเรียส** ว่าใช้ string หรือ boolean — ง่ายสุดทั้ง frontend+backend
- ตรงนี้ยังใช้ `"pass"/"fail"` (สอดคล้อง code ปัจจุบัน)
- ถ้าจะเปลี่ยนเป็น boolean ต้องทำทั้งระบบ (ยังไม่เริ่ม) — แล้วแต่ตัดสินใจตอน implement

---

## 3. Timestamp — `created_at` / `updated_at` (✅ B1 + C1 ทำแล้ว)

### ข้อตกลง (Agreed ✅)
- ใช้ **`String` แบบ ISO 8601**
- **บังคับ timezone `+07:00`** (Asia/Bangkok)
- ตัวอย่าง: `"2026-08-02 06:35:00 +07:00"`

### เหตุผล
- **แปลงกลับไปกลับมาไม่ยุ่งยาก**
- **user ไม่เห็น** (ทำงานเบื้องหลังเงียบ ๆ) — user focus ที่ checklist ไม่ใช่เวลา
- คนไทยอ่าน/เข้าใจง่าย (เห็นเวลาไทยตรง ๆ ไม่ต้อง +7 ชั่วโมง)

### ✅ ที่ทำไปแล้ว
- B1: เพิ่ม field `updated_at` ใน `DailyChecksheet`, `ChecksheetEntity`, mapper
- C1: set ค่าจริงใน `submitChecksheet` — `created_at` = `updated_at` = `DateUtils.getNowIsoString()` (ISO 8601 +07:00)
- ครั้งแรก `updated_at` = `created_at`; ครั้งแก้ไข update `updated_at` ใหม่

### ยังไม่ตัดสินใจ / รอ
- format แน่นอน (มี `T` หรือช่องว่าง) — ใช้ `"yyyy-MM-dd HH:mm:ss Z"` เป็นกลาง (ยังปรับได้)

---

## 4. `id` — Auto-gen + Update ตาม id เดิม (ยังไม่ implement)

### ข้อตกลง (Agreed ✅)
- **ครั้งแรก (create):** firestore **auto-gen id** → เก็บไว้
- **ครั้งแก้ไข (update):** ใช้ **id เดิม** `docRef.update(...)` — **ไม่สร้างทุกครั้ง**

### เหตุผล
- ป้องกัน document ซ้ำ (สร้างใหม่ทุกครั้งผิด)
- ทำให้มีประวัติ/version ตาม id (audit) — "ใครแก้ตอนไหน" ผ่าน `updated_at`

---

## 5. Firebase — ใช้ Emulator จำลองก่อน (ยังไม่ได้ผูก Firestore จริง)

### สถานะ
- ปัจจุบัน repository เป็น **mock** (`Result.success("mock-id")`) — Firestore ยังปิด/comment
- จะใช้ **Firebase Emulator** จำลองการสร้าง/อัปเดต document ก่อน
- **ยังไม่ถึงขั้น implement ผูก Firestore จริง** (ตามลำดับข้อ 3/4)

### ขั้นตอนที่ทำเมื่อถึงเวลา
1. จำลองผ่าน emulator (ยิง create → รับ id → update ตาม id)
2. ผูก Firestore จริง (เปิด `firestore` ใน repository + ตั้ง `created_at`/`updated_at`)
3. เปิด item checklist dynamic (ถ้าถึงตอนนั้นได้ข้อมูลจากบริษัทให้เช่า)

---

## 6. สรุป pending works (รอลงมือ)

- [ ] **item checklist**: รอข้อมูลบริษัทให้เช่ารถ → เพิ่ม/ลด/แก้ item + category + เพิ่ม `order`/`enabled`
- [x] **`updated_at`**: เพิ่ม field ใน model/entity/mapper ✅ *(B1 — `DailyChecksheet`, `ChecksheetEntity`, `ChecksheetMapper`)*
- [x] **`created_at`/`updated_at`**: set เป็น ISO 8601 `+07:00` แทน `""` ✅ *(C1 — `DateUtils.getNowIsoString()` + `ChecklistViewModel.submitChecksheet`)*
- [ ] **id handling**: auto-gen (create) + update ตาม id เดิม (ไม่สร้างซ้ำ)
- [ ] **Firebase emulator**: จำลองสร้าง/อัปเดต document ก่อนผูกของจริง (repo ยังเป็น mock)
- [ ] **(option)** status `"skipped"` = ทั้งคันไม่ตรวจ — ยังไม่ทำ
- [x] ❌ ~~results เปลี่ยนเป็น 0/1/2~~ — **B2 ยกเลิก** (ยังใช้ `"pass"/"fail"` string)

---

## 7. ⚠️ JSON ที่จะส่ง firebase — สถานะหลังทำ B1 + C1

### สรุป: model `DailyChecksheet` อัปเดตแล้วบางส่วน (results ยัง pass/fail)

| field | model ปัจจุบัน | ควรเป็น (ตามข้อตกลง) | สถานะ |
|-------|---------------|----------------------|--------|
| `id` | `""` | firestore **auto-gen** | logic ถูกแล้ว ยังไม่ implement |
| `created_at` | `getNowIsoString()` (ISO +07:00) | ISO 8601 `+07:00` | ✅ C1 ทำแล้ว |
| `updated_at` | `getNowIsoString()` (= created ครั้งแรก) | ISO 8601 `+07:00` | ✅ B1 + C1 ทำแล้ว |
| `results` | `Map<String,String>` (`"pass"/"fail"`) | **ยังใช้ `"pass"/"fail"` (string)** — B2 (0/1) ยกเลิก | ✅ ตามเดิม |
| `status` | `"normal"/"unsafe"` | เพิ่ม `"skipped"` (ทั้งคันไม่ตรวจ) | ⏳ ยังไม่ทำ (option) |
| `shift` + `shift_order` | ✅ มีแล้ว | ✅ ตรงตามที่ต้องการ (ใช้อ้างอิง M/E/N) | ✅ |

### ตัวอย่าง JSON — แบบ Ideal (ตามข้อตกลงทั้งหมด)

```json
{
  "id": "dXkPq9nU2m4",
  "date": "2026-08-02",
  "shift": "N",
  "shift_order": 3,
  "chassis_no": "Y1F2-402504",
  "flno_at_time": "PLBG-01",
  "operator_uid": "wiroj",
  "results": {
    "light-head": "pass",
    "brake-foot": "fail",
    "horn": "pass"
  },
  "remarks": {
    "brake-foot": "เหยียบลึก ต้องไล่วงลม"
  },
  "main_remark": "",
  "manhourMeter": "12450",
  "status": "unsafe",
  "created_at": "2026-08-03 00:35:00 +07:00",
  "updated_at": "2026-08-03 00:35:00 +07:00"
}
```

> 📌 หมายเหตุ:
> - `results` ใช้ **`"pass"` / `"fail"` (string)** — ตาม code ปัจจุบัน (B2 ที่เคยทดลอง 0/1 ยกเลิกแล้ว) — `2`(not_checked) เป็น **UI state เท่านั้น** ไม่เก็บใน firebase (กะไม่ตรวจ = ไม่มี doc)
> - `created_at`/`updated_at` = ISO 8601 `+07:00` (ครั้งแรก updated = created)
> - `status` (option) เพิ่มกรณี `"skipped"` = ทั้งคันไม่ได้ตรวจ (ยังไม่ทำ)

---

> **หมายเหตุ ณ session ล่าสุด:** เป็น **บันทึกข้อตกลง (decision log)** + สถานะการ implement
> - ส่วนที่ ✅ = **implement/ทดสอบผ่านแล้ว** (B1/C1/C2/C3)
> - ส่วนที่ ⏳ / ยังไม่ขีด = ยังรอลงมือ (item checklist, id auto-gen, firebase จริง, status skipped)
