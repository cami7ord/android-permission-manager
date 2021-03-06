package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.permissions.ActivityOrFragmentReference;
import com.example.myapplication.permissions.PermissionRequestResult;
import com.example.myapplication.permissions.PermissionsInteractor;

import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static org.koin.core.parameter.DefinitionParametersKt.parametersOf;
import static org.koin.java.KoinJavaComponent.inject;

public class JavaActivity extends AppCompatActivity {

    private Lazy<PermissionsInteractor> permissionsInteractor =
            inject(PermissionsInteractor.class,
                    null,
                    () -> parametersOf(new ActivityOrFragmentReference.ActivityReference(this))
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button viewBtnCamera = findViewById(R.id.view_btn_camera);
        Button viewBtnLocation = findViewById(R.id.view_btn_location);
        Button viewBtnBackground = findViewById(R.id.view_btn_location_background);
        Button viewBtnFragment = findViewById(R.id.view_btn_fragment);

        viewBtnCamera.setOnClickListener(v -> permissionsInteractor.getValue().requestCameraPermissions(
                this::handleResult
        ));

        viewBtnLocation.setOnClickListener(v -> permissionsInteractor.getValue().requestForegroundLocationPermissions(
                permissionResult -> handleResult(permissionResult)
        ));

        viewBtnBackground.setOnClickListener(v -> permissionsInteractor.getValue().requestBackgroundLocationPermissions(
                function));

        viewBtnFragment.setOnClickListener(v -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = new KDialogFragment();
            dialogFragment.show(ft, "dialog");
        });
    }

    final Function1<PermissionRequestResult, Unit> function = new Function1<PermissionRequestResult, Unit>() {
        @Override
        public Unit invoke(PermissionRequestResult permissionResult) {
            return handleResult(permissionResult);
        }
    };

    private Unit handleResult(PermissionRequestResult permissionResult) {
        Toast.makeText(JavaActivity.this,
                permissionResult.toString(),
                Toast.LENGTH_SHORT).show();
        return null;
    }

}
