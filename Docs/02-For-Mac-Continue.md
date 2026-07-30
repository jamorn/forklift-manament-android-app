# 🍏 ทำงานต่อบน Mac (CLI ล้วนๆ ไม่ต้อง Android Studio)

> **สมมติฐาน:** Mac มี JDK 17 อยู่แล้ว + Android SDK (command line)  
> เพราะกำลังทำ Kotlin โปรเจกต์อื่นอยู่  
>  
> ⚡ **งานต่อจาก PC:** Logic Checklist + Dashboard components ยังต้อง implement จริง  
> บน PC ทำแค่โครงสร้าง UI และ DI ไว้แล้ว

---

## 📥 1. ดึง Code จาก Git

```bash
cd ~/Projects
git clone <repo-url>
cd forklift-manament-android-app
```

หรือ ถ้าอยู่ใน Repo เดียวกัน:
```bash
git pull origin main
```

---

## 🚀 2. Build APK

```bash
# Build เลย
./gradlew assembleDebug
```

> ✅ Mac ใช้ Bash/Zsh ได้ตรงๆ ไม่ต้อง Git Bash

### ถ้ายังไม่มี Android SDK บน Mac

```bash
# 1. ดาวน์โหลด command line tools
brew install --cask android-sdk
# หรือ ใช้ sdkmanager ตรงๆ
mkdir -p ~/Library/Android/sdk
cd ~/Library/Android/sdk
curl -LO https://dl.google.com/android/repository/commandlinetools-mac-11076708_latest.zip
unzip commandlinetools-mac-*.zip
mv cmdline-tools latest
mkdir cmdline-tools
mv latest cmdline-tools/

# 2. ตั้ง Environment Variables
echo 'export ANDROID_HOME=$HOME/Library/Android/sdk' >> ~/.zshrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin' >> ~/.zshrc
source ~/.zshrc

# 3. ติดตั้ง SDK Platform
sdkmanager "platforms;android-35" "build-tools;35.0.0"
```

---

## ✅ 3. ตรวจสอบว่าพร้อม

```bash
# เช็ค JDK
java -version
# ต้องเป็น openjdk version "17.x.x"

# เช็ค Android SDK
echo $ANDROID_HOME
ls $ANDROID_HOME/platforms  # ต้องมี android-35
ls $ANDROID_HOME/build-tools  # ต้องมี 35.0.0
```

---

## 🛠️ 4. สิ่งที่ต้องทำต่อ (Logic จริง)

เราทำโครงสร้าง UI + DI ครบแล้ว ต้อง implement Logic จริง:

### Priority 🔴 — ต้องทำ

| ไฟล์ | หน้าที่ | สถานะ |
|------|--------|:-----:|
| `ChecklistForm.kt` | ต่อ `checkResults` state เข้ากับ CategorySection | ✅ UI พร้อม, ⏳ ต้องเชื่อม state |
| `SupervisorDashboardViewModel.kt` | เปลี่ยนจาก Mock → โหลด checksheet จริง | ✅ DI พร้อม, ⚠️ ใช้ MOCK Data |
| `ChecksheetRepositoryImpl.kt` | Implement Firestore CRUD | ⏳ ยังเป็น TODO |
| `AuthRepositoryImpl.kt` | Implement Firebase Auth | ⏳ ยังเป็น TODO |

### Priority 🔵 — ถัดมา

| ไฟล์ | หน้าที่ |
|------|--------|
| `VehicleRepositoryImpl.kt` | โหลด vehicle list จาก Firestore |
| `DepartmentRepositoryImpl.kt` | โหลด department list จาก Firestore |
| `GetAccessibleDepartmentsUseCase.kt` | scope-based filtering |
| `LoginViewModel.kt` | เปลี่ยน mock → Firebase Auth จริง |

---

## 📁 โครงสร้างโปรเจกต์ (Refactor แล้ว)

```
forklift-manament-android-app/
├── app/src/main/java/com/irpc/forklift/
│   ├── core/
│   │   ├── data/
│   │   │   ├── mock/          ← MockData (checklistItems, vehicles)
│   │   │   └── repository/    ← TODO: implement จริง
│   │   ├── domain/
│   │   │   ├── model/         ← Vehicle, DailyChecksheet, ShiftCode, ...
│   │   │   ├── repository/    ← Interfaces
│   │   │   └── usecase/       ← Logic (GetCurrentShiftUseCase ✅)
│   │   └── common/
│   ├── di/
│   │   └── AppModule.kt       ← @Module พร้อมแล้ว
│   ├── feature/
│   │   ├── auth/              ← LoginScreen ✅ (ใช้ mock)
│   │   ├── checklist/         ← ChecklistScreen ✅ (UI ครบ)
│   │   │   └── components/    ← 11 components ✅
│   │   ├── dashboard/         ← SupervisorDashboard ✅ (UI ครบ)
│   │   │   └── components/    ← 5 components ✅
│   │   ├── maintenance/       ← TODO
│   │   └── report/            ← TODO
│   └── ui/components/         ← 9 reusable components ✅
├── Docs/                       ← เอกสารทั้งหมด
└── build.gradle.kts
```

---

## ⚡ คำสั่งสั้นๆ ที่ต้องใช้บ่อย

```bash
# Build APK
./gradlew assembleDebug

# Compile เฉพาะ Kotlin (เร็วกว่า)
./gradlew :app:compileDebugKotlin

# Clean + Build
./gradlew clean assembleDebug

# ดู APK
ls -lh app/build/outputs/apk/debug/app-debug.apk

# Install ลงเครื่องจริง (ถ้าเสียบ USB)
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📌 เทียบ Windows vs Mac

| รายการ | PC (Windows) | Mac (บ้าน) |
|--------|-------------|-----------|
| **JDK** | JDK 17 Temurin ✅ | JDK 17 ✅ |
| **Gradle** | 8.9 ✅ | 8.9 ✅ |
| **Android SDK** | C:\Android\Sdk ✅ | ~/Library/Android/sdk ✅ |
| **รัน gradlew** | ต้อง Git Bash (`sh.exe`) | Terminal ตรงๆ ✅ |
| **Android Studio** | ไม่มี ❌ | ไม่มี ❌ |
| **Long Paths** | เปิดแล้ว ✅ | Mac ไม่มีปัญหา ✅ |

---

> 📝 อัปเดตล่าสุด: 30 กรกฎาคม 2026  
> 👤 สำหรับ: likit_s
