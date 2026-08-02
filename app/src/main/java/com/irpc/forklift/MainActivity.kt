// MainActivity.kt
@file:OptIn(ExperimentalMaterial3Api::class)

@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.irpc.forklift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.irpc.forklift.core.common.utils.DateUtils
import com.irpc.forklift.core.data.local.CheckedVehicleStore
import kotlinx.coroutines.launch
import com.irpc.forklift.core.data.mock.MockData
import com.irpc.forklift.core.domain.model.ShiftCode
import com.irpc.forklift.core.common.constants.DepartmentConstants
import com.irpc.forklift.core.domain.model.UserProfile
import com.irpc.forklift.core.domain.model.Vehicle
import com.irpc.forklift.core.domain.repository.VehicleRepository
import com.irpc.forklift.core.domain.usecase.shift.GetCurrentShiftUseCase
import androidx.hilt.navigation.compose.hiltViewModel
import com.irpc.forklift.feature.auth.LoginScreen
import com.irpc.forklift.feature.auth.LoginViewModel
import com.irpc.forklift.feature.dashboard.SupervisorDashboardScreen
import com.irpc.forklift.feature.maintenance.MaintenanceScreen
import com.irpc.forklift.feature.menu.MainMenuScreen
import com.irpc.forklift.feature.report.ReportScreen
import com.irpc.forklift.feature.scan.ScannerScreen
import com.irpc.forklift.feature.vehicles.VehicleListScreen
import com.irpc.forklift.ui.theme.ForkliftTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.irpc.forklift.feature.checklist.ChecklistScreen as NewChecklistScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var checkedVehicleStore: CheckedVehicleStore

    @Inject
    lateinit var vehicleRepository: VehicleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForkliftTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppRoot(
                        checkedVehicleStore = checkedVehicleStore,
                        vehicleRepository = vehicleRepository,
                    )
                }
            }
        }
    }
}

// ============ ROOT NAV ============
@Composable
fun AppRoot(
    checkedVehicleStore: CheckedVehicleStore,
    vehicleRepository: VehicleRepository,
) {
    var screen by remember { mutableStateOf("login") }
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    var selectedV by remember { mutableStateOf<Vehicle?>(null) }

    // ตารางเวรวันนี้ + กะตรงกับเวลาปัจจุบัน
    val shiftUseCase = remember { GetCurrentShiftUseCase() }
    val todayShifts = remember { shiftUseCase.getTodayShifts() }
    val currentShift = remember { shiftUseCase.getShiftByTime() }
    val todayStr = DateUtils.getTodayString()

    // ติดตามรถที่ตรวจแล้วในวันนี้ — persist ลง SharedPreferences (ผ่าน CheckedVehicleStore)
    // โหลดจาก store ตอนแรก (data เดิมที่เคยบันทึก) → state ไว้ reactive
    val checkedVehicles =
        remember(todayStr, checkedVehicleStore) {
            mutableStateMapOf<String, String>().apply {
                putAll(checkedVehicleStore.getCheckedVehicles(todayStr))
            }
        }

    val isOperator: Boolean = profile?.roles?.role == "operator"
    val canAccessMenu: Boolean = profile?.roles?.role in listOf("sa", "admin", "super")
    // ViewModel scope ระดับ Activity — อยู่รอดผ่าน screen change
    val loginViewModel: LoginViewModel = hiltViewModel()

    // Coroutine scope ระดับ composable — ใช้สำหรับ async ที่ไม่บล็อก UI (เช่น find vehicle จาก repo)
    val scope = rememberCoroutineScope()

    // ออกจากระบบ — reset กลับหน้า login เต็มตัว (ไม่มี state ค้าง)
    val logout: () -> Unit = {
        loginViewModel.resetState()
        profile = null
        selectedV = null
        checkedVehicles.clear()
        screen = "login"
    }

    when (screen) {
        "login" ->
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { p ->
                    profile = p
                    screen = if (p.roles.role == "operator") "vehicles" else "menu"
                },
            )
        "menu" ->
            MainMenuScreen(
                onGoChecklist = { screen = "vehicles" },
                onGoDashboard = { screen = "dashboard" },
                onGoMaintenance = { screen = "maintenance" },
                onGoReport = { screen = "report" },
            )
        "vehicles" ->
            VehicleListScreen(
                todayShifts = todayShifts,
                currentShift = currentShift,
                checkedVehicles = checkedVehicles,
                isOperator = isOperator,
                onVehicleClick = { v ->
                    selectedV = v
                    screen = "checklist"
                },
                onScan = { screen = "scan" },
                onBack = {
                    screen = if (isOperator) "login" else "menu"
                },
                onLogout = {
                    logout()
                },
            )
        "scan" ->
            ScannerScreen(
                onVehicleScanned = { chassisNo ->
                    scope.launch {
                        // ใช้ profile ปัจจุบัน + repository กลาง — เห็นเฉพาะรถที่ตนเข้าถึงได้
                        val currentProfile = profile // capture local value (หลีกเลี่ยง smart-cast error จาก mutableState)
                        val found =
                            if (currentProfile != null) {
                                vehicleRepository
                                    .getAccessibleVehicles(currentProfile)
                                    .getOrDefault(emptyList())
                                    .firstOrNull { it.chassis_no == chassisNo }
                            } else {
                                null
                            }
                        if (found != null) {
                            selectedV = found
                            screen = "checklist"
                        }
                    }
                },
                onBack = { screen = "vehicles" },
            )
        "checklist" ->
            NewChecklistScreen(
                initialVehicle = selectedV,
                onBack = { screen = "vehicles" },
                onChecklistSaved = { chassisNo, info ->
                    // อัปเดต state (reactive) + persist ลง SharedPreferences
                    checkedVehicles[chassisNo] = info
                    checkedVehicleStore.setCheckedVehicle(todayStr, chassisNo, info)
                },
            )
        "dashboard" -> SupervisorDashboardScreen(onBack = { screen = "menu" })
        "maintenance" -> MaintenanceScreen(onBack = { screen = "menu" })
        "report" -> ReportScreen(onBack = { screen = "menu" })
    }
}
