package com.example.myapplication.permissions.usecases

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.toPermissionRequestResult

interface ForegroundLocationPermissionRequestUseCase : PermissionRequestUseCase

class ForegroundLocationPermissionRequestUseCaseImpl(
    private val caller: ActivityOrFragmentReference
) : ForegroundLocationPermissionRequestUseCase {

    override fun requestPermissions(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ),
            requestId = LOCATION_REQ_ID,
            callback = { callback(this.toPermissionRequestResult()) }
        )
    }

    companion object {
        private const val LOCATION_REQ_ID = 2
    }
}
