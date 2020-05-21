package com.example.myapplication.permissions.usecases

import android.Manifest.permission
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionsInteractor.Companion.CAMERA_REQ_ID

class CameraPermissionRequestUseCase constructor(
    private val caller: ActivityOrFragmentReference
) {

    operator fun invoke(callback: PermissionResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(permission.CAMERA),
            requestId = CAMERA_REQ_ID,
            callback = { callback(this) }
        )
    }
}
