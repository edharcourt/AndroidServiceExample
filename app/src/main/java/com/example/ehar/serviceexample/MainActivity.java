package com.example.ehar.serviceexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button play = null;
    Button stop = null;
    Button whatever = null;

    private static String LOG_TAG = MainActivity.class.getName();

    public static final int EXT_STOREAGE_READ_REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXT_STOREAGE_READ_REQUEST);
        }
        else
            init();
    }

    private void init() {
        play = (Button) findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MusicService.getInstance() == null)
                    startService(new Intent(MainActivity.this, MusicService.class));
                else
                    MusicService.getInstance().play();
            }
        });

        stop = (Button) findViewById(R.id.stop_button);
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, MusicService.class));
            }
        });

        whatever = (Button) findViewById(R.id.whatever_button);
        whatever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Whatever", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXT_STOREAGE_READ_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            }
            else {
                Log.e(LOG_TAG, "external storage permission not granted");
            }
        }
    }
}