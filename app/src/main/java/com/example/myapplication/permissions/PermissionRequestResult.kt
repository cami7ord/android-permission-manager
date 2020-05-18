package com.example.myapplication.permissions

import com.eazypermissions.common.model.PermissionResult

sealed class PermissionRequestResult(val requestCode: Int) {
    class Granted(requestCode: Int) : PermissionRequestResult(requestCode = requestCode)
    class Rational(requestCode: Int) : PermissionRequestResult(requestCode = requestCode)
    class Denied(requestCode: Int, val deniedPermissions: List<String>) :
        PermissionRequestResult(requestCode = requestCode)

    class PermanentlyDenied(requestCode: Int, val permanentlyDeniedPermissions: List<String>) :
        PermissionRequestResult(requestCode = requestCode)
}

fun PermissionResult.toPermissionRequestResult(): PermissionRequestResult {
    return when (this) {
        is PermissionResult.PermissionGranted -> PermissionRequestResult.Granted(requestCode = requestCode)
        is PermissionResult.ShowRational -> PermissionRequestResult.Rational(requestCode = requestCode)
        is PermissionResult.PermissionDenied -> PermissionRequestResult.Denied(
            requestCode = requestCode,
            deniedPermissions = deniedPermissions
        )
        is PermissionResult.PermissionDeniedPermanently -> PermissionRequestResult.PermanentlyDenied(
            requestCode = requestCode,
            permanentlyDeniedPermissions = permanentlyDeniedPermissions
        )
    }
}