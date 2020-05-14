package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eazypermissions.common.model.PermissionResult;
import com.example.myapplication.permissions.PermissionsInteractor;

import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
                function
        ));

        viewBtnLocation.setOnClickListener(v -> permissionsInteractor.getValue().requestLocationPermissions(
                JavaActivity.this,
                false,
                function
        ));

        viewBtnBackground.setOnClickListener(v -> permissionsInteractor.getValue().requestLocationPermissions(
                JavaActivity.this,
                true,
                function));
    }

    final Function1<PermissionResult, Unit> function = new Function1<PermissionResult, Unit>() {
        @Override
        public Unit invoke(PermissionResult permissionResult) {
            return handleResult(permissionResult);
        }
    };

    private Unit handleResult(PermissionResult permissionResult) {
        Toast.makeText(JavaActivity.this,
                "Granted " + permissionResult.getRequestCode(),
                Toast.LENGTH_SHORT).show();
        return null;
    }

}
