package com.example.myapplication.permissions.usecases

import com.example.myapplication.permissions.PermissionRequestResult

interface PermissionRequestUseCase {
    fun requestPermissions(callback: PermissionRequestResult.() -> Unit)
}