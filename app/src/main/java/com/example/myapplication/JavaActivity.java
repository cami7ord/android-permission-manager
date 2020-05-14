package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.permissions.PermissionsInteractor;
import com.example.myapplication.permissions.PermissionsResponseListener;

import kotlin.Lazy;

import static org.koin.java.KoinJavaComponent.inject;

public class JavaActivity extends AppCompatActivity {

    private Lazy<PermissionsInteractor> permissionsInteractor = inject(PermissionsInteractor.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button viewBtnCamera = findViewById(R.id.view_btn_camera);
        Button viewBtnLocation = findViewById(R.id.view_btn_location);
        Button viewBtnBackground = findViewById(R.id.view_btn_location_background);

        viewBtnCamera.setOnClickListener(v -> permissionsInteractor.getValue().requestCameraPermissions(
                JavaActivity.this,
                new PermissionsResponseListener(
                        openAppSettingsRunnable,
                        null,
                        null,
                        null)
        ));

        viewBtnLocation.setOnClickListener(v -> permissionsInteractor.getValue().requestLocationPermissions(
                JavaActivity.this,
                false,
                new PermissionsResponseListener(
                        openAppSettingsRunnable,
                        null,
                        null,
                        null)
        ));

        viewBtnBackground.setOnClickListener(v -> permissionsInteractor.getValue().requestLocationPermissions(
                JavaActivity.this,
                true,
                new PermissionsResponseListener(
                        openAppSettingsRunnable,
                        null,
                        null,
                        null)
        ));
    }

    final Runnable openAppSettingsRunnable = () ->
            Toast.makeText(JavaActivity.this, "Granted", Toast.LENGTH_SHORT).show();
}
