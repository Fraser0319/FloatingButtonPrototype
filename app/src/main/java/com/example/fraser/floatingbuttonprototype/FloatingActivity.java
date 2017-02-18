package com.example.fraser.floatingbuttonprototype;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class FloatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        int measuredWidth = size.x;
        int measuredHeight = size.y;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = measuredHeight - 600 ;
        params.width = measuredWidth - 100;
        params.y = -200;
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        this.getWindow().setAttributes(params);
    }


    public void close(View v){
        finish();
    }
}
