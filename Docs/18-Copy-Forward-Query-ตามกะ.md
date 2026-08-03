# 18 — Copy-Forward Query ตามกะ (การหาข้อมูลกะก่อนหน้า)

> 📋 เอกสารนี้สรุป **Flow การ"ยิงถาม" firebase เพื่อหา "กะก่อนหน้า" (copy-forward)**
> โดยอาศัย `DateUtils.getWorkDate()` + `shift` (M/E/N)
> มาใช้ตัดสินใจว่า จะถาม firebase ด้วย `date` + `shift` อะไร
>
> **สถานะ ✅:** logic "map กะก่อนหน้า" + query **implement แล้ว** (C3)
> - `GetPreviousChecksheetUseCase` คำนวณ prevDate/prevShift (ตัวเลือก B) + fail-fast guard
> - `ChecksheetRepositoryImpl.getPreviousChecksheet` เตรียม query firebase ตามนี้ไว้ (ยัง comment จนผูกของจริง)

---

## 1. แนวคิดหลัก (สำคัญที่สุด)

**เราไม่ต้องยุ่งกับทีม A/B/C/D ในการหา copy-forward**
เพราะเราสนใจแค่ **"วันทำงาน (work date)" + "กะ M/E/N"** เท่านั้น

- ระบบรู้อยู่แล้วว่า **ตอนนี้เป็นกะอะไร** จาก `getShiftByTime()`
- ระบบรู้อยู่แล้วว่า **วันทำงาน (work date) คือวันไหน** จาก `DateUtils.getWorkDate()`
- ฉะนั้น "กะก่อนหน้า" + "วันที่ต้องถาม" **เป็นตารางตายตัว** แน่นอนเสมอ

---

## 2. ตาราง "กะก่อนหน้า + date ที่จะถาม"

| กะปัจจุบัน (ตอนนี้) | กะก่อนหน้า | date ที่ถาม firebase (หา via getWorkDate) | shift ที่ถาม |
|--------------------|------------|------------------------------------------|--------------|
| **E** (บ่าย 14-22) | M | `getWorkDateString(now)` → date ของ **วันนี้** | `"M"` |
| **N** (ดึก 22-06) | E | `getWorkDateString(now)` → date ของ **วันนี้/วันทำงาน** | `"E"` |
| **M** (เช้า 06-14) | N | `getWorkDateString(now.minusDays(1))` → date ของ **เมื่อวาน** | `"N"` |

> ⭐ **กุญแจสำคัญของกะดึก (N):**
> `getWorkDate()` มี logic `hour < 6` → ย้อนวันให้ถูก
> ทำให้ **ก่อนเที่ยงคืน & หลังเที่ยงคืน** หา "กะ E" จาก **`date` เดียวกัน** ได้เลย

---

## 3. เหตุผลว่าทำไม `getWorkDate()` ครอบคลุมกะดึกทั้งก่อน/หลังเที่ยงคืน

จาก `DateUtils`:
```kotlin
fun getWorkDate(now: ZonedDateTime = ZonedDateTime.now(BANGKOK_ZONE)): LocalDate =
    if (now.hour < 6) {
        now.minusDays(1).toLocalDate()   // 00:00–05:59 → ย้อนเป็นเมื่อวาน (วันเริ่มกะ)
    } else {
        now.toLocalDate()                // ตั้งแต่ 06:00 → วันปัจจุบัน
    }
```

### ตัวอย่าง: ตอนนี้คือกะดึก N อยากหา "กะ E" (ดึง copy-forward)

สถานการณ์ operator กะ N ทำงานคาบ 2 วัน (22:00–06:00):

| เวลาจริงที่กด | `getWorkDateString(now)` | date ที่ถามหา E | firebase query |
|--------------|--------------------------|----------------|----------------|
| 22:17 (ก่อนเที่ยงคืน) | `2026-08-02` | `2026-08-02` | `date=02, shift=E` |
| 00:30 (หลังเที่ยงคืน) | `2026-08-02` (ย้อนจาก 03) | `2026-08-02` | `date=02, shift=E` |

**ผลลัพธ์ทั้ง 2 กรณี ได้ `date` เดียวกัน (`2026-08-02`)!**
→ ใช้ `DateUtils.getWorkDateString(now)` ตัวเดียวได้ทั้งก่อน/หลังเที่ยงคืน
→ **ไม่จำเป็นต้องเขียน if/else แยก 2 เงื่อนไข** (เพราะ work date ย้อนให้เอง)

> ✅ สรุป: `getWorkDate()` ครอบคลุมทั้ง 2 ช่วงของกะดึกตามโจทย์

---

## 4. Query firebase (คำสั่งที่ "ยิงถาม")

เมื่อได้ `date` + `shift` ที่ชัดเจนแล้ว → ยิง firebase เพื่อหาว่ากะก่อนหน้ามี doc ไหม

> 📌 **ตัวเลือก B (implement แล้ว):** logic "map กะก่อนหน้า" อยู่ที่ **`GetPreviousChecksheetUseCase`**
> → Repo `getPreviousChecksheet(chassisNo, prevDate, prevShift)` รับค่าที่คำนวณเสร็จแล้ว ชัดเจน

### 📍 โค้ดจริง — `GetPreviousChecksheetUseCase.resolvePredecessor()`
```kotlin
private fun resolvePredecessor(currentDate: String, currentShift: String): Pair<String, String> =
    when (currentShift) {
        "M" -> ...minusDays(1) to "N"   // M → N เมื่อวาน
        "E" -> currentDate to "M"       // E → M วันนี้
        "N" -> currentDate to "E"       // N → E วันนี้
        else -> throw IllegalArgumentException("Unknown shift: $currentShift")   // fail-fast guard
    }
```

