package com.example.fraser.floatingbuttonprototype.Model;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.fraser.floatingbuttonprototype.Activities.MainActivity;
import com.example.fraser.floatingbuttonprototype.Activities.HeadLayer;
import com.example.fraser.floatingbuttonprototype.R;


/**
 *
 * class makes use of code from this tutorial
 *
 * — example of a simple use of a window head
 * https://codingshark.wordpress.com/2015/02/01/how-to-draw-on-top-of-other-applications/?utm_source=androiddevdigest
 * https://github.com/mollyIV/ChatHeads
 */

public class HeadService extends Service {
    private HeadLayer mHeadLayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logServiceStarted();

        initHeadLayer();

        PendingIntent pendingIntent = createPendingIntent();
        Notification notification = createNotification(pendingIntent);

        startForeground(111,notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        destroyHeadLayer();
        stopForeground(true);
        logServiceEnded();
    }

    private void initHeadLayer() {
        mHeadLayer = new HeadLayer(this);
    }

    private void destroyHeadLayer() {
        mHeadLayer.destroy();
        mHeadLayer = null;
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private Notification createNotification(PendingIntent intent) {
        return new Notification.Builder(this)
                .setContentTitle(getText(R.string.notificationTitle))
                .setContentText(getText(R.string.notificationText))
                .setSmallIcon(R.drawable.padlock)
                .setContentIntent(intent)
                .build();
    }

    private void logServiceStarted() {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }

    private void logServiceEnded() {
        Toast.makeText(this, "Service ended", Toast.LENGTH_SHORT).show();
    }
}
