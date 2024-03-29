package com.example.fraser.floatingbuttonprototype.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fraser.floatingbuttonprototype.R;


/**
 * Created by Fraser on 09/02/2017.
 */

public class ListDBAdapter extends CursorAdapter {

    public ListDBAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listdb_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//        TextView imageID = (TextView) view.findViewById(R.id.imageID);
//        TextView imageName = (TextView) view.findViewById(R.id.imageName);
//        TextView cat = (TextView) view.findViewById(R.id.imageCategory);
//        //TextView authenID = (TextView) view.findViewById(R.id.imageAuthenID);
//        TextView _idTex = (TextView) view.findViewById(R.id.imageTableID);

        TextView imageID = (TextView) view.findViewById(R.id.imageID);
        TextView imageName = (TextView) view.findViewById(R.id.imageName);
        TextView cat = (TextView) view.findViewById(R.id.imageCategory);
        //TextView authenID = (TextView) view.findViewById(R.id.imageAuthenID);
        TextView _idTex = (TextView) view.findViewById(R.id.imageTableID);

        long _id = cursor.getLong(cursor.getColumnIndex("_id"));
//        int icon = cursor.getInt(cursor.getColumnIndex("IMAGE_ID"));
//        String name = cursor.getString(cursor.getColumnIndex("NAME"));
//        String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
        int device = cursor.getInt(cursor.getColumnIndex("DEVICE_RESOURCE_ID"));
        int authentication = cursor.getInt(cursor.getColumnIndex("AUTHENTICATOR_RESOURCE_ID"));
        int emotion = cursor.getInt(cursor.getColumnIndex("EMOTION_RESOURCE_ID"));
        String comment = cursor.getString(cursor.getColumnIndex("COMMENTS"));
        String location = cursor.getString(cursor.getColumnIndex("LOCATION"));
        //int authenIDtex = cursor.getInt(cursor.getColumnIndex("AUTHEN_ID"));


        imageID.setText("Device: "+device+"");
        imageName.setText("Authenticator: "+authentication);
        cat.setText("Emotion: "+emotion);
        //authenID.setText("AUTHEN_ID: "+authenIDtex+"");
        _idTex.setText("_id: "+_id+"");
    }
}
