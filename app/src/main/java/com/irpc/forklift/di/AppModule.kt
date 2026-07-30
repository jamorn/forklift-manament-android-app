// 📁 di/AppModule.kt
package com.irpc.forklift.di

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

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDepartmentRepository(): DepartmentRepository {
        return DepartmentRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(): VehicleRepository {
        return VehicleRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChecksheetRepository(): ChecksheetRepository {
        return ChecksheetRepositoryImpl()
    }
}

