# 🔄 สรุปงาน ตั้งแต่ `git pull` — ตรวจพบ 7 Bugs และแก้ไข

> รวบรวมปัญหาที่ตรวจพบ จาก commit `2e8f61b feat: add QR Scanner, shift-aware checklist tracking, and 16KB alignment fix`
> ทั้งหมดได้รับการแก้ไขและผ่านการ compile แล้ว — รวมงานย่อยอีก 2 ส่วน คือ **SHIFT CYCLE** และ **Ktlint Plugin**

---

## 📌 ภาพรวมสถานะก่อนเริ่ม

- ทำ `git pull` ได้ commit ล่าสุดเป็น **`2e8f61b`** (feat: add QR Scanner, ...)
- ตรวจ code review พบ **7 Bugs** — ระดับ 🔴 critical ไปจนถึง 🟡 minor
- งานที่ทำใน session นี้:
  1. แก้ **7 Bugs**
  2. ปรับปรุง **SHIFT CYCLE**
  3. ติดตั้ง **Ktlint plugin** + จัดรูปแบบ code (`ktlintFormat`)

---

## 🐛 7 Bugs ที่ตรวจพบ & วิธีแก้ไข

### 🐛 Bug 1: `shiftKey` ไม่แยกช่วงเวลา M/E/N ของวันเดียวกัน — 🔴 critical

**ปัญหา:**
- ข้อมูล checksheet ใช้ `date` (เช่น `2026-07-30`) เป็น key → กะ M/E/N ของวันเดียวกันทับกัน ไม่รู้ว่าบันทึกนี้เป็นกะไหน (M/E/N)

**วิธีแก้:**
- แนวคิดแยกเป็น **2 concepts**:
  - 📅 **ตารางเวร (8-day cycle)** — "ทีมไหนเข้าบรรวันนี้" (M/E/N/O)
  - ⏱️ **กะตามช่วงเวลา (clock time)** — "ตอนนี้เป็นเวลากะไหน" (M=06-14, E=14-22, N=22-06)
- บันทึกลง checksheet ทั้ง **`shift`** และ **`shift_order`** → แยก M/E/N ในวันเดียวกันได้ชัดเจน
- ไฟล์: `core/domain/usecase/shift/GetCurrentShiftUseCase.kt`

---

### 🐛 Bug 2: `GetCurrentShiftUseCase` ไม่มีเวลา — คำนวณ shift ผิด — 🔴 critical

**ปัญหา:**
- เดิมใช้ `LocalDate.now()` อย่างเดียว ไม่มีส่วนของเวลา → คำนวณกะตามนาฬิกา (M/E/N) ไม่ได้

**วิธีแก้:**
- เพิ่มฟังก์ชัน **`getShiftByTime(time: LocalTime)`** คำนวณกะตาม clock time:
  - `22:00 – 05:59` → **N** (กลางคืน)
  - `06:00 – 13:59` → **M** (เช้า)
  - `14:00 – 21:59` → **E** (บ่าย)
- เพิ่ม **`getShiftOrder(shift)`** ให้ `shift_order` (M=1, E=2, N=3) สำหรับเรียงลำดับกะ
- ไฟล์: `core/domain/usecase/shift/GetCurrentShiftUseCase.kt`

---

### 🐛 Bug 3: `SubmitChecksheetUseCase` ใช้ `submitChecksheet.invoke()` แต่ receiver ผิด — 🔴 critical

**ปัญหา:**
- เดิมเรียก wrong receiver/incorrect invoke pattern → การ submit ผิดจุด

**วิธีแก้:**
- Implement `operator fun invoke(checksheet: DailyChecksheet)` ให้ถูกต้อง → เรียก `submitChecksheet.invoke(checksheet)`
- ตัว `submitChecksheet()` ใน ViewModel เรียกผ่าน `submitChecksheet.invoke(checksheet)` อย่างถูกต้อง
- ไฟล์: `core/domain/usecase/checklist/SubmitChecksheetUseCase.kt` + `feature/checklist/ChecklistViewModel.kt`

---

### 🐛 Bug 4: `ChecklistViewModel.selectVehicle()` ใช้ `currentShift = "M"` hardcode — 🔴 critical

**ปัญหา:**
- เดิม `currentShift` ถูก hardcode เป็น `"M"` → copy-forward ดึงมาแต่กะเช้าเสมอ

**วิธีแก้:**
- ใช้ `getShiftUseCase.getShiftByTime().name` แทน hardcode → copy-forward ดึง checksheet ของกะเดียวกันตามเวลาจริง
- **สำคัญ:** ใส่ shift จริงลงใน DailyChecksheet (`shift = currentShift.name`, `shift_order = getShiftUseCase.getShiftOrder(currentShift)`)

```kotlin
// ก่อน (Bug)          // หลัง (แก้ไข)
currentShift = "M"     currentShift = getShiftUseCase.getShiftByTime().name
```

- ไฟล์: `feature/checklist/ChecklistViewModel.kt`

---

### 🐛 Bug 5: ข้อมูล `checkedVehicles` ไม่ persist (จำไม่ได้ตอน restart) — 🟡 minor

**ปัญหา:**
- เดิมใช้ `remember { mutableStateMapOf() }` ใน `MainActivity` → **State อยู่ใน memory** ปิด app / หมุนจอ → ข้อมูล "รถที่ตรวจแล้ว" หาย

