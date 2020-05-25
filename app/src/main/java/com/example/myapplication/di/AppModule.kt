package com.example.myapplication.di

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionsInteractor
import com.example.myapplication.permissions.OnDemandPermissionsInteractorImpl
import com.example.myapplication.permissions.AlwaysGrantedPermissionsInteractorImpl
import com.example.myapplication.permissions.usecases.*
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {

    factory<PermissionsInteractor> { (caller: ActivityOrFragmentReference) ->
        when {
            VERSION.SDK_INT < VERSION_CODES.M -> AlwaysGrantedPermissionsInteractorImpl()
            else -> {
                OnDemandPermissionsInteractorImpl(
                    cameraUseCase = get(parameters = { parametersOf(caller) }),
                    foregroundLocationUseCase = get(parameters = { parametersOf(caller) }),
                    backgroundLocationUseCase = get(parameters = { parametersOf(caller) })
                )
            }
        }
    }

    // region Permission specific use cases

    factory<CameraPermissionRequestUseCase> { (caller: ActivityOrFragmentReference) ->
        CameraPermissionRequestUseCaseImpl(caller = caller)
    }

    factory<ForegroundLocationPermissionRequestUseCase> { (caller: ActivityOrFragmentReference) ->
        ForegroundLocationPermissionRequestUseCaseImpl(caller = caller)
    }

    factory<BackgroundLocationPermissionRequestUseCase> { (caller: ActivityOrFragmentReference) ->
        when {
            VERSION.SDK_INT >= 30 -> BackgroundLocationPermissionRequestUseCase30Impl(caller) // TODO use real code reference
            VERSION.SDK_INT >= VERSION_CODES.Q -> BackgroundLocationPermissionRequestUseCase29Impl(caller)
            else -> BackgroundLocationPermissionRequestUseCase23Impl(get(parameters = { parametersOf(caller) }))
        }
    }

    // endregion
}
