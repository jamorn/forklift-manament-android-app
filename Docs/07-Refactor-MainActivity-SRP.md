# 🧹 Refactor `MainActivity.kt` — ใช้หลัก SRP (Single Responsibility)

> สรุปการแยก `MainActivity.kt` ที่เป็น "ศูนย์รวม" UI ออกไปเป็น feature modules
> ให้เพื่อนร่วมงานที่ Mac ที่บ้าน (หรือต่อยอดในเครื่องอื่น) อ่านเข้าใจบริบทและรับงานต่อได้เลย
>
> ต่อเนื่องจาก `Docs/05-Summary-From-Git-Pull.md` และ `Docs/06-Ktlint-Setup-and-ktlintFormat.md`

---

## 📌 ปัญหาที่พบ (ทำไมต้อง refactor)

เดิม **`MainActivity.kt`** (~306 บรรทัด) เป็น "God file" — รวมหลายบทบาทไว้ในไฟล์เดียว แม้ project จะจัดโฟลเดอร์เป็น **feature-based** แล้วก็ตาม:

| สิ่งที่อยู่ใน MainActivity เดิม | บทบาท                                   |
| ------------------------------- | --------------------------------------- |
| `class MainActivity`            | Activity (entry point + Hilt injection) |
| `fun AppRoot`                   | Navigation logic (screen state)         |
| `fun MainMenuScreen`            | หน้า Menu (UI screen)                   |
| `fun MenuButton`                | ปุ่มเมนู (reusable component)           |
| `fun VehicleListScreen`         | หน้าเลือกรถ + ตารางเวร (UI screen)      |
| `fun VehicleCard`               | การ์ดรถ (reusable component)            |

**ขัดหลัก Single Responsibility (SRP)** — ไฟล์เดียวทำหลายอย่าง → แก้ยาก, reuse ไม่ได้, merge conflict ง่าย, test ยาก

---

## 🎯 หลักการที่ใช้ (สำคัญ — อ่านก่อนแก้ต่อ)

1. **SSOT (Single Source of Truth) ระหว่าง refactor**
   - **สร้าง code ใหม่ใน `feature/*` ก่อน** → re-check (compile/ktlint) → **ค่อยลบของเก่า**
   - ทำให้ไม่มีช่วงที่ code "พัง" กลางทาง เหมือนไม่ได้แก้เลย
2. **ไม่แก้ข้ามขั้น** — ทำทีละ step แล้ว re-check ทุกครั้ง
3. **Feature module ที่มีอยู่แล้ว** ใน project เช่น `feature/auth`, `feature/checklist`, `feature/dashboard` → ใช้ pattern เดียวกัน

---

## 🔍 4 Steps ที่ทำไปแล้ว (เสร็จสมบูรณ์)

### Step 1 — `feature/menu/` (MainMenuScreen + MenuButton)

- สร้างไฟล์ใหม่: `feature/menu/MainMenuScreen.kt`
- copy `MainMenuScreen` + `MenuButton` ออกมา (package `com.irpc.forklift.feature.menu`)
- **compile ✅** → commit `dccdd85`

### Step 2 — `feature/vehicles/` (VehicleListScreen + VehicleCard)

- สร้างไฟล์ใหม่: `feature/vehicles/VehicleListScreen.kt`
- copy `VehicleListScreen` + `VehicleCard` ออกมา (package `com.irpc.forklift.feature.vehicles`)
- **compile ✅** → commit `baff6b2`

### Step 3 — ลบของเก่าออกจาก `MainActivity.kt` + ใช้ feature/\*

- เปลี่ยน import → `feature.menu.MainMenuScreen`, `feature.vehicles.VehicleListScreen`
- ลบ `MainMenuScreen`, `MenuButton`, `VehicleListScreen`, `VehicleCard` (ตัวใน `com.irpc.forklift`) ออก
- ลบ imports ที่ไม่ใช้ (ยกเว้นตัวที่ `AppRoot`/`onCreate` ใช้จริง เช่น `fillMaxSize`)
- รัน `ktlintFormat` จัดเหลือเฉพาะที่จำเป็น
- **compile ✅** → commit `f819459`

---

## 📊 ผลลัพธ์

| ไฟล์                                    |    เดิม     |                      หลัง refactor                      |
| --------------------------------------- | :---------: | :-----------------------------------------------------: |
| `MainActivity.kt`                       | ~306 บรรทัด |              **~127 บรรทัด** (−300 บรรทัด)              |
| เหลืออะไรใน MainActivity                |      —      | เฉพาะ `class MainActivity` + `fun AppRoot` (navigation) |
| `feature/menu/MainMenuScreen.kt`        |  — (ใหม่)   |             `MainMenuScreen` + `MenuButton`             |
| `feature/vehicles/VehicleListScreen.kt` |  — (ใหม่)   |           `VehicleListScreen` + `VehicleCard`           |

