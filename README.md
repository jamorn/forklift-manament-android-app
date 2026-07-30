# Forklift Management — Android (Kotlin)
> ต้นแบบ Kotlin สำหรับแปลงจาก React/TypeScript -> Android Jetpack Compose + Firebase

## 🏗️ Architecture

```
MVVM + Clean Architecture
├── core/domain/model      # Data Classes (เทียบ types/index.ts)
├── core/domain/usecase    # Business Logic (เทียบ lib/*-utils.ts)
├── core/data/repository   # Firestore + Room Implementations
├── feature/*              # Screen + ViewModel (เทียบ app/(dashboard)/*)
└── ui/components          # Reusable Compose Components (เทียบ components/ui/*)
```

## 🚀 Quick Start

1. เปิด Android Studio
2. File → Open → เลือก `KotlinAlternative/`
3. รอ Gradle Sync
4. วาง `google-services.json` (Firebase) ลง `app/`
5. Run emulator

## 🔐 Mock Users (Dev Mode)

| อีเมล | บทบาท | scope |
|-------|--------|-------|
| jamorn@irpc.co.th | SA | ทั้งหมด |
| supv-pl@irpc.co.th | Admin | Bagging (ไม่รวม SASB) |
| wiroj@abc-logistics.co.th | Operator | Bagging (ไม่รวม SASB) |

## 📋 Features

- [x] Login (Google + Mock)
- [x] Daily Checklist (Copy-Forward)
- [x] Supervisor Dashboard (Missing Vehicle)
- [x] Maintenance Cost Log
- [x] Reports & Insights
- [ ] User Management (Admin)
- [ ] Offline Queue (Room)

## 📦 Tech Stack

- **UI**: Jetpack Compose + Material 3
- **DI**: Dagger Hilt
- **Auth**: Firebase Auth (Google)
- **DB**: Firebase Firestore + Room (Offline)
- **Prefs**: DataStore
- **Build**: Gradle KTS + Kotlin 1.9
