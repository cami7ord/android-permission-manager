package com.example.myapplication.permissions.usecases

import android.Manifest.permission.CAMERA
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.toPermissionRequestResult

interface CameraPermissionRequestUseCase : PermissionRequestUseCase

class CameraPermissionRequestUseCaseImpl(
    private val caller: ActivityOrFragmentReference
) : CameraPermissionRequestUseCase {

    override fun requestPermissions(callback: PermissionRequestResult.() -> Unit) {
        PermissionManager._requestPermissions(
            caller.reference,
            permissions = *arrayOf(CAMERA),
            requestId = CAMERA_REQ_ID,
            callback = { callback(this.toPermissionRequestResult()) }
        )
    }

    companion object {
        private const val CAMERA_REQ_ID = 1
    }
}
