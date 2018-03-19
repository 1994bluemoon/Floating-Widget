package com.example.dminh.floatingwidget;



import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttonShow)
    Button buttonShow;

    public static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            RuntimePermissionForUser();
        }

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                    startService(new Intent(MainActivity.this, FloatingWidgetShowService.class));

                    finish();

                } else if (Settings.canDrawOverlays(MainActivity.this)) {

                    startService(new Intent(MainActivity.this, FloatingWidgetShowService.class));

                    finish();

                } else {
                    RuntimePermissionForUser();

                    Toast.makeText(MainActivity.this, "System Alert Window Permission Is Required For Floating Widget.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void RuntimePermissionForUser() {

        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
}
