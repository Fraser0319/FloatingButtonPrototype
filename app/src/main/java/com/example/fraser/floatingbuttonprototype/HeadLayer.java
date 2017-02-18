package com.example.fraser.floatingbuttonprototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Fraser on 01/12/2016.
 */

public class HeadLayer extends View {

    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private boolean shouldClick;
    private boolean openClose = false;

    public HeadLayer(Context context) {
        super(context);

        mContext = context;
        mFrameLayout = new FrameLayout(mContext);

        addToWindowManager();
        //createOnTouchEvent();

    }

    private void addToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,// allows you to use the back button properly
                PixelFormat.TRANSLUCENT);
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.gravity = Gravity.LEFT;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
        layoutInflater.inflate(R.layout.head, mFrameLayout);
        //final ImageView imageView = (ImageView) mFrameLayout.findViewById(R.id.imageView);
        // add a click listener to the image view
        final ImageView imageView = (ImageView) mFrameLayout.findViewById(R.id.imageView);
        
        // Support dragging the image view
        imageView.setOnTouchListener(new OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;
            private int initX, initY;
            private int initTouchX, initTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = System.currentTimeMillis();
                        Log.e("Down", "touch");
                        //shouldClick = false;
                        initX = params.x;
                        initY = params.y;
                        initTouchX = x;
                        initTouchY = y;

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {

                        params.x = initX + (x - initTouchX);
                        params.y = initY + (y - initTouchY);
                        mWindowManager.updateViewLayout(mFrameLayout, params);
                        return true;

                    }

                    case MotionEvent.ACTION_UP: {
                        long clickDuration = System.currentTimeMillis() - startClickTime;

                        if (clickDuration < MAX_CLICK_DURATION) {
                            //click event has occurred

                            if (!openClose) {
                                Intent intent = new Intent(getContext(), testActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears activity to open the new small activity
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getContext().startActivity(intent);
                                openClose = true;
                            } else {
//                                Intent intent = new Intent(getContext(),MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears activity to open the main activity
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                getContext().startActivity(intent);

                                openClose = false;
                            }
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * Removes the view from window manager.
     */
    public void destroy() {
        mWindowManager.removeView(mFrameLayout);
    }
}


