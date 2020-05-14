package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.permissions.PermissionsInteractor
import com.example.myapplication.permissions.PermissionsInteractorImpl
import org.koin.dsl.module

val appModule = module {

    //single<PermissionsInteractor> { PermissionsInteractorImpl() }

    //factory<PermissionsInteractor> { PermissionsInteractorImpl() }

    factory<PermissionsInteractor> { (context: Context) -> PermissionsInteractorImpl(context) }
}
