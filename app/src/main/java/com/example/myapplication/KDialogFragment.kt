package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.permissions.ActivityOrFragmentReference
import com.example.myapplication.permissions.PermissionRequestResult
import com.example.myapplication.permissions.PermissionRequestResult.*
import com.example.myapplication.permissions.PermissionsInteractor
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class KDialogFragment : DialogFragment() {

    private val permissionsInteractor: PermissionsInteractor by inject {
        parametersOf(ActivityOrFragmentReference.FragmentReference(this))
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
            permissionsInteractor.requestForegroundLocationPermissions {
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
    }

    private fun handleResult(permissionResult: PermissionRequestResult) {
        Toast.makeText(
            context,
            permissionResult.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

}
