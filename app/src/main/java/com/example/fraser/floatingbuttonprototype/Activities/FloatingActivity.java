package com.example.fraser.floatingbuttonprototype.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fraser.floatingbuttonprototype.Adapters.CustomImageListAdapter;
import com.example.fraser.floatingbuttonprototype.Model.DatabaseHelper;
import com.example.fraser.floatingbuttonprototype.R;

public class FloatingActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor targetCursor;
    private Cursor authenticatorCursor;
    private Cursor emotionCursor;
    private DatabaseHelper dbHelper;
    private static ListView targetList;
    private static ListView authenticatorList;
    private static ListView emotionList;
    private static CustomImageListAdapter listAdapter;
    private SQLiteOpenHelper authenticationDatabase;
    public static FloatingActivity act;
    int targetImageId = -1;
    int authenImageId = -1;
    int emotionImageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setUpDB();
        setUpFloatingActvity();
        getSelectedItem();
        this.act = this;
    }


    public void setUpFloatingActvity() {
        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        int measuredWidth = size.x;
        int measuredHeight = size.y;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;

        // for managing small screen sizes
        if (measuredHeight <= 1776) {
            params.height = measuredHeight - 190;
        } else {
            params.height = measuredHeight - 500;
        }

        params.width = measuredWidth - 100;
        params.y = -200;
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        this.getWindow().setAttributes(params);
    }


    public void setUpDB() {
        authenticationDatabase = new DatabaseHelper(this);
        db = authenticationDatabase.getReadableDatabase();
        dbHelper = new DatabaseHelper(this);

        String getImageIDFromCategory = "SELECT * FROM IMAGE_NAMES WHERE CATEGORY = ";
        targetCursor = db.rawQuery(getImageIDFromCategory + "'Target'", null);
        authenticatorCursor = db.rawQuery(getImageIDFromCategory + "'Authenticator'", null);
        emotionCursor = db.rawQuery(getImageIDFromCategory + "'Emotion'", null);

        targetList = (ListView) findViewById(R.id.targetListViewFloatingActivity);
        authenticatorList = (ListView) findViewById(R.id.authenListViewFloatingActivity);
        emotionList = (ListView) findViewById(R.id.emoListViewFloatingActivity);

        targetList.setAdapter(new CustomImageListAdapter(getApplicationContext(), targetCursor));
        authenticatorList.setAdapter(new CustomImageListAdapter(this, authenticatorCursor));
        emotionList.setAdapter(new CustomImageListAdapter(this, emotionCursor));
    }


    public void getSelectedItem() {
        targetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("id", id + "");
                //use id to get the image name,id etc from the db as its the _id of that image in the image_names table.
                targetList.setSelection(position);
                targetImageId = dbHelper.getImageResourceID(db, id);
                Log.e("targetImageId",targetImageId+"");
            }
        });

        authenticatorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                authenticatorList.setSelection(position);
                authenImageId = dbHelper.getImageResourceID(db, id);
                Log.e("authenImageId",authenImageId+"");
            }
        });

        emotionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                emotionList.setSelection(position);
                emotionList.setSelected(true);
                emotionImageId = dbHelper.getImageResourceID(db, id);
                Log.e("emotionImageId",emotionImageId+"");
            }
        });
    }


    public void insertAuthentication(View v) {

        TextView locationText = (TextView) findViewById(R.id.locationEditText);
        TextView commentsText = (TextView) findViewById(R.id.commentsEditText);

        String location = locationText.getText().toString();
        String comments = commentsText.getText().toString();

        if (targetImageId != -1 && authenImageId != -1 && emotionImageId != -1) {
            dbHelper.insertAuthentication(db, targetImageId, authenImageId, emotionImageId, comments,location);
            Toast.makeText(this, "Authentication Added !", Toast.LENGTH_SHORT).show();
            locationText.setText("");
            commentsText.setText("");
            emotionList.setSelected(false);
            authenticatorList.setSelected(false);
            targetList.setSelected(false);
        } else {
            Toast.makeText(this, "Please set a Target, Authenticator and Emotion", Toast.LENGTH_SHORT).show();
        }
    }
}
