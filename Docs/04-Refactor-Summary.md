# 🔄 สรุปการ Refactor: Forklift Android App

> รวบรวมการเปลี่ยนแปลงทั้งหมด — **ทำอะไร? ทำไมถึงต้องทำ?**

---

## 📌 หลักการ Refactor

| ข้อ | หลักการ | สาเหตุ |
|:--:|--------|--------|
| 1 | **ของเทียม → ของจริง** | ไฟล์ส่วนใหญ่เป็น `// TODO` skeletons (object เปล่า) ต้อง implement ให้ใช้งานได้ |
| 2 | **Hardcode → DI** | สิ่งที่เขียนตายตัวใน Activity ต้องย้ายไปใช้ Hilt injection |
| 3 | **ซ้ำซ้อน → Reusable** | Component ที่แยกกันหลายที่ ถูกรวมเป็น shared components |
| 4 | **ของเก่า → ของใหม่** | API เก่าถูก deprecate หรือลบ ต้องอัปเดต |
| 5 | **Windows Compat** | โปรเจกต์ถูกออกแบบให้ dev บน Mac ต้องปรับให้รันบน Windows ได้ |

---

## 📦 1. DI AppModule (1 ไฟล์)

### ไฟล์: `di/AppModule.kt`

| ก่อน | หลัง |
|------|------|
| `object AppModule { // TODO }` | `@Module @InstallIn(SingletonComponent::class) object AppModule` |

### ทำไมต้องทำ?
- Hilt ต้องการ `@Module` ที่มี `@Provides` หรือ `@Binds` จริง
- ViewModels ทั้งหมด (`LoginViewModel`, `ChecklistViewModel`, `SupervisorDashboardViewModel`) inject repositories ผ่าน constructor
- ถ้าไม่มี module → Hilt จะฟ้อง `MissingBinding` ตอน build

### Provides ที่เพิ่ม:
- `AuthRepository` → `AuthRepositoryImpl`
- `DepartmentRepository` → `DepartmentRepositoryImpl`
- `VehicleRepository` → `VehicleRepositoryImpl`
- `ChecksheetRepository` → `ChecksheetRepositoryImpl`

---

## 🔐 2. Auth Feature (3 ไฟล์)

### ก่อน
```
feature/auth/
├── LoginScreen.kt      ← object { // TODO }
├── LoginViewModel.kt   ← object { // TODO }
└── components/
    └── MockUserSelector.kt  ← object { // TODO }
```

### หลัง
```
feature/auth/
├── LoginScreen.kt          ← @Composable จริง — แสดงฟอร์ม login
├── LoginViewModel.kt       ← @HiltViewModel จริง — mockLogin() + loginWithGoogle()
└── components/
    └── MockUserSelector.kt ← @Composable จริง — เลือก user ทดสอบ (SA/Admin/Operator)
```

### ทำไมต้องทำ?
- แอปต้องมี entry point ผ่าน Login (ไม่งั้นเข้าใช้เลยไม่ได้)
- ตอนนี้ใช้ Mock ก่อน → อนาคตเปลี่ยนเป็น Firebase Auth
- ต้องการทดสอบ Role-based access (SA, Admin, Operator)

---

## 🧩 3. UI Components (9 ไฟล์)

### ไฟล์ทั้งหมดใน `ui/components/`

