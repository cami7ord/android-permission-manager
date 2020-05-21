package com.example.myapplication.permissions.usecases

import android.Manifest.permission.*
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.BackgroundPermissionsRequester
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.PermissionsInteractor.Companion.BACK_LOCATION_REQ_ID
import com.example.myapplication.permissions.toPermissionRequestResult

class BackgroundLocationPermissionRequestUseCase29(
    private val caller: ActivityOrFragmentReference
): BackgroundPermissionsRequester() {

    override fun requestBackgroundPermissions(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                ACCESS_BACKGROUND_LOCATION
            ),
            requestId = BACK_LOCATION_REQ_ID,
            callback = {
                callback(this.toPermissionRequestResult())
            }
        )
    }
}