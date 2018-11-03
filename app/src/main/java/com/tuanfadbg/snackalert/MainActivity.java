package com.tuanfadbg.snackalert;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSuccess, btnError, btnNormal, btnWarning, btnImage, btnCustom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSuccess = findViewById(R.id.btn_success);
        btnError = findViewById(R.id.btn_error);
        btnNormal = findViewById(R.id.btn_normal);
        btnWarning = findViewById(R.id.btn_warning);
        btnImage = findViewById(R.id.btn_image);
        btnCustom = findViewById(R.id.btn_custom);


        btnSuccess.setOnClickListener(v -> new SnackAlert(MainActivity.this)
                .setTitle("Success")
                .setMessage("This is a success message")
                .setOnAlertClickListener(() -> Toast.makeText(MainActivity.this, "You click to alert", Toast.LENGTH_SHORT).show())
                .show());

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackAlert(MainActivity.this)
                        .setTitle("Normal")
                        .setMessage("This is a normal message")
                        .setType(SnackAlert.NORMAL)
                        .show();
            }
        });
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackAlert(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("This is a error message")
                        .setType(SnackAlert.ERROR)
                        .show();
            }
        });
        btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackAlert(MainActivity.this)
                        .setTitle("Warning")
                        .setMessage("This is a normal message")
                        .setType(SnackAlert.WARNING)
                        .show();
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackAlert(MainActivity.this)
                        .setTitle("Image")
                        .setMessage("This is a image message, custom duration")
                        .setType(SnackAlert.ERROR)
                        .setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_launcher_foreground))
                        .setDurationAnimationShow(2000)
                        .setDurationAnimationHide(2000)
                        .setInterpolatorShow(new DecelerateInterpolator())
                        .setOnAlertClickListener(() -> Toast.makeText(MainActivity.this, "You click to alert", Toast.LENGTH_SHORT).show())
                        .show();
            }
        });
        btnCustom.setOnClickListener(v -> new SnackAlert(MainActivity.this)
                .setTitle("Custom title")
                .setMessage("Custome color message")
                .setType(SnackAlert.WARNING)
                .setSwipeToDimiss(true)
                .setBackgroundColor(Color.RED)
                .setBackgroundAlpha(1)
                .setTitleColor(Color.BLACK)
                .show());

    }
}
