package com.example.myapplication.permissions

import android.Manifest.permission.*
import android.content.Context
import com.eazypermissions.common.model.PermissionResult.*
import com.eazypermissions.dsl.PermissionManager

interface PermissionsInteractor {

    fun requestCameraPermissions(
        context: Context,
        permissionsResponseListener: PermissionsResponseListener
    )

    fun requestLocationPermissions(
        context: Context,
        background: Boolean,
        permissionsResponseListener: PermissionsResponseListener
    )
}

class PermissionsResponseListener(
    val onPermissionsGranted: Runnable? = null,
    val onPermissionsDenied: Runnable? = null,
    val onShowRational: Runnable? = null,
    val onDeniedPermanently: Runnable? = null
)

class PermissionsInteractorImpl : PermissionsInteractor {

    companion object {
        const val CAMERA_REQ_ID = 1
        const val LOCATION_REQ_ID = 2

        val CAMERA_PERMISSION_ARRAY = arrayOf(CAMERA)
        val LOCATION_PERMISSION_ARRAY = arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
        )
    }

    private fun requestPermissions(
        context: Context,
        requestId: Int,
        vararg permissions: String,
        permissionsResponseListener: PermissionsResponseListener
    ) {

        PermissionManager._requestPermissions(
            context,
            permissions = *permissions,
            requestId = requestId,
            callback = {
                when (this) {
                    is PermissionGranted -> {
                        permissionsResponseListener.onPermissionsGranted?.run()
                    }
                    is PermissionDenied -> {
                        permissionsResponseListener.onPermissionsDenied?.run()
                    }
                    is ShowRational -> {
                        permissionsResponseListener.onShowRational?.run()
                    }
                    is PermissionDeniedPermanently -> {
                        permissionsResponseListener.onDeniedPermanently?.run()
                    }
                }
            })

    }

    override fun requestCameraPermissions(
        context: Context,
        permissionsResponseListener: PermissionsResponseListener
    ) {
        requestPermissions(
            context,
            requestId = CAMERA_REQ_ID,
            permissions = *CAMERA_PERMISSION_ARRAY,
            permissionsResponseListener = permissionsResponseListener
        )
    }

    override fun requestLocationPermissions(
        context: Context,
        background: Boolean,
        permissionsResponseListener: PermissionsResponseListener
    ) {

        val permissionsArray = if (background) {
            arrayOf(*LOCATION_PERMISSION_ARRAY, ACCESS_BACKGROUND_LOCATION)
        } else {
            LOCATION_PERMISSION_ARRAY
        }

        requestPermissions(
            context,
            requestId = LOCATION_REQ_ID,
            permissions = *permissionsArray,
            permissionsResponseListener = permissionsResponseListener
        )
    }
}
