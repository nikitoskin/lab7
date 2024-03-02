package com.example.lab7;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.CAMERA
                    }, 100);
                }
                else {
                    ScanOptions options = new ScanOptions();
                    options.setPrompt("Скан кода");
                    options.setBeepEnabled(true);
                    options.setOrientationLocked(true);
                    options.setCaptureActivity(CaptureAct.class);

                    barLauncher.launch(options);
                }
            }
        });
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null) {
            if(result.getContents().toString().equals("Tree")) {
                Intent intent = new Intent(MainActivity.this, TreeActivity.class);
                startActivity(intent);
            }
            else if(result.getContents().toString().equals("Car")) {
                Intent intent = new Intent(MainActivity.this, CarActivity.class);
                startActivity(intent);
            }
            else if(result.getContents().toString().equals("House")) {
                Intent intent = new Intent(MainActivity.this, HouseActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Неизвестный QR-код", Toast.LENGTH_LONG).show();
            }
        }
    });
}