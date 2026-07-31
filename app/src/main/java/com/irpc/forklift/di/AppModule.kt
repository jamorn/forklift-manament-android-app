// 📁 di/AppModule.kt
package com.irpc.forklift.di

import android.content.Context
import android.content.SharedPreferences
import com.irpc.forklift.core.data.repository.AuthRepositoryImpl
import com.irpc.forklift.core.data.repository.ChecksheetRepositoryImpl
import com.irpc.forklift.core.data.repository.DepartmentRepositoryImpl
import com.irpc.forklift.core.data.repository.VehicleRepositoryImpl
import com.irpc.forklift.core.domain.repository.AuthRepository
import com.irpc.forklift.core.domain.repository.ChecksheetRepository
import com.irpc.forklift.core.domain.repository.DepartmentRepository
import com.irpc.forklift.core.domain.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 🔌 Hilt App Module — Main DI Bindings
 *
 * Provides all repository implementations as singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /** SharedPreferences — ใช้เก็บข้อมูล local เช่น checkedVehicles แยกวัน */
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences("forklift_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Provides
    @Singleton
    fun provideDepartmentRepository(): DepartmentRepository = DepartmentRepositoryImpl()

    @Provides
    @Singleton
    fun provideVehicleRepository(): VehicleRepository = VehicleRepositoryImpl()

    @Provides
    @Singleton
    fun provideChecksheetRepository(): ChecksheetRepository = ChecksheetRepositoryImpl()
}
