# 🪟 การตั้งค่าสภาพแวดล้อมสำหรับ Build Android App บน Windows (แบบไม่ต้องใช้ Android Studio)

> **สรุปการติดตั้งสำหรับ PC:**
> เครื่องนี้ใช้ `JDK 17` + `Gradle 8.9 (wrapper)` + `Android SDK 35`  
> ⚡ Build ผ่าน Git Bash (CLI ล้วนๆ ไม่ต้องใช้ Android Studio)

---

## 📋 สิ่งที่ต้องติดตั้ง

| ลำดับ | เครื่องมือ | เวอร์ชัน | ติดตั้งแล้ว? | หมายเหตุ |
|:---:|-----------|---------|:----------:|----------|
| 1 | **JDK 17 (Temurin)** | `17.0.20+8` | ✅ | Adoptium — ลงที่ `C:\Users\<user>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8` |
| 2 | **Git for Windows** | (ล่าสุด) | ✅ | ใช้ `Git Bash` ในการรัน `gradlew` (shell script) |
| 3 | **Android SDK** | `android-35` + `build-tools 35.0.0` | ✅ | อยู่ที่ `C:\Android\Sdk` |
| 4 | **Gradle Wrapper** | `8.9` | ✅ | มากับโปรเจกต์ — ใช้ `gradlew` รัน |
| 5 | ~~Android Studio~~ | — | ❌ | **ไม่จำเป็น!** Build ผ่าน CLI ได้เลย |

---

## ⚙️ Windows Environment Variables ที่ต้องตั้ง

### 🔑 ตัวแปรสภาพแวดล้อม (System Variables)

```
JAVA_HOME = C:\Users\<USER>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8
ANDROID_HOME = C:\Android\Sdk
ANDROID_SDK_ROOT = C:\Android\Sdk
```

### 📌 Path ที่ต้องเพิ่ม (System PATH)

| Path | เหตุผล |
|------|--------|
| `%JAVA_HOME%\bin` | ให้ `java`, `javac` ใช้งานได้ |
| `%ANDROID_HOME%\platform-tools` | ให้ `adb` ใช้งานได้ |
| `%ANDROID_HOME%\cmdline-tools\latest\bin` | ให้ `sdkmanager` ใช้งานได้ |
| `%USERPROFILE%\AppData\Local\Programs\Git\bin` | ให้ `sh.exe`, `bash.exe` ใช้รัน gradlew |
| `%USERPROFILE%\AppData\Local\Programs\Git\usr\bin` | ให้ `xargs`, `find`, `uname` ฯลฯ (จำเป็นสำหรับ gradlew script) |

### ✅ วิธีตรวจสอบ

เปิด PowerShell แล้วรัน:

```powershell
# ตรวจ JAVA_HOME
echo $env:JAVA_HOME
# ควรได้: C:\Users\<USER>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8

# ตรวจ ANDROID_HOME
echo $env:ANDROID_HOME
# ควรได้: C:\Android\Sdk

# ตรวจ Java version
java -version
# ควรขึ้น: openjdk version "17.0.20" ...

# ตรวจ Android SDK
Get-ChildItem $env:ANDROID_HOME\platforms
# ควรมี android-35

Get-ChildItem $env:ANDROID_HOME\build-tools
# ควรมี 35.0.0
```

---

## 🪟 Windows Features ที่ต้องเปิด

### 1. Long Paths (Win32 Long Paths)

> **จำเป็น!** เพราะ Android SDK + Gradle สร้าง path ที่ยาวมาก (200+ ตัวอักษร)
> Windows ปกติจำกัดที่ 260 ตัวอักษร ถ้าไม่เปิดจะ build ไม่ผ่าน

**เช็คว่าเปิดอยู่หรือยัง:**
```powershell
Get-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled"
# ถ้าได้ LongPathsEnabled = 1 → เปิดแล้ว
# ถ้าได้ LongPathsEnabled = 0 → ยังปิด ต้องเปิด
```

**วิธีเปิด (รัน PowerShell ในฐานะ Administrator):**
```powershell
Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1
```

**จากนั้น Restart เครื่อง 1 ครั้ง**

---

