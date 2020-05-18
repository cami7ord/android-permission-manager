package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.PermissionRequestResult.*
import com.example.myapplication.permissions.PermissionsInteractor
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val permissionsInteractor: PermissionsInteractor by inject {
        parametersOf(ActivityOrFragmentReference.ActivityReference(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_btn_camera.setOnClickListener {
            permissionsInteractor.requestCameraPermissions {
                when (this) {
                    is Granted -> {
                        handleResult(this)
                    }
                    is Denied -> {
                        handleResult(this)
                    }
                    is Rational -> {
                        handleResult(this)
                    }
                    is PermanentlyDenied -> {
                        handleResult(this)
                    }
                }
            }
        }

        view_btn_location.setOnClickListener {
            permissionsInteractor.requestLocationPermissions {
                handleResult(this)
            }
        }

        view_btn_location_background.setOnClickListener {
            permissionsInteractor.requestBackgroundLocationPermissions {
                when (this) {
                    is Granted -> {
                        handleResult(this)
                    }
                    is Denied -> {
                        handleResult(this)
                    }
                    is Rational -> {
                        handleResult(this)
                    }
                    is PermanentlyDenied -> {
                        handleResult(this)
                    }
                }
            }
        }

        view_btn_java.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
        }

        view_btn_settings.setOnClickListener {
            startActivity(Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", packageName, null)
            })
        }
    }

    private fun handleResult(permissionResult: PermissionRequestResult) {
        Toast.makeText(
            this,
            permissionResult.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

}
