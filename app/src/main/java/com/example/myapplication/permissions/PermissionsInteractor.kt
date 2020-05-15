package com.example.myapplication.permissions

import android.Manifest.permission.*
import android.content.Context
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.dsl.PermissionManager

interface PermissionsInteractor {

    //var context: Context

    fun requestCameraPermissions(
        //context: Context,
        callback: PermissionResult.() -> Unit
    )

    fun requestLocationPermissions(
        //context: Context,
        background: Boolean,
        callback: PermissionResult.() -> Unit
    )

}

class PermissionsInteractorImpl(private val activityOrFragment: Any) : PermissionsInteractor {

    //override lateinit var context: Context

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
        //context: Context,
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

    override fun requestCameraPermissions(
        //context: Context,
        callback: PermissionResult.() -> Unit
    ) {
        requestPermissions(
            //context,
            requestId = CAMERA_REQ_ID,
            permissions = *CAMERA_PERMISSION_ARRAY,
            callback = callback
        )
    }

    override fun requestLocationPermissions(
        //context: Context,
        background: Boolean,
        callback: PermissionResult.() -> Unit
    ) {

        val permissionsArray = if (background) {
            arrayOf(*LOCATION_PERMISSION_ARRAY, ACCESS_BACKGROUND_LOCATION)
        } else {
            LOCATION_PERMISSION_ARRAY
        }

        requestPermissions(
            //context,
            requestId = LOCATION_REQ_ID,
            permissions = *permissionsArray,
            callback = callback
        )
    }

}