| Component | เปลี่ยนจาก | เป็น | ทำไมต้องทำ? |
|-----------|-----------|-----|-------------|
| `AppBadge.kt` | `// TODO` | Badge พร้อมสีสถานะ | ต้องการ badge reuse ในหลายหน้า |
| `AppButton.kt` | `// TODO` | Primary/Danger/Outline + Loading | ปุ่มมีหลาย style, กัน code ซ้ำ |
| `AppCard.kt` | `// TODO` | Card wrapper | 統一 card style ทั่วแอป |
| `AppInput.kt` | `// TODO` | Text field + Error state | ต้องการ input ที่มี error validation |
| `AppModal.kt` | `// TODO` | Dialog (ใช้ `Dialog` + `Card`) | `AlertDialog` API ไม่ Compat กับ BOM 2024.01.00 |
| `LoadingSpinner.kt` | `// TODO` | FullScreen + Small spinner | กัน code progress bar ซ้ำ |
| `SkeletonLoader.kt` | `// TODO` | Skeleton พร้อม Shimmer | UX ขณะโหลดข้อมูล |
| `ToastHost.kt` | `// TODO` | Snackbar wrapper | แสดง notification ทั่วแอป |
| `StatusBadge.kt` | `// TODO` | ใช้ `AppBadge` ข้างใน | badge สถานะรถ ใช้หลายที่ |

### ทำไมต้องทำ?

```
ก่อน:   ChecklistScreen มี FilterChip + StatusBadge เขียนซ้ำ
        DashboardScreen มี Card + Badge เขียนซ้ำ
        LoginScreen มี Button เขียนซ้ำ
        
หลัง:   ทั้งแอปใช้ ui/components/* ร่วมกัน
        → แก้ครั้งเดียว ใช้ได้ทุกที่
```

---

## 📋 4. Checklist Feature (11 ไฟล์)

### ก่อน
```
feature/checklist/
├── ChecklistViewModel.kt   ← object { // TODO }
├── ChecklistEvent.kt       ← sealed class { // TODO }
├── ChecklistScreen.kt       ← อยู่ใน MainActivity (hardcode)
└── components/
    ├── CategorySection/     ← // TODO
    ├── CheckItemRow/        ← // TODO
    ├── ChecklistForm.kt     ← // TODO
    ├── CopyForwardBanner.kt ← // TODO
    ├── ManhourMeterInput/   ← // TODO
    ├── OfflineIndicator.kt  ← // TODO
    ├── SuccessScreen/       ← // TODO
    └── VehicleSelector.kt   ← // TODO
```

### หลัง
```
feature/checklist/
├── ChecklistViewModel.kt   ← @HiltViewModel จริง (selectVehicle, checkItem, submit, reset)
├── ChecklistEvent.kt       ← Sealed class จริง (OnVehicleSelected, OnCheckItem, SubmitChecksheet)
├── ChecklistScreen.kt      ← @Composable จริง 3 Steps (VehicleSelector → Form → Success)
└── components/  ← ทุกอันเป็น @Composable จริง
```

### ทำไมต้องทำ?
- **Checklist คือ core feature** ของแอป — ต้องทำงานก่อน Dashboard หรือ Report
- แยกออกจาก `MainActivity` (เดิมเขียน hardcode อยู่ใน Activity ใหญ่ไฟล์เดียว)
- ViewModel ใช้ `@HiltViewModel` เพื่อ inject repositories + usecases
- UI components แยกกันเป็นสัดส่วน (CategorySection, CheckItemRow, etc.)
- Copy-Forward Banner เตรียมไว้สำหรับ logic ดึงข้อมูล checksheet ก่อนหน้า

---

## 📊 5. Dashboard Feature (5 ไฟล์)

### ก่อน
```
feature/dashboard/
├── SupervisorDashboardViewModel.kt  ← object { // TODO }
└── components/
    ├── FilterChips.kt           ← // TODO
    ├── MissingVehicleTable.kt   ← // TODO
    ├── ShiftOverview.kt         ← // TODO
    └── VehicleRow.kt            ← // TODO
```

### หลัง
```
feature/dashboard/
├── SupervisorDashboardViewModel.kt  ← @HiltViewModel จริง (ใช้ Mock Data ชั่วคราว)
└── components/  ← ทุกอันเป็น @Composable จริง
    ├── FilterChips.kt           ← filter department
    ├── MissingVehicleTable.kt   ← list รถที่ยังไม่ตรวจ
    ├── ShiftOverview.kt         ← การ์ดกะปัจจุบัน
    └── VehicleRow.kt            ← แถวรถแต่ละคัน
```

