package com.crystal.getfps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.crystal.getfps.service.FloatWinService;


public class MainActivity extends AppCompatActivity {

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    Intent floatWinIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatWinIntent = new Intent(MainActivity.this, FloatWinService.class);
    }


    public void begin(View v) {
        askForPermission();
    }


    public void end(View v) {
        //关闭悬浮框
        stopService(floatWinIntent);
    }



    public void askForPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(MainActivity.this, "Permission denied...Please try again!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            startService(floatWinIntent);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(MainActivity.this, "Fail.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Success.", Toast.LENGTH_SHORT).show();
                //启动FxService
                startService(floatWinIntent);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
