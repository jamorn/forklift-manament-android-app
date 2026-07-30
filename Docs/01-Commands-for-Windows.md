# ⚡ คำสั่ง Build สำหรับ Windows (Git Bash)

> รวบรวมคำสั่งที่ใช้สำหรับ Build Android App บนเครื่อง PC  
> **JDK 17:** `C:\Users\likit_s\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8`  
> **โปรเจกต์:** `E:\Project2026\forklift-manament-android-app`  
> **Gradle Wrapper:** `8.9`

---

## ✅ วิธี Build

เปิด PowerShell หรือ Git Bash วางคำสั่งใดคำสั่งหนึ่งด้านล่าง:

### วิธีที่ 1: คำสั่งเต็ม (PowerShell)

```powershell
$env:JAVA_HOME = "C:\Users\likit_s\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8"; $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"; Set-Location "E:\Project2026\forklift-manament-android-app"; & "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && export JAVA_HOME='/c/Users/likit_s/AppData/Local/Programs/Eclipse Adoptium/jdk-17.0.20+8' && export PATH=\$JAVA_HOME/bin:/c/Users/likit_s/AppData/Local/Programs/Git/usr/bin:\$PATH && ./gradlew assembleDebug 2>&1"
```

### วิธีที่ 2: ใช้ PowerShell Function (แนะนำ ✅)

สร้าง alias ไว้ใน PowerShell Profile เพื่อให้พิมพ์แค่ `buildapp` ก็ Build ได้เลย

**ขั้นตอน:**

1. เปิด PowerShell แล้วพิมพ์คำสั่งนี้:
   ```powershell
   notepad $PROFILE
   ```
   > ถ้าถามสร้างไฟล์ใหม่ให้กด Yes

2. วางฟังก์ชันนี้ลงในไฟล์ แล้วบันทึก:
   ```powershell
   function Build-Forklift {
       $env:JAVA_HOME = "C:\Users\likit_s\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8"
       $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
       Set-Location "E:\Project2026\forklift-manament-android-app"
       & "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && export JAVA_HOME='/c/Users/likit_s/AppData/Local/Programs/Eclipse Adoptium/jdk-17.0.20+8' && export PATH=\$JAVA_HOME/bin:/c/Users/likit_s/AppData/Local/Programs/Git/usr/bin:\$PATH && ./gradlew assembleDebug 2>&1"
   }
   Set-Alias buildapp Build-Forklift
   ```

3. ปิด PowerShell แล้วเปิดใหม่
4. ต่อไปแค่พิมพ์:
   ```powershell
   buildapp
   ```

### วิธีที่ 3: ตั้ง Environment Variables ถาวร (แนะนำที่สุด ✅✅)

ตั้ง `JAVA_HOME` และ `ANDROID_HOME` ที่ Windows OS เลย เพื่อให้ทุก Terminal ใช้ค่าเดียวกันโดยไม่ต้องเซ็ตทุกครั้ง

1. กดปุ่ม **Windows** พิมพ์ `environment variables` แล้วเลือก **Edit the system environment variables**
2. คลิก **Environment Variables...**
3. ในช่อง **System variables** คลิก **New...** แล้วเพิ่ม:
   - **Variable name:** `JAVA_HOME`
   - **Variable value:** `C:\Users\likit_s\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20+8`
4. ในช่อง **System variables** เลือก `Path` → คลิก Edit → **New** แล้วเพิ่ม:
   - `%JAVA_HOME%\bin`
   - `C:\Android\Sdk\platform-tools`
   - `C:\Users\likit_s\AppData\Local\Programs\Git\bin`
   - `C:\Users\likit_s\AppData\Local\Programs\Git\usr\bin`
5. คลิก OK ทุกอัน ปิดแล้วเปิด PowerShell ใหม่

หลังจากนั้นแค่:

```powershell
cd E:\Project2026\forklift-manament-android-app
& "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew assembleDebug"
```

---

## 🔥 คำสั่งสั้นๆ ที่ใช้บ่อย

| สิ่งที่ต้องการ | คำสั่ง PowerShell |
|-------------|-----------------|
| Build APK | `buildapp` (หลังตั้ง Profile) |
| Build APK (ไม่ใช้ alias) | `& "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew assembleDebug"` |
| Clean + Build | `& "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew clean assembleDebug"` |
| Compile เฉพาะ Kotlin (เร็ว) | `& "C:\Users\likit_s\AppData\Local\Programs\Git\bin\sh.exe" --login -c "cd /e/Project2026/forklift-manament-android-app && ./gradlew :app:compileDebugKotlin"` |
| ดูไฟล์ APK | `Get-ChildItem E:\Project2026\forklift-manament-android-app\app\build\outputs\apk\debug\` |

---

## 📁 ตำแหน่งไฟล์ที่ได้

| ไฟล์ | Path |
|-----|------|
| **APK (Debug)** | `E:\Project2026\forklift-manament-android-app\app\build\outputs\apk\debug\app-debug.apk` |
| **รายงาน Build** | `E:\Project2026\forklift-manament-android-app\app\build\reports\` |

---

## ⚠️ ข้อควรจำ

- **ห้ามรัน `gradlew` ตรงๆ ใน PowerShell** เพราะเป็น Unix shell script Windows ไม่รู้จัก
- **ต้องรันผ่าน Git Bash (`sh.exe`) เสมอ**
- ถ้าเจอ error `xargs is not available` → ต้องเพิ่ม `C:\Users\{user}\AppData\Local\Programs\Git\usr\bin` ใน Path
- Long Paths ต้องเปิดแล้ว (เช็คด้วย `Get-ItemProperty ... LongPathsEnabled` ต้องมีค่า = 1)

---

> 📝 อัปเดตล่าสุด: 30 กรกฎาคม 2026