### ทำไมต้องทำ?
- Supervisor ต้องดูภาพรวมว่ารถคันไหนยังไม่ถูกตรวจในกะนี้
- FilterChips ช่วยกรองตาม department (มี 6 แผนก)
- ตอนนี้ใช้ Mock Data (hardcode 5 คันแรกว่าตรวจแล้ว) → อนาคตเปลี่ยนเป็น Firestore จริง

---

## 🏠 6. MainActivity (1 ไฟล์)

### ก่อน
```kotlin
// เริ่มที่ Menu เลย ไม่มี Login
var screen = "menu"  

// ChecklistScreen เป็นฟังก์ชัน local ใน Activity (hardcode)
ChecklistScreen(v, onBack, onSubmit)  

// StatusBadge ฟังก์ชัน local ซ้ำกับ ui/components/
fun StatusBadge(status: String) { ... }
```

### หลัง
```kotlin
// เริ่มที่ Login ก่อน
var screen = "login"

// ใช้ ChecklistScreen จาก feature/checklist/
NewChecklistScreen()

// ลบ StatusBadge local → ใช้จาก ui/components/StatusBadge แทน
```

### ทำไมต้องทำ?
- แยกความรับผิดชอบ (Single Responsibility) — Activity ไม่ควรมี UI เยอะขนาดนั้น
- Login ต้องมาก่อน Menu (เพิ่มความปลอดภัย)
- ลบ code ซ้ำซ้อน (local `StatusBadge` ซ้ำกับ `ui/components/`)

---

## 🐛 7. Fix UseCase (1 ไฟล์)

### ไฟล์: `core/domain/usecase/shift/GetCurrentShiftUseCase.kt`

| ก่อน | หลัง |
|------|------|
| `class GetCurrentShiftUseCase {` | `class GetCurrentShiftUseCase @Inject constructor() {` |

### ทำไมต้องทำ?
- `SupervisorDashboardViewModel` inject `GetCurrentShiftUseCase`
- Hilt ต้องการ `@Inject constructor()` หรือ `@Provides` เสมอ
- ถ้าไม่มี → build error: `GetCurrentShiftUseCase cannot be provided without an @Inject constructor or an @Provides-annotated method`

---

## 🧹 สรุปจำนวนไฟล์ที่เปลี่ยนแปลง

| หมวด | จำนวนไฟล์ | ลักษณะการเปลี่ยนแปลง |
|:----:|:--------:|--------------------|
| DI Module | 1 | `// TODO` → `@Module` จริง |
| Auth | 3 | `// TODO` → ViewModel + Screen + Component |
| UI Components | 9 | `// TODO` → Composable จริง ทั้ง 9 ตัว |
| Checklist | 11 | `// TODO` → ViewModel + Screen + 8 Components |
| Dashboard | 5 | `// TODO` → ViewModel + 4 Components |
| MainActivity | 1 | refactor navigation + ลบของซ้ำ |
| UseCase Fix | 1 | เพิ่ม `@Inject constructor()` |
| **รวม** | **31 ไฟล์** | |

### + Documentation 4 ไฟล์
| # | ไฟล์ | เนื้อหา |
|:-:|------|--------|
| 1 | `Docs/00-Setup-Environment-Windows.md` | การติดตั้งบน Windows |
| 2 | `Docs/01-Commands-for-Windows.md` | คำสั่ง Build สำหรับ Windows |
| 3 | `Docs/02-For-Mac-Continue.md` | งานต่อที่ Mac |
| 4 | **`Docs/04-Refactor-Summary.md`** | **ไฟล์นี้ — สรุปภาพรวม Refactor ทั้งหมด** |

---

> 📝 **สรุปโดย:** likit_s  
> 📅 **วันที่:** 30 กรกฎาคม 2026  
> ⚡ **สถานะ:** `./gradlew assembleDebug` → ✅ **BUILD SUCCESSFUL**