## 🚀 วิธี Build โปรเจกต์

### 1. เปิด PowerShell หรือ Git Bash

```powershell
# ถ้าใช้ PowerShell — ต้องตั้ง JAVA_HOME ก่อนทุกครั้ง (ถ้ายังไม่ได้ set เป็น System Variable)
$env:JAVA_HOME = "C:\Users\<USER>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

### 2. รัน build ผ่าน Git Bash

```powershell
# เข้าไปที่โปรเจกต์
cd E:\Project2026\forklift-manament-android-app

# Build ผ่าน Git Bash
& "C:\Users\<USER>\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && export JAVA_HOME='/c/Users/<USER>/AppData/Local/Programs/Eclipse Adoptium/jdk-17.0.20+8' && export PATH=\$JAVA_HOME/bin:/c/Users/<USER>/AppData/Local/Programs/Git/usr/bin:\$PATH && ./gradlew assembleDebug 2>&1"
```

> ⚠️ **สำคัญ:** `gradlew` เป็น Unix shell script (ไม่มี .bat)  
> Windows รันตรงๆ ไม่ได้ ต้องใช้ Git Bash, WSL, หรือ `sh` เท่านั้น!

### 3. ไฟล์ APK ที่ได้

```
E:\Project2026\forklift-manament-android-app\app\build\outputs\apk\debug\app-debug.apk
```

---

## 🛠️ วิธีติดตั้งถ้าต้อง Setup ใหม่ตั้งแต่ต้น

### 1. ติดตั้ง JDK 17 (Temurin)
```powershell
# ดาวน์โหลดจาก Adoptium API
curl.exe -L -o jdk17.zip "https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jdk/hotspot/normal/eclipse"

# แตกไฟล์
Expand-Archive -Path jdk17.zip -DestinationPath "$env:LOCALAPPDATA\Programs\Eclipse Adoptium\" -Force

# ตั้งค่า JAVA_HOME
[Environment]::SetEnvironmentVariable("JAVA_HOME", "$env:LOCALAPPDATA\Programs\Eclipse Adoptium\jdk-17.0.20+8", "Machine")
```

### 2. ติดตั้ง Android SDK (Command Line Tools)
```powershell
# โหลด cmdline-tools
curl.exe -L -o sdk.zip "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"
Expand-Archive -Path sdk.zip -DestinationPath "C:\Android\Sdk\cmdline-tools\" -Force
Rename-Item "C:\Android\Sdk\cmdline-tools\cmdline-tools" "latest"

# ตั้ง ANDROID_HOME
[Environment]::SetEnvironmentVariable("ANDROID_HOME", "C:\Android\Sdk", "Machine")

# ติดตั้ง SDK platform + build-tools
& "C:\Android\Sdk\cmdline-tools\latest\bin\sdkmanager.bat" "platforms;android-35" "build-tools;35.0.0"
```

### 3. ติดตั้ง Git for Windows
- ดาวน์โหลดจาก https://git-scm.com/download/win
- ติดตั้งแบบ default (รวม Git Bash)

### 4. เปิด Long Paths (จำเป็น!)
```powershell
# Administrator PowerShell
Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1
# แล้ว Restart เครื่อง
```

---

## 📊 เปรียบเทียบ: Dev Environment (Mac vs Windows)

| รายการ | Mac (ที่บ้าน) | PC Windows (เครื่องนี้) |
|--------------|---------------------|-------------------------------|
| **JDK** | JDK 17 | ✅ JDK 17 (Temurin) |
| **Gradle** | 8.9 (wrapper) | ✅ 8.9 (wrapper) |
| **Android SDK** | android-35 | ✅ android-35 |
| **Build tool** | Terminal + gradlew | ✅ Git Bash + gradlew |
| **Android Studio** | ไม่จำเป็น | ✅ ไม่จำเป็น |
| **Long Paths** | macOS ไม่มีปัญหา | ✅ เปิด LongPathsEnabled แล้ว |
| **WSL** | — | มี WSL ติดตั้งแล้ว (optional) |

---

> 📝 **อัปเดตล่าสุด:** 30 กรกฎาคม 2026  
> 👤 **ตั้งค่าโดย:** likit_s