### 📍 โค้ดจริง — `ChecksheetRepositoryImpl.getPreviousChecksheet()`
```kotlin
// firestore
//     .collection(AppConstants.COLLECTION_CHECKSHEETS)
//     .whereEqualTo("chassis_no", chassisNo)   // 🔑 key — ไม่มีทางซ้ำ
//     .whereEqualTo("date", prevDate)          // work date ของกะก่อนหน้า
//     .whereEqualTo("shift", prevShift)        // กะก่อนหน้า M/E/N
//     .limit(1).get().await()
// (comment ไว้ — ยังไม่ผูก firestore จริง, ใช้ Result.success(null))
```

### หลักการใช้ key
- **[✅] ใช้ `chassis_no`** เป็น primary key — เพราะตัวถังไม่ซ้ำกันแน่นอน
- **[❌] ไม่ใช้ `flno_at_time`** — เพราะ FL no. อาจถูกสลับ/เปลี่ยนระหว่างรอบ

---

## 5. Processing ผลลัพธ์จาก firebase — 2 กรณี

### 🅰️ กรณีที่ 1 — firebase "เจอ" (มี document)
```kotlin
if (snapshot.hasDocument) {
    val prevCs = snapshot.first.toObject(DailyChecksheet::class.java)
    // → copy-forward เอาไปใช้
    //   ดึง results, remarks, main_remark, manhourMeter, date↑shift context
}
```

### 🅱️ กรณีที่ 2 — firebase "ไม่เจอ" (ไม่มี document)
```kotlin
else {
    // → กะก่อนหน้า ไม่ตรวจ (ไม่มีข้อมูล)
    //   แสดง floating label "กะก่อนไม่ได้ตรวจ" (C2 — NoPreviousChecksheetNotice)
    //   กะปัจจุบันกรอกผลเอง ("pass"/"fail") จากการดูรถจริงด้วยตาเปล่า
}
```

---

## 6. ความสัมพันธ์กับ 2 logic (มีข้อมูล / ไม่มีข้อมูล)

| เงื่อนไข | firebase ตอบ | ระบบทำ | สถานะ |
|----------|---------------|--------|-------|
| **กะก่อนตรวจแล้ว (มี doc)** | เจอ (has document) | copy-forward (results/remarks/meter/context) | ✅ `ChecklistForm` แสดง `CopyForwardBanner` |
| **กะก่อนไม่ตรวจ (ไม่มี doc)** | ไม่เจอ (null) | floating label **"กะก่อนไม่ได้ตรวจ"** + กรอกเอง | ✅ C2 — `NoPreviousChecksheetNotice` |

> 🔎 **จนข้อเท็จจริง:** ฟอร์ม **บังคับให้กรอกครบทุก item ก่อน submit** (valid)
> จึง**ไม่มีทาง** submit แล้วเป็น "not_checked" ได้
> ฉะนั้น "กะที่ไม่ตรวจ" = **ไม่มี document ใน firebase** ตรวจสอบจาก "ไม่มีข้อมูล"

---

## 7. สรุป pipeline ของ copy-forward

```
┌─ 1. getShiftByTime()  ──► รู้ "กะปัจจุบัน" (M/E/N)
│
├─ 2. DateUtils.getWorkDate(now) ──► รู้ "work date" (ข้ามเที่ยงคืนถูกต้อง)
│
├─ 3. GetPreviousChecksheetUseCase.resolvePredecessor()  (ตัวเลือก B)
│       M → N เมื่อวาน (currentDate - 1)
│       E → M วันนี้
│       N → E วันนี้
│       else → throw IllegalArgumentException (fail-fast)
│
├─ 4. ChecksheetRepositoryImpl.getPreviousChecksheet(chassis_no, prevDate, prevShift) limit 1
│       (พร้อม query จริง — ยัง comment จนผูก firestore)
│
├─ 5. firebase ตอบ?
│       • เจอ doc  → copy-forward (CopyForwardBanner)
│       • ไม่พบ   → NoPreviousChecksheetNotice "กะก่อนไม่ได้ตรวจ" + กรอกเอง (C2)
│
└─ Done
```

---

## 8. หมายเหตุ / เรื่องที่ควรระวัง

1. **ใช้ `chassis_no` เป็น key เสมอ** (ไม่ซ้ำ) ไม่ใช่ flno
2. **`getWorkDate()` ใช้ตัวเดียว** ครอบคลุมกะดึกทั้งก่อน/หลังเที่ยงคืน — ไม่ต้อง if/else แยกเอง
3. **copy-forward เฉพาะกรณี "มี doc"** เท่านั้น — กรณีไม่มี doc คือกะไม่ตรวจ → เริ่มกรอกเอง
4. **`main_remark`/`remarks`** — ตามที่คุย (Doc 14/16) เพิ่มเติมอีกส่วน ยังเป็นไปตาม business logic ที่ตกลง
5. ✅ **Implement แล้ว (C3 ตัวเลือก B)**: logic map กะก่อนหน้า → `GetPreviousChecksheetUseCase.resolvePredecessor()` (พร้อม fail-fast guard) + Repo เตรียม query ไว้
6. ✅ **Implement แล้ว (C2)**: floating label "กะก่อนไม่ได้ตรวจ" → `NoPreviousChecksheetNotice`
7. ⏳ **ยังรอ**: ผูก Firestore จริง (เปิด query ใน Repo) — ตอนนี้ยัง `Result.success(null)`

---

> **หมายเหตุรวม:** เอกสารนี้อ้างอิง Docs 13 (กะข้ามวัน/work date), Docs 14 (3 กะ copy-forward),
> Docs 16 (ข้อตกลง design) และโค้ดจริง `DateUtils.kt`, `GetCurrentShiftUseCase.kt`
