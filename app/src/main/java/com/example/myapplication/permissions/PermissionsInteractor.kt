package com.example.myapplication.permissions

import com.example.myapplication.permissions.PermissionsInteractor.Companion.CAMERA_REQ_ID
import com.example.myapplication.permissions.PermissionsInteractor.Companion.LOCATION_REQ_ID
import com.example.myapplication.permissions.PermissionsInteractor.Companion.BACK_LOCATION_REQ_ID
import com.example.myapplication.permissions.usecases.CameraPermissionRequestUseCase
import com.example.myapplication.permissions.usecases.ForegroundLocationPermissionRequestUseCase

interface PermissionsInteractor {

    companion object {
        const val CAMERA_REQ_ID = 1
        const val LOCATION_REQ_ID = 2
        const val BACK_LOCATION_REQ_ID = 3
    }

    fun requestCameraPermissions(
        callback: PermissionRequestResult.() -> Unit
    )

    fun requestLocationPermissions(
        callback: PermissionRequestResult.() -> Unit
    )

    fun requestBackgroundLocationPermissions(
        callback: PermissionRequestResult.() -> Unit
    )

}

abstract class BackgroundPermissionsRequester {
    abstract fun requestBackgroundPermissions(
        callback: PermissionRequestResult.() -> Unit
    )
}

class PermissionsInteractorUnder23Impl : PermissionsInteractor {
    override fun requestCameraPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            PermissionRequestResult.Granted(requestCode = CAMERA_REQ_ID)
        )
    }

    override fun requestLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            PermissionRequestResult.Granted(requestCode = LOCATION_REQ_ID)
        )
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            PermissionRequestResult.Granted(requestCode = BACK_LOCATION_REQ_ID)
        )
    }

}

class PermissionsInteractor23AndAboveImpl(
    private val cameraUseCase: CameraPermissionRequestUseCase,
    private val foregroundUseCase: ForegroundLocationPermissionRequestUseCase,
    private val backgroundRequester: BackgroundPermissionsRequester
) : PermissionsInteractor {
    override fun requestCameraPermissions(callback: PermissionRequestResult.() -> Unit) {
        cameraUseCase.invoke {
            callback(this.toPermissionRequestResult())
        }
    }

    override fun requestLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        foregroundUseCase.invoke {
            callback(this.toPermissionRequestResult())
        }
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        backgroundRequester.requestBackgroundPermissions {
            callback(this)
        }
    }
}
