package com.example.myapplication.permissions

import android.Manifest.permission.*
import android.content.Context
import com.eazypermissions.common.model.PermissionResult.*
import com.eazypermissions.dsl.PermissionManager

interface PermissionsInteractor {

    fun requestCameraPermissions(
        context: Context,
        onPermissionGranted: Runnable? = null,
        onPermissionDenied: Runnable? = null,
        onShowRational: Runnable? = null,
        onDeniedPermanently: Runnable? = null
    )

    fun requestLocationPermissions(
        context: Context,
        background: Boolean,
        onPermissionGranted: Runnable? = null,
        onPermissionDenied: Runnable? = null,
        onShowRational: Runnable? = null,
        onDeniedPermanently: Runnable? = null
    )
}

class PermissionsInteractorImpl : PermissionsInteractor {

    private fun requestPermissions(
        context: Context,
        requestId: Int,
        vararg permissions: String,
        onPermissionGranted: Runnable? = null,
        onPermissionDenied: Runnable? = null,
        onShowRational: Runnable? = null,
        onDeniedPermanently: Runnable? = null
    ) {

        PermissionManager._requestPermissions(
            context,
            permissions = *permissions,
            requestId = requestId,
            callback = {
                when (this) {
                    is PermissionGranted -> {
                        onPermissionGranted?.run()
                    }
                    is PermissionDenied -> {
                        onPermissionDenied?.run()
                    }
                    is ShowRational -> {
                        onShowRational?.run()
                    }
                    is PermissionDeniedPermanently -> {
                        onDeniedPermanently?.run()
                    }
                }
            })

    }

    override fun requestCameraPermissions(
        context: Context,
        onPermissionGranted: Runnable?,
        onPermissionDenied: Runnable?,
        onShowRational: Runnable?,
        onDeniedPermanently: Runnable?
    ) {
        val permissionsArray = arrayOf(
            CAMERA
        )

        requestPermissions(
            context,
            requestId = 1,
            permissions = *permissionsArray,
            onPermissionGranted = onPermissionGranted,
            onPermissionDenied = onPermissionDenied,
            onShowRational = onShowRational,
            onDeniedPermanently = onDeniedPermanently
        )
    }

    override fun requestLocationPermissions(
        context: Context,
        background: Boolean,
        onPermissionGranted: Runnable?,
        onPermissionDenied: Runnable?,
        onShowRational: Runnable?,
        onDeniedPermanently: Runnable?
    ) {

        val permissionsArray = if (background) {
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                ACCESS_BACKGROUND_LOCATION
            )
        } else {
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        }

        requestPermissions(
            context,
            requestId = 2,
            permissions = *permissionsArray,
            onPermissionGranted = onPermissionGranted,
            onPermissionDenied = onPermissionDenied,
            onShowRational = onShowRational,
            onDeniedPermanently = onDeniedPermanently
        )
    }
}
