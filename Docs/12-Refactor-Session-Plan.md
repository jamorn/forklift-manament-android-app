# 🔧 Refactor Session Plan — 30 July 2026

> **เป้าหมายวันนี้:** ทำให้ App ทำงานแบบ **มีโครงสร้าง Clean Architecture จริง** ไม่ใช่แค่ Mock UI  
> **หลักการ:** Refactor ทีละจุด ไม่ให้ App พังกลางทาง

---

## 📍 สถานะก่อนเริ่ม

### สิ่งที่มีอยู่แล้ว
| ของที่มี | ใช้ได้? |
|---------|--------|
| Gradle dependencies (Hilt, Firebase, Room, Nav, DataStore) | ✅ พร้อม |
| Domain Models (6 ตัว) | ✅ เสร็จ |
| Repository Interfaces (4 ตัว) | ✅ เสร็จ |
| UseCases (4 ตัว) | ✅ เสร็จ |
| Mock Data (15 คัน + 29 checklist items) | ✅ พร้อม |
| Theme (Color, Shape, Type) | ✅ เสร็จ |
| UI Screens เก่าใน MainActivity (Menu, VehicleList, Checklist) | ✅ ทำงานได้ |
| SupervisorDashboardScreen (hardcoded) | ✅ ทำงานได้ |
| MaintenanceScreen (hardcoded) | ✅ ทำงานได้ |

### ปัญหาที่ต้องแก้
| # | ปัญหา | กระทบ |
|---|-------|--------|
| 1 | DI Module ยังเป็น placeholder | ViewModel ใช้ Hilt ไม่ได้ ❌ |
| 2 | ViewModel ทั้ง 5 ตัวเป็น object placeholder | ไม่มี business logic ❌ |
| 3 | UI Components 8 ตัวเป็น object placeholder | reuse ไม่ได้ ❌ |
| 4 | Screens ใหม่ (LoginScreen, ฯลฯ) เป็น object placeholder | ใช้ Navigation Compose ไม่ได้ ❌ |
| 5 | Navigation เป็น string-based ใน MainActivity | ขยายลำบาก ⚠️ |
| 6 | SupervisorDashboardScreen data hardcoded | ไม่ dynamic ⚠️ |
| 7 | Dashboard Components (FilterChips, ฯลฯ) เป็น placeholder | ใช้ DashboardVM ไม่ได้ ❌ |
| 8 | Checklist components (CategorySection, ฯลฯ) เป็น placeholder | ใช้ ChecklistVM ไม่ได้ ❌ |

---

## 🎯 แผนวันนี้ — 4 Steps

```
Step 0: 📋 Setup — ตรวจสอบว่า build ผ่าน
Step 1: 🔧 DI + Login (ทำให้ Login ใช้ Hilt + ViewModel ได้)
Step 2: 🔧 UI Components (เปิดของที่มีอยู่แล้ว)
Step 3: 🔧 Checklist (ViewModel + Components)
Step 4: 🔧 Dashboard (ViewModel + Components)
```

---

## Step 0: 📋 Setup (5 นาที)

### สิ่งที่ต้อง确认ก่อนเริ่ม
- [ ] `./gradlew build` ผ่านหรือไม่
- [ ] ไฟล์ที่ต้องแก้มีครบ (list ด้านล่าง)
- [ ] git branch พร้อม (สร้าง branch ใหม่: `refactor/init-mvp`)

---

## Step 1: 🔧 DI + Login (30 นาที)

### ไฟล์ที่ต้องแก้

```
📄 di/AppModule.kt
    └── จาก object placeholder → @Module จริง มี @Provides

📄 feature/auth/LoginViewModel.kt
    └── จาก object placeholder → @HiltViewModel จริง
    
📄 feature/auth/LoginScreen.kt
    └── จาก object placeholder → @Composable function จริง
    
📄 feature/auth/components/MockUserSelector.kt
    └── จาก object placeholder → @Composable function จริง
```

### สิ่งที่จะได้
```
✅ Hilt ใช้งานได้
✅ Login ผ่าน ViewModel (mock login)
✅ LoginUiState อัปเดตตามจริง
✅ เลือก user จาก MockUserSelector
```

### โค้ด概要

```kotlin
// AppModule.kt
@Module @InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
    
    @Provides @Singleton
    fun provideDepartmentRepository(): DepartmentRepository = DepartmentRepositoryImpl()
    
    @Provides @Singleton
    fun provideVehicleRepository(): VehicleRepository = VehicleRepositoryImpl()
    
    @Provides @Singleton
    fun provideChecksheetRepository(): ChecksheetRepository = ChecksheetRepositoryImpl()
}

// LoginViewModel.kt
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(
        mockUsers = AuthRepositoryImpl.MOCK_USERS.values.toList()
    ))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun mockLogin(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authRepository.mockLogin(email).onSuccess { profile ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false, isLoggedIn = true, profile = profile
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
```

