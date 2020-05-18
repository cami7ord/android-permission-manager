package com.example.myapplication.permissions

import android.Manifest.permission.*
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.common.model.PermissionResult.*
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

    override fun requestCameraPermissions(
        callback: PermissionRequestResult.() -> Unit
    ) {
        requestPermissions(
            requestId = CAMERA_REQ_ID,
            permissions = *CAMERA_PERMISSION_ARRAY,
            callback = {
                when (this) {
                    is PermissionGranted -> callback(Granted(this))
                    is PermissionDenied -> callback(Denied(this))
                    is ShowRational -> callback(Rational(this))
                    is PermissionDeniedPermanently -> callback(PermanentlyDenied(this))
                }
            }
        )
    }

    override fun requestLocationPermissions(
        callback: PermissionRequestResult.() -> Unit
    ) {
        requestPermissions(
            requestId = LOCATION_REQ_ID,
            permissions = *LOCATION_PERMISSION_ARRAY,
            callback = {
                when (this) {
                    is PermissionGranted -> callback(Granted(this))
                    is PermissionDenied -> callback(Denied(this))
                    is ShowRational -> callback(Rational(this))
                    is PermissionDeniedPermanently -> callback(PermanentlyDenied(this))
                }
            }
        )
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

class PermissionsInteractorImpl21(activityOrFragment: Any) : BasePermissionInteractor(
    activityOrFragment
) {
    override fun requestLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            Granted(
                PermissionGranted(LOCATION_REQ_ID)
            )
        )
    }

    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        callback(
            Granted(
                PermissionGranted(BACK_LOCATION_REQ_ID)
            )
        )
    }
}

class PermissionsInteractorImpl23(activityOrFragment: Any) : BasePermissionInteractor(
    activityOrFragment
) {
    override fun requestBackgroundLocationPermissions(callback: PermissionRequestResult.() -> Unit) {
        requestLocationPermissions(callback)
    }
}


class PermissionsInteractorImpl29(activityOrFragment: Any) : BasePermissionInteractor(
    activityOrFragment
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
                        callback = {
                            when (this) {
                                is PermissionGranted -> callback(Granted(this))
                                is PermissionDenied -> callback(Denied(this))
                                is ShowRational -> callback(Rational(this))
                                is PermissionDeniedPermanently -> callback(PermanentlyDenied(this))
                            }
                        }
                    )
                }
                else -> {
                    callback(this)
                }
            }
        }
    }
}