### ✅ ยืนยัน

- **BUILD SUCCESSFUL** (ผ่านทุก re-check ทั้ง compile + ktlintFormat)
- **MainActivity กลายเป็นไฟล์เล็ก clean** — ทำหน้าที่ Activity + root navigation เท่านั้น (ครบ SRP)
- **4 commits** push ขึ้น `origin/master` แล้ว

---

## 🧭 Dependencies / อ้างอิงระหว่างไฟล์หลัง refactor

```
MainActivity.kt (AppRoot)
  ├─ LoginScreen            (feature/auth)
  ├─ MainMenuScreen         (feature/menu)  ◀── ย้ายมาใหม่
  ├─ VehicleListScreen      (feature/vehicles) ◀── ย้ายมาใหม่
  ├─ ScannerScreen          (feature/scan)
  ├─ NewChecklistScreen     (feature/checklist)
  ├─ SupervisorDashboard    (feature/dashboard)
  ├─ MaintenanceScreen      (feature/maintenance)
  └─ ReportScreen           (feature/report)
```

- `VehicleListScreen` (feature/vehicles) **ยังใช้ `MockData` + `StatusBadge`** — มีการ import ของตัวเองครบแล้ว
- `VehicleCard` (feature/vehicles) ใช้ **`StatusBadge` จาก `ui/components/`** ✓

---

## 🚀 งานที่รับไปทำต่อได้ (สำหรับ Mac ที่บ้าน / คนถัดไป)

> 💡 สิ่งเหล่านี้ **ยังไม่ได้ทำ** — เหมาะเป็นงานถัดไป

### 1. ตรวจ `AppRoot` navigation — ยังเป็น String-based (`screen = "..."`)

ปัจจุบัน navigation ใน `AppRoot` ใช้ `when(screen)` กับ String ควรพิจารณา:

- ย้ายไปใช้ **`Navigation Compose`** (`androidx.navigation`) หรือ
- ใช้ **enum/sealed class** เป็น screen destination (type-safe ขึ้น)

> ⚠️ หมายเหตุ: `navigation/AppNavigation.kt` ยังเป็น `TODO` อยู่ (ไม่ได้ใช้งานจริงใน MainActivity ตอนนี้) — ปิดไว้เป็นงานถัดไป

### 2. ตรวจไฟล์ "God file" อื่นที่ยังอาจมี

- ดูว่ายังมีไฟล์รวมหลายบทบาทแบบเดิมอีกไหม (เช่นพวก `*.kt` ที่ยาวมาก >300 บรรทัด)

### 3. ลางาน `shift_order` / copy-forward (ถ้าจะทำต่อ)

ตามที่คุยกันใน session นี้ — `shift_order` เป็นแค่ utility ไม่มีผลกับ Firebase โฟกัสงานหลักก่อนได้

### 4. `ktlintFormat` ควรวิ่งเป็น routine

`ignoreFailures=true` ตั้งไว้ช่วงแรก — เมื่อพื้นฐานเรียบร้อยแล้ว ควรเปิดให้เข้มขึ้นใน CI (ตั้ง `ignoreFailures=false` + `.editorconfig` ปรับ rule ที่ขัดกับ Compose)

---

## ✅ สรุป Quick Reference

| ประเด็น       | สรุป                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **งานวันนี้** | แยก `MainActivity.kt` (God file) → `feature/menu` + `feature/vehicles`           |
| **หลัก**      | SSOT (สร้างใหม่ก่อน → re-check → ค่อยลบ) + ทำทีละ step                           |
| **ผล**        | MainActivity เหลือ Activity + AppRoot (~−300 บรรทัด)                             |
| **สถานะ**     | ✅ compiled + ktlint + push 4 commits                                            |
| **ต่อยอด**    | refactor navigation ให้ type-safe, ตรวจ God file อื่น, ค่อยตั้ง ktlint เข้มใน CI |

---

> 📝 **สรุปโดย:** likit_s | 📅 **วันที่:** 30 กรกฎาคม 2026 | 🔧 **สถานะ:** refactor เสร็จ 3 steps (menu/vehicles/MainActivity)
>
> 📚 **อ่านประกอบ:** `Docs/05-Summary-From-Git-Pull.md` (7 bugs), `Docs/06-Ktlint-Setup-and-ktlintFormat.md` (ktlint)