**วิธีแก้:**
- สร้าง **`CheckedVehicleStore`** (`@Singleton`) เก็บเป็น JSON ลง **SharedPreferences** โดย key = วันที่
  ```
  date "2026-07-30" → { "FOK-001": "08:45 โดย wiroj", "FOK-002": "09:10 โดย wiroj" }
  ```
- มี 3 ฟังก์ชัน: `getCheckedVehicles(date)`, `setCheckedVehicle(date, chassisNo, info)`, `removeCheckedVehicle(date, chassisNo)`
- ไฟล์: `core/data/local/CheckedVehicleStore.kt` (ไฟล์ใหม่ — `??` ใน git status)

---

### 🐛 Bug 6: `ScannerScreen` ใช้ `rememberPermissionState` — deprecated ใน Accompanist ใหม่ — 🟡 minor

**ปัญหา:**
- `com.google.accompanist.permissions.*` — API เก่าถูก deprecate ในเวอร์ชันใหม่

**วิธีแก้:**
- ยังใช้ Accompanist เวอร์ชันที่ project ตั้ง (`0.34.0`) แต่ย้าย/จัดโครงสร้าง permission handling ให้ถูกต้อง และ resolve เรื่อง **`ResolutionSelector`** — แทน `setTargetResolution` ที่ deprecate

```kotlin
val resolutionSelector =
    ResolutionSelector.Builder()
        .setResolutionStrategy(
            ResolutionStrategy(
                Size(1280, 720),
                ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER,
            ),
        ).build()
```

- ไฟล์: `feature/scan/ScannerScreen.kt`

---

### 🐛 Bug 7: `chassis_no` case-sensitive ระหว่าง QR กับ MockData — 🟡 minor

**ปัญหา:**
- QR scan ได้ `chassis_no` อาจเป็น uppercase/lowercase ต่างจากใน `MockData.vehicles` → หารถไม่เจอ

**วิธีแก้:**
- ใช้ **`v.chassis_no.equals(trimmedCode, ignoreCase = true)`** → ไม่คำนึงตัวพิมพ์เล็ก/ใหญ่ + `code.trim()` ลบช่องว่าง
- ไฟล์: `feature/scan/ScanViewModel.kt`

---

## 🔁 SHIFT CYCLE (งานย่อย 2)

**สิ่งที่ปรับปรุง:**

| Concept | รายละเอียด |
|--------|-----------|
| 📅 **ตารางเวร (8-day cycle)** | `SHIFT_CYCLE = ["M","M","E","E","N","N","O","O"]` — ทีม A/B/C/D หมุนเวียนไม่ชนกันด้วย offset ต่างกัน (A=3, B=5, C=7, D=1) |
| 🏗️ **BASE_DATE** | `2026-01-01` — เริ่มต้นรอบแรกของ cycle |
| ⏱️ **กะตาม clock time** | `M=06-14, E=14-22, N=22-06` — ใช้บันทึกลง `DailyChecksheet.shift` + `shift_order` |

**ผลลัพธ์:** ระบบรู้ทั้ง "ทีมไหนอยู่กะไหน" (เวร) และ "ตอนนี้เป็นกะไหน" (เวลา) → copy-forward + report ตรงกับกะที่แท้จริง

ไฟล์: `core/common/constants/ShiftConstants.kt`, `core/domain/usecase/shift/GetCurrentShiftUseCase.kt`

---

## 🧹 Ktlint Plugin (งานย่อย 3)

**สิ่งที่ทำ:**
1. เพิ่ม plugin `org.jlleitschuh.gradle.ktlint` version `14.2.0` ใน `build.gradle.kts` (root + app)
2. ตั้งค่าใน app `build.gradle.kts`:
   ```kotlin
   ktlint {
       android.set(true)          // 4-space indent (ตรงกับ VSCode)
       ignoreFailures.set(true)   // lint ไม่พัง build ระยะแรก
       filter { exclude("**/generated/**") }
   }
   ```
3. รัน `./gradlew :app:ktlintFormat` → **autofix ทั่วโปรเจกต์** (indent, trailing comma, import order, space, semicolon...)

> 📌 รายละเอียดเต็มอยู่ใน **`Docs/06-Ktlint-Setup-and-ktlintFormat.md`**

---

## ✅ สรุปไฟล์ที่แก้/เพิ่ม

| หมวด | ไฟล์ | สถานะ |
|:----:|------|:-----:|
| Shift | `GetCurrentShiftUseCase.kt`, `ShiftConstants.kt` | ✅ M |
| Checklist | `ChecklistViewModel.kt` (Bug 3,4) | ✅ M |
| Submit | `SubmitChecksheetUseCase.kt` (Bug 3) | ✅ M |
| Persist | `CheckedVehicleStore.kt` (Bug 5) | ✅ **ใหม่** (`??`) |
| Scan | `ScannerScreen.kt`, `ScanViewModel.kt` (Bug 6,7) | ✅ M |
| Plugin | `build.gradle.kts` (root+app) + `.kt` format ทั่วไป | ✅ M |

> ⚡ **ยืนยัน:** `./gradlew :app:compileDebugKotlin` → ✅ **BUILD SUCCESSFUL** (ทุก Bug แก้แล้ว compile ผ่าน)
>
> 📝 **สรุปโดย:** likit_s  |  📅 **วันที่:** 30 กรกฎาคม 2026  |  🔧 **สถานะ:** แก้ครบ 7 Bugs + SHIFT CYCLE + Ktlint
