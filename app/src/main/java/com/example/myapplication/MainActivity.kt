package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.permissions.PermissionsInteractor
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val permissionsInteractor: PermissionsInteractor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.setOnClickListener {
            permissionsInteractor.requestCameraPermissions(
                    context = this,
                    onPermissionGranted = openAppSettingsRunnable)
        }
    }

    private val openAppSettingsRunnable = Runnable {
        Toast.makeText(this, "Granted!", Toast.LENGTH_LONG).show()
    }

}
