package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.common.model.PermissionResult.*
import com.example.myapplication.permissions.PermissionsInteractor
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val permissionsInteractor: PermissionsInteractor by inject {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_btn_camera.setOnClickListener {
            permissionsInteractor.requestCameraPermissions {
                when (this) {
                    is PermissionGranted -> {
                        handleResult(this)
                    }
                    is PermissionDenied -> {
                        handleResult(this)
                    }
                    is ShowRational -> {
                        handleResult(this)
                    }
                    is PermissionDeniedPermanently -> {
                        handleResult(this)
                    }
                }
            }
        }

        view_btn_location.setOnClickListener {
            permissionsInteractor.requestLocationPermissions(
                background = false
            ) {
                handleResult(this)
            }
        }

        view_btn_location_background.setOnClickListener {
            permissionsInteractor.requestLocationPermissions(
                background = true
            ) {
                when (this) {
                    is PermissionGranted -> {
                    }
                    is PermissionDenied -> {
                    }
                    is ShowRational -> {
                    }
                    is PermissionDeniedPermanently -> {
                    }
                }
            }
        }

        view_btn_java.setOnClickListener {
            val intent = Intent(this, JavaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleResult(permissionResult: PermissionResult) {
        Toast.makeText(
            this,
            "Granted " + permissionResult.requestCode,
            Toast.LENGTH_SHORT
        ).show()
    }

}
