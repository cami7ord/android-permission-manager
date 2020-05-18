package com.example.myapplication.di

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.myapplication.permissions.*
import org.koin.dsl.module

/*sealed class ActivityOrFragment {
    class MyFragment(val reference: Fragment): ActivityOrFragment()
    class MyActivity(val reference: AppCompatActivity): ActivityOrFragment()
}*/

val appModule = module {

    factory<PermissionsInteractor> { (any: Any) ->
        when {
            VERSION.SDK_INT >= VERSION_CODES.Q -> {
                PermissionsInteractorImpl29(any)
            }
            VERSION.SDK_INT >= VERSION_CODES.M -> {
                PermissionsInteractorImpl23(any)
            }
            else -> {
                PermissionsInteractorImpl21(any)
            }
        }
    }
}
