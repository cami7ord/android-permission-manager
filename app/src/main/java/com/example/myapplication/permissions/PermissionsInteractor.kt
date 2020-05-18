package com.example.myapplication.permissions

import android.Manifest.permission.*
import com.eazypermissions.dsl.PermissionManager
import com.example.myapplication.permissions.PermissionRequestResult.*

interface PermissionsInteractor {

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

abstract class BasePermissionInteractor(private val activityOrFragmentReference: ActivityOrFragmentReference) :
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

    override fun requestCameraPermissions(
        callback: PermissionRequestResult.() -> Unit
    ) {
        requestPermissions(
            requestId = CAMERA_REQ_ID,
            permissions = *CAMERA_PERMISSION_ARRAY,
            callback = callback
        )
    }

    override fun requestLocationPermissions(
        callback: PermissionRequestResult.() -> Unit
    ) {
        requestPermissions(
            requestId = LOCATION_REQ_ID,
            permissions = *LOCATION_PERMISSION_ARRAY,
            callback = callback
        )
    }

    protected fun requestPermissions(
        requestId: Int,
        vararg permissions: String,
        callback: PermissionRequestResult.() -> Unit
    ) {

        PermissionManager._requestPermissions(
            activityOrFragmentReference.reference,
            permissions = *permissions,
            requestId = requestId,
            callback = { callback(this.toPermissionRequestResult()) }
        )
    }

}

class PermissionsInteractorImpl21(activityOrFragmentReference: ActivityOrFragmentReference) :
    BasePermissionInteractor(
        activityOrFragmentReference
    ) {
    override fun requestLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            Granted(requestCode = LOCATION_REQ_ID)
        )
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            Granted(requestCode = BACK_LOCATION_REQ_ID)
        )
    }
}

class PermissionsInteractorImpl23(activityOrFragmentReference: ActivityOrFragmentReference) :
    BasePermissionInteractor(
        activityOrFragmentReference
    ) {
    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        requestLocationPermissions(callback)
    }
}


class PermissionsInteractorImpl29(activityOrFragmentReference: ActivityOrFragmentReference) :
    BasePermissionInteractor(
        activityOrFragmentReference
    ) {
    override fun requestBackgroundLocationPermissions(
        callback: PermissionRequestResult.() -> Unit
    ) {
        requestLocationPermissions {
            when (this) {
                is Granted -> {
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