---

## Step 2: 🔧 UI Components (20 นาที)

### ไฟล์ที่ต้องแก้ (8 ไฟล์)

```
📄 ui/components/AppBadge.kt
📄 ui/components/AppButton.kt
📄 ui/components/AppCard.kt
📄 ui/components/AppInput.kt
📄 ui/components/AppModal.kt
📄 ui/components/LoadingSpinner.kt
📄 ui/components/SkeletonLoader.kt
📄 ui/components/ToastHost.kt
```

### สิ่งที่จะได้
```
✅ AppBadge — reusable status badge
✅ AppButton — primary/danger/outline
✅ AppCard — wrapper card
✅ AppInput — text field with error
✅ AppModal — dialog
✅ LoadingSpinner — full screen + small
✅ SkeletonLoader — shimmer effect
✅ ToastHost — snackbar
```

### วิธีการ
```kotlin
// จาก: object AppBadge { // TODO: implement }
// เป็น:

@Composable
fun AppBadge(text: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
```

---

## Step 3: 🔧 Checklist (40 นาที)

### ไฟล์ที่ต้องแก้

```
📄 feature/checklist/ChecklistViewModel.kt
    └── object → @HiltViewModel จริง
    
📄 feature/checklist/ChecklistEvent.kt
    └── object → sealed class จริง
    
📄 feature/checklist/components/CategorySection/CategorySection.kt
📄 feature/checklist/components/CheckItemRow/CheckItemRow.kt
📄 feature/checklist/components/ChecklistForm.kt
📄 feature/checklist/components/CopyForwardBanner.kt
📄 feature/checklist/components/ManhourMeterInput/ManhourMeterInput.kt
📄 feature/checklist/components/SuccessScreen/SuccessScreen.kt
📄 feature/checklist/components/OfflineIndicator.kt
📄 feature/checklist/components/VehicleSelector.kt
    └── ทั้งหมด: object → @Composable function จริง
```

### สิ่งที่จะได้
```
✅ Checklist ทำงานผ่าน ViewModel
✅ Copy-Forward (ดึง checksheet ก่อนหน้า)
✅ Submit checksheet (save ผ่าน repository)
✅ แต่ละ component reuse ได้
```

---

## Step 4: 🔧 Dashboard (30 นาที)

### ไฟล์ที่ต้องแก้

```
📄 feature/dashboard/SupervisorDashboardViewModel.kt
    └── object → @HiltViewModel จริง
    
📄 feature/dashboard/components/FilterChips.kt
📄 feature/dashboard/components/MissingVehicleTable.kt
📄 feature/dashboard/components/ShiftOverview.kt
📄 feature/dashboard/components/VehicleRow.kt
    └── ทั้งหมด: object → @Composable function จริง
```

### สิ่งที่จะได้
```
✅ Dashboard ทำงานผ่าน ViewModel
✅ คำนวณ missing vehicles จริง (จาก checksheet ปัจจุบัน)
✅ Filter ตาม department จริง
✅ Shift overview แสดงกะปัจจุบัน
```

---

## 📋 Summary

| Step | ไฟล์ที่แก้ | เวลา | ผลลัพธ์ |
|------|-----------|------|---------|
| 0 | — | 5 นาที | Build ผ่าน |
| 1 | 4 ไฟล์ | 30 นาที | DI + Login ใช้ได้ |
| 2 | 8 ไฟล์ | 20 นาที | UI Components พร้อม |
| 3 | 10 ไฟล์ | 40 นาที | Checklist ทำงาน |
| 4 | 5 ไฟล์ | 30 นาที | Dashboard ทำงาน |
| **รวม** | **~27 ไฟล์** | **~2 ชม.** | **MVP ใช้ได้** |

### สิ่งที่จะ **ไม่ทำ** วันนี้ (ไว้รอบหน้า)
- ❌ Navigation Compose (ยังใช้ string-based ใน MainActivity)
- ❌ Firebase จริง (ยังใช้ Mock)
- ❌ Room Database
- ❌ Reports Screen
- ❌ Maintenance Screen (UI มีแล้ว ทำงานได้)
- ❌ Unit Tests

---

## 🚨 กฎการ Refactor

```
1. แก้ทีละไฟล์ → build ผ่าน → commit → ไฟล์ถัดไป
2. ถ้าไม่แน่ใจ → เปิด Issue/Discussion
3. ไม่ลบของเก่าจนกว่าของใหม่ใช้ได้
4. commit message: "refactor: สิ่งที่ทำ"
```
