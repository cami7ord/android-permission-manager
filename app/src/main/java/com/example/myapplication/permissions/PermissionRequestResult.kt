package com.example.myapplication.permissions

import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.common.model.PermissionResult.*

sealed class PermissionRequestResult(val result : PermissionResult) {
    class Granted(result: PermissionGranted) : PermissionRequestResult(result)
    class Denied(result : PermissionDenied) : PermissionRequestResult(result)
    class Rational(result : ShowRational) : PermissionRequestResult(result)
    class PermanentlyDenied(result : PermissionDeniedPermanently) : PermissionRequestResult(result)
}
