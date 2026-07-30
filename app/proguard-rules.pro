# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the Android SDK tools proguard config.

# Keep Firebase model classes
-keep class com.irpc.forklift.core.domain.model.** { *; }
-keep class com.irpc.forklift.core.data.local.entity.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep Compose
-dontwarn androidx.compose.**

# Keep Gson/JSON
-keepattributes Signature
-keepattributes *Annotation*
