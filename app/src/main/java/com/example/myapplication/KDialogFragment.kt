package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.eazypermissions.common.model.PermissionResult
import com.example.myapplication.permissions.PermissionsInteractor
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class KDialogFragment : DialogFragment() {

    private val permissionsInteractor: PermissionsInteractor by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_btn_camera.setOnClickListener {
            permissionsInteractor.requestCameraPermissions {
                when (this) {
                    is PermissionResult.PermissionGranted -> {
                        handleResult(this)
                    }
                    is PermissionResult.PermissionDenied -> {
                        handleResult(this)
                    }
                    is PermissionResult.ShowRational -> {
                        handleResult(this)
                    }
                    is PermissionResult.PermissionDeniedPermanently -> {
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
                    is PermissionResult.PermissionGranted -> {
                    }
                    is PermissionResult.PermissionDenied -> {
                    }
                    is PermissionResult.ShowRational -> {
                    }
                    is PermissionResult.PermissionDeniedPermanently -> {
                    }
                }
            }
        }
    }

    private fun handleResult(permissionResult: PermissionResult) {
        Toast.makeText(
            context,
            "Granted " + permissionResult.requestCode,
            Toast.LENGTH_SHORT
        ).show()
    }

}
