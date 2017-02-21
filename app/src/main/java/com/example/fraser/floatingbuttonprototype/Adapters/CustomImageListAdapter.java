package com.example.fraser.floatingbuttonprototype.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.fraser.floatingbuttonprototype.R;

/**
 * Created by Fraser on 21/02/2017.
 */

public class CustomImageListAdapter extends CursorAdapter {


    public CustomImageListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list_view_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView iconImage = (ImageView) view.findViewById(R.id.imageViewFloatingActivity);
        int icon = cursor.getInt(cursor.getColumnIndex("IMAGE_ID"));
        Log.e("imageID",icon+"");
        iconImage.setImageResource(icon);

    }

}
