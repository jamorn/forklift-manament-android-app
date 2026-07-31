// 📁 core/data/repository/VehicleRepositoryImpl.kt
package com.irpc.forklift.core.data.repository

import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.repository.VehicleRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 🚛 Vehicle Repository Implementation
 *
 * - Firestore สำหรับ getVehicles
 * - Room cache สำหรับ offline
 */
@Singleton
class VehicleRepositoryImpl
    @Inject
    constructor(
        // private val firestore: FirebaseFirestore,
        // private val vehicleDao: VehicleDao,
    ) : VehicleRepository {
        override suspend fun getVehicles(departmentIds: List<String>): Result<List<Vehicle>> =
            try {
                // val snapshot = firestore
                //     .collection("vehicles")
                //     .whereIn("department_id", departmentIds)
                //     .get()
                //     .await()
                // val vehicles = snapshot.documents.map { it.toObject(Vehicle::class.java) }
                // Result.success(vehicles)
                Result.failure(Exception("Not implemented"))
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getVehicleByChassis(chassisNo: String): Result<Vehicle> =
            try {
                // val doc = firestore.collection("vehicles").document(chassisNo).get().await()
                // val vehicle = doc.toObject(Vehicle::class.java)
                //     ?: return Result.failure(Exception("Vehicle not found"))
                // Result.success(vehicle)
                Result.failure(Exception("Not implemented"))
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getMaintenanceVehicles(): Result<List<Vehicle>> =
            try {
                // val snapshot = firestore
                //     .collection("vehicles")
                //     .whereEqualTo("status", "maintenance")
                //     .get()
                //     .await()
                // Result.success(snapshot.documents.map { it.toObject(Vehicle::class.java) })
                Result.failure(Exception("Not implemented"))
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
