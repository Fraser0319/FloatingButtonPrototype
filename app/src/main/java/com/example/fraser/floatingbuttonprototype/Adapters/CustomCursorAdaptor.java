package com.example.fraser.floatingbuttonprototype.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fraser.floatingbuttonprototype.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fraser on 17/12/2016.
 */

public class CustomCursorAdaptor extends CursorAdapter {


    public CustomCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.authentication_list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView deviceImage = (ImageView) view.findViewById(R.id.deviceImage);
        ImageView authenticationImage = (ImageView) view.findViewById(R.id.authenticationImage);
        ImageView emotionImage = (ImageView) view.findViewById(R.id.emotionImage);
        ImageView commentImage = (ImageView) view.findViewById(R.id.commentImage);
        ImageView locationImage = (ImageView) view.findViewById(R.id.locationImage);

        int device = cursor.getInt(cursor.getColumnIndex("DEVICE_RESOURCE_ID"));
        int authentication = cursor.getInt(cursor.getColumnIndex("AUTHENTICATOR_RESOURCE_ID"));
        int emotion = cursor.getInt(cursor.getColumnIndex("EMOTION_RESOURCE_ID"));
        String comment = cursor.getString(cursor.getColumnIndex("COMMENTS"));
        String location = cursor.getString(cursor.getColumnIndex("LOCATION"));

        if (comment != null) {
            if (!comment.isEmpty()) {
                commentImage.setImageResource(R.drawable.chat);
            } else {
                commentImage.setImageResource(R.drawable.chat_empty);
            }
        } else {
            commentImage.setImageResource(R.drawable.chat_empty);
        }

        if (location != null) {
            if (!location.isEmpty()) {
                locationImage.setImageResource(R.drawable.location);
            } else {
                locationImage.setImageResource(R.drawable.location_empty);
            }
        } else {
            locationImage.setImageResource(R.drawable.location_empty);
        }

        try {
            deviceImage.setImageResource(device);
            authenticationImage.setImageResource(authentication);
            emotionImage.setImageResource(emotion);
        } catch (Exception e) {
            Toast.makeText(context, "Cant set image resource id", Toast.LENGTH_SHORT).show();
        }

        TextView timeStamp = (TextView) view.findViewById(R.id.timeStamp);
        String dateTime = cursor.getString(cursor.getColumnIndex("ADDED_ON"));

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(dateTime);

            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String newDateTime = sd.format(d);
            timeStamp.setText(newDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
