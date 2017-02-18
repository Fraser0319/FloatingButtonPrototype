package com.example.fraser.floatingbuttonprototype;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ImageButton startNotification;
    private ImageButton endNotification;
    private static boolean serviceState;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private PermissionChecker mPermissionChecker;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        // setUpTabs();
        if (getIntent().getExtras() != null) {
            serviceState = getIntent().getExtras().getBoolean("state");
        }
        checkPermissions();
        getServiceState();
    }

    public void checkPermissions() {
        mPermissionChecker = new PermissionChecker(this);
        if (!mPermissionChecker.isRequiredPermissionGranted()) {
            Intent intent = mPermissionChecker.createRequiredPermissionIntent();
            startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean getServiceState() {
        SharedPreferences sharedPrefs = getSharedPreferences("com.example.fraser.floatingbuttonprototype", MODE_PRIVATE);

        startNotification = (ImageButton) findViewById(R.id.start_notification);
        endNotification = (ImageButton) findViewById(R.id.end_notification);
        if (sharedPrefs.getBoolean("service_status", serviceState)) {
            startNotification.setEnabled(false);
            endNotification.setEnabled(true);
            endNotification.setAlpha((float) 1);
            startNotification.setAlpha((float) 0.2);
            return true;
        } else {
            endNotification.setEnabled(false);
            startNotification.setEnabled(true);
            startNotification.setAlpha((float) 1);
            endNotification.setAlpha((float) 0.2);
            return false;
        }
    }

    public void startService(View v) {
        serviceState = true;
        SharedPreferences.Editor editor = getSharedPreferences("com.example.fraser.floatingbuttonprototype", MODE_PRIVATE).edit();
        editor.putBoolean("service_status", serviceState);
        editor.commit();

        getServiceState();
        Intent service = new Intent(getApplicationContext(), HeadService.class);
        startService(service);
    }

    public void stopService(View v) {
        serviceState = false;
        SharedPreferences.Editor editor = getSharedPreferences("com.example.fraser.floatingbuttonprototype", MODE_PRIVATE).edit();
        editor.putBoolean("service_status", serviceState);
        editor.commit();

        getServiceState();
        Intent intent = new Intent(getApplicationContext(), HeadService.class);
        stopService(intent);
        Toast.makeText(getApplicationContext(), "service stopped", Toast.LENGTH_LONG).show();
    }


    private void setUpTabs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setUpPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
    }

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {

            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
