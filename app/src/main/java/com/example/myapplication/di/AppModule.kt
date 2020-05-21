package com.example.myapplication.di

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.myapplication.permissions.*
import com.example.myapplication.permissions.usecases.*
import org.koin.dsl.module

val appModule = module {

    factory<PermissionsInteractor> { (caller: ActivityOrFragmentReference) ->
        when {
            VERSION.SDK_INT >= VERSION_CODES.M -> {
                PermissionsInteractor23AndAboveImpl(
                    CameraPermissionRequestUseCase(caller),
                    ForegroundLocationPermissionRequestUseCase(caller),
                    when {
                        VERSION.SDK_INT >= 30 -> { // TODO use real code reference
                            BackgroundLocationPermissionRequestUseCase30(caller)
                        }
                        VERSION.SDK_INT >= VERSION_CODES.Q -> {
                            BackgroundLocationPermissionRequestUseCase29(caller)
                        }
                        else -> {
                            BackgroundLocationPermissionRequestUseCase23(caller)
                        }
                    }
                )
            }
            else -> {
                PermissionsInteractorUnder23Impl()
            }
        }
    }

}
