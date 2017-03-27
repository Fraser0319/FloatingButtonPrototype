package com.example.fraser.floatingbuttonprototype.Activities;

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

import com.example.fraser.floatingbuttonprototype.R;
/**
 *
 * class makes use of code from this tutorial
 *
 * — example of a simple use of a window head
 * https://codingshark.wordpress.com/2015/02/01/how-to-draw-on-top-of-other-applications/?utm_source=androiddevdigest
 * https://github.com/mollyIV/ChatHeads
 */

public class HeadLayer extends View {

    private Context context;
    private FrameLayout frameLayout;
    private WindowManager windowManager;
    private boolean openClose = false;

    public HeadLayer(Context Context) {
        super(Context);

        context = Context;
        frameLayout = new FrameLayout(context);

        addToWindowManager();
        //createOnTouchEvent();

    }

    private void addToWindowManager() {

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, this is the commented out one
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,// allows you to use the back button properly
                PixelFormat.TRANSLUCENT);
      // params.flags = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.gravity = Gravity.LEFT;



        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(frameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
        layoutInflater.inflate(R.layout.head, frameLayout);
        //final ImageView imageView = (ImageView) mFrameLayout.findViewById(R.id.imageView);

        // add a click listener to the image view
        final ImageView imageView = (ImageView) frameLayout.findViewById(R.id.imageView);
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
                        windowManager.updateViewLayout(frameLayout, params);
                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        long clickDuration = System.currentTimeMillis() - startClickTime;

                        if (clickDuration < MAX_CLICK_DURATION) {
                            //click event has occurred
                            if (!openClose) {
                                Intent intent = new Intent(getContext(), FloatingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears activity to open the new small activity
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getContext().startActivity(intent);

                                openClose = true;
                            } else {
                                FloatingActivity.act.finish();
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
        windowManager.removeView(frameLayout);
    }
}


