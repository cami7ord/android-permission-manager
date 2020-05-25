package com.example.myapplication.permissions.usecases

import android.Manifest.permission
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.toPermissionRequestResult

interface BackgroundLocationPermissionRequestUseCase : PermissionRequestUseCase

// For Android SDK 23 - 28 (including) Background location doesn't exist -> request foreground one
class BackgroundLocationPermissionRequestUseCase23Impl(
    private val foregroundUseCase: ForegroundLocationPermissionRequestUseCase
) : BackgroundLocationPermissionRequestUseCase {

    override fun requestPermissions(callback: PermissionRequestResult.() -> Unit) {
        foregroundUseCase.requestPermissions(callback)
    }
}

// For Android SDK 29 - Background location permission must be requested together with a background one
class BackgroundLocationPermissionRequestUseCase29Impl(
    private val caller: ActivityOrFragmentReference
) : BackgroundLocationPermissionRequestUseCase {

    override fun requestPermissions(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION,
                permission.ACCESS_BACKGROUND_LOCATION
            ),
            requestId = BACK_LOCATION_REQ_ID,
            callback = { callback(this.toPermissionRequestResult()) }
        )
    }

    companion object {
        private const val BACK_LOCATION_REQ_ID = 3
    }
}

// For Android SDK 30 - Foreground location permission must be requested and then the background one can be requested
class BackgroundLocationPermissionRequestUseCase30Impl(
    private val caller: ActivityOrFragmentReference
) : BackgroundLocationPermissionRequestUseCase {

    override fun requestPermissions(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION
            ),
            requestId = LOCATION_REQ_ID,
            callback = {
                when (this) {
                    is PermissionResult.PermissionGranted -> onForegroundPermissionsGranted(callback)
                    else -> callback(this.toPermissionRequestResult())
                }
            }
        )
    }

    private fun onForegroundPermissionsGranted(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(permission.ACCESS_BACKGROUND_LOCATION),
            requestId = BACK_LOCATION_REQ_ID,
            callback = { callback(this.toPermissionRequestResult()) }
        )
    }

    companion object {
        private const val LOCATION_REQ_ID = 2
        private const val BACK_LOCATION_REQ_ID = 3
    }
}