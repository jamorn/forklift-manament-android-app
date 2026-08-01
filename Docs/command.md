# 🚀 คำสั่ง Build & Dev สำหรับ Forklift

> รวบรวมคำสั่งที่ใช้ประจำสำหรับโปรเจกต์ forklift-manament-android-app
> แยกตาม OS: 🍏 Mac (ที่บ้าน) / 🪟 Windows (ที่ทำงาน)

---

# 🍏 Mac (ที่บ้าน)

> Terminal (zsh/bash) ใช้ตรงๆ ไม่ต้อง Android Studio

### Build APK (Debug)
```bash
cd ~/forklift-manament-android-app && ./gradlew assembleDebug
```

### Build + ดูผล error เท่านั้น
```bash
cd ~/forklift-manament-android-app && ./gradlew assembleDebug 2>&1 | grep -E "BUILD|e:|error"
```

### Compile เฉพาะ Kotlin (เร็ว — เช็ค syntax บ่อยๆ)
```bash
cd ~/forklift-manament-android-app && ./gradlew :app:compileDebugKotlin
```

### Clean + Build ใหม่ (เคลียร์ cache)
```bash
cd ~/forklift-manament-android-app && ./gradlew clean assembleDebug
```

### เช็คอุปกรณ์ + install
```bash
cd ~/forklift-manament-android-app && adb devices && ./gradlew installDebug 2>&1 | tail -8
```

### Ktlint (แก้รูปแบบ code อัตโนมัติ)
```bash
cd ~/forklift-manament-android-app && ./gradlew :app:ktlintFormat
```

### Git — pull / commit / push
```bash
cd ~/forklift-manament-android-app && git pull origin master
cd ~/forklift-manament-android-app && git add -A && git commit -m "feat: ..." && git push origin master
```

---

# 🪟 Windows (ที่ทำงาน)

> ⚠️ **สำคัญ:** `gradlew` เป็น Unix shell script — Windows รันตรงๆ ไม่ได้
> ต้องรันผ่าน **Git Bash** (`sh.exe`) เสมอ

## 🔑 สภาพแวดล้อม (ตั้งไว้แล้วในเครื่อง)

```
JAVA_HOME     = C:\Users\<USER>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8
ANDROID_HOME  = C:\Android\Sdk
```
> ต้องเปิด **Long Paths** (`LongPathsEnabled=1`) + Restart เครื่องแล้ว

## ⚡ วิธีรัน (PowerShell)

### รันผ่าน Git Bash ครั้งเดียว (หายากหน่อยแต่ใช้ได้เสมอ)
```powershell
& "C:\Users\<USER>\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && export JAVA_HOME='/c/Users/<USER>/AppData/Local/Programs/Eclipse Adoptium/jdk-17.0.20+8' && export PATH=\$JAVA_HOME/bin:/c/Users/<USER>/AppData/Local/Programs/Git/usr/bin:\$PATH && ./gradlew assembleDebug 2>&1"
```

### วิธีแนะนำ: สร้าง alias `buildapp` (ทำครั้งเดียว)
เปิด PowerShell แล้วใส่ใน Profile (`notepad $PROFILE`):
```powershell
function Build-Forklift {
    $env:JAVA_HOME = "C:\Users\<USER>\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8"
    $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
    Set-Location "E:\Project2026\forklift-manament-android-app"
    & "C:\Users\<USER>\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && export JAVA_HOME='/c/Users/<USER>/AppData/Local/Programs/Eclipse Adoptium/jdk-17.0.20+8' && export PATH=\$JAVA_HOME/bin:/c/Users/<USER>/AppData/Local/Programs/Git/usr/bin:\$PATH && ./gradlew assembleDebug 2>&1"
}
Set-Alias buildapp Build-Forklift
```
> ต่อไปแค่พิมพ์ `buildapp`

## 🧰 คำสั่งสั้นๆ ที่ใช้บ่อย (ผ่าน alias/ใน Git Bash)

| สิ่งที่ต้องการ | คำสั่ง PowerShell |
|-------------|-----------------|
| Build APK | `buildapp` (หลังตั้ง alias) |
| Build APK (ไม่ใช้ alias) | `& "...\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew assembleDebug"` |
| Clean + Build | `& "...\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew clean assembleDebug"` |
| Compile Kotlin (เร็ว) | `& "...\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew :app:compileDebugKotlin"` |
| Install ลงมือถือ | `cd E:\Project2026\forklift-manament-android-app` จากนั้น `adb install -r app\build\outputs\apk\debug\app-debug.apk` |
| Ktlint format | `& "...\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew :app:ktlintFormat"` |
| ดูไฟล์ APK | `Get-ChildItem E:\Project2026\forklift-manament-android-app\app\build\outputs\apk\debug\` |

> 🔁 แทน `<USER>` ด้วยชื่อ user ของคุณ ตัวอย่าง: `likit_s`

## 📁 ตำแหน่ง APK (Windows)

```
E:\Project2026\forklift-manament-android-app\app\build\outputs\apk\debug\app-debug.apk
```

---

# 📌 เทียบเร็ว Mac vs Windows

| รายการ | 🍏 Mac (ที่บ้าน) | 🪟 Windows (ที่ทำงาน) |
|--------|----------------|----------------------|
| **รัน gradlew** | Terminal ตรงๆ ✅ | ต้องผ่าน Git Bash (`sh.exe`) |
| **Build** | `./gradlew assembleDebug` | `buildapp` (alias) |
| **JAVA_HOME** | อัตโนมัติ | ต้องตั้ง/ส่งผ่าน |
| **Long Paths** | ไม่มีปัญหา | ต้องเปิดแล้ว |
| **APK path** | `~/forklift.../app/build/outputs/apk/debug/` | `E:\Project2026\...` |

---

> 📝 อัปเดตล่าสุด: 30 กรกฎาคม 2026 | 👤 สำหรับ: likit_s
