package com.example.myapplication.permissions

import com.example.myapplication.permissions.usecases.BackgroundLocationPermissionRequestUseCase
import com.example.myapplication.permissions.usecases.CameraPermissionRequestUseCase
import com.example.myapplication.permissions.usecases.ForegroundLocationPermissionRequestUseCase

interface PermissionsInteractor {
    fun requestCameraPermissions(callback: PermissionRequestResult.() -> Unit)
    fun requestForegroundLocationPermissions(callback: PermissionRequestResult.() -> Unit)
    fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit)
}

// Since Android versions lower than 23 doesn't support on-demand permissions, we pass granted result
class AlwaysGrantedPermissionsInteractorImpl : PermissionsInteractor {
    override fun requestCameraPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(PermissionRequestResult.Granted(requestCode = 0))
    }

    override fun requestForegroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(PermissionRequestResult.Granted(requestCode = 0))
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(PermissionRequestResult.Granted(requestCode = 0))
    }
}

// Android SDK 23 and higher does support on-demand permissions -> delegate request to specific use cases
class OnDemandPermissionsInteractorImpl(
    private val cameraUseCase: CameraPermissionRequestUseCase,
    private val foregroundLocationUseCase: ForegroundLocationPermissionRequestUseCase,
    private val backgroundLocationUseCase: BackgroundLocationPermissionRequestUseCase
) : PermissionsInteractor {
    override fun requestCameraPermissions(callback: PermissionRequestResult.() -> Unit) {
        cameraUseCase.requestPermissions(callback)
    }

    override fun requestForegroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        foregroundLocationUseCase.requestPermissions(callback)
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        backgroundLocationUseCase.requestPermissions(callback)
    }
}
