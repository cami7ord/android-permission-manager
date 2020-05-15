package com.example.myapplication.permissions

import android.Manifest.permission.*
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.common.model.PermissionResult.*
import com.eazypermissions.dsl.PermissionManager

interface PermissionsInteractor {

    fun requestCameraPermissions(
        callback: PermissionResult.() -> Unit
    )

    fun requestLocationPermissions(
        callback: PermissionResult.() -> Unit
    )

    fun requestBackgroundLocationPermissions(
        callback: PermissionResult.() -> Unit
    )

}

abstract class BasePermissionInteractor(private val activityOrFragment: Any) :
    PermissionsInteractor {

    companion object {
        const val CAMERA_REQ_ID = 1
        const val LOCATION_REQ_ID = 2
        const val BACK_LOCATION_REQ_ID = 3

        val CAMERA_PERMISSION_ARRAY = arrayOf(CAMERA)
        val LOCATION_PERMISSION_ARRAY = arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
        )
        val BACK_LOCATION_PERMISSION_ARRAY = arrayOf(ACCESS_BACKGROUND_LOCATION)
    }

    protected fun requestPermissions(
        requestId: Int,
        vararg permissions: String,
        callback: PermissionResult.() -> Unit
    ) {

        PermissionManager._requestPermissions(
            activityOrFragment,
            permissions = *permissions,
            requestId = requestId,
            callback = callback
        )
    }

}

class PermissionsInteractorImpl(activityOrFragment: Any) : BasePermissionInteractor(
    activityOrFragment
) {

    override fun requestCameraPermissions(
        callback: PermissionResult.() -> Unit
    ) {
        requestPermissions(
            requestId = CAMERA_REQ_ID,
            permissions = *CAMERA_PERMISSION_ARRAY,
            callback = callback
        )
    }

    override fun requestLocationPermissions(
        callback: PermissionResult.() -> Unit
    ) {
        requestPermissions(
            requestId = LOCATION_REQ_ID,
            permissions = *LOCATION_PERMISSION_ARRAY,
            callback = callback
        )
    }

    override fun requestBackgroundLocationPermissions(
        callback: PermissionResult.() -> Unit
    ) {
        requestLocationPermissions {
            when (this) {
                is PermissionGranted -> {
                    requestPermissions(
                        requestId = BACK_LOCATION_REQ_ID,
                        permissions = *BACK_LOCATION_PERMISSION_ARRAY,
                        callback = callback
                    )
                }
                else -> {
                    callback(this)
                }
            }
        }
    }

}
