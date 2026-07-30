// 📁 di/AppModule.kt
package com.irpc.forklift.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 🔌 Hilt App Module
 *
 * @Module
 * @InstallIn(SingletonComponent::class)
 * object AppModule {
 *
 *     @Provides @Singleton
 *     fun provideDepartmentRepository(): DepartmentRepository =
 *         DepartmentRepositoryImpl()
 *
 *     @Provides @Singleton
 *     fun provideChecksheetRepository(): ChecksheetRepository =
 *         ChecksheetRepositoryImpl()
 *
 *     @Provides
 *     fun provideGetAccessibleDeptsUseCase(repo: DepartmentRepository): GetAccessibleDepartmentsUseCase =
 *         GetAccessibleDepartmentsUseCase(repo)
 * }
 */
object AppModule {
    // TODO: implement Hilt modules
}
