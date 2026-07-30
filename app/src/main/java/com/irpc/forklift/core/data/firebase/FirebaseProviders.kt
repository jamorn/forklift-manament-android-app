// 📁 core/data/firebase/FirebaseProviders.kt
package com.irpc.forklift.core.data.firebase

/**
 * 🔥 Firebase Providers
 *
 * สร้าง instance ของ Firebase Auth + Firestore
 * ใช้ Hilt @Module ในการ inject
 */
object FirebaseProviders {
    // สำหรับ Android: Firebase.auth, Firebase.firestore
    // ผ่าน dependency firebase-bom + firebase-auth-ktx + firebase-firestore-ktx

    // val auth: FirebaseAuth = Firebase.auth
    // val firestore: FirebaseFirestore = Firebase.firestore
}
