package com.example.fraser.floatingbuttonprototype.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setUpDB();
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


    public void setUpDB() {
        authenticationDatabase = new DatabaseHelper(this);
        db = authenticationDatabase.getReadableDatabase();

        String getImageIDFromCategory = "SELECT * FROM IMAGE_NAMES WHERE CATEGORY = ";
        targetCursor = db.rawQuery(getImageIDFromCategory+"'Target'", null);
        authenticatorCursor = db.rawQuery(getImageIDFromCategory + "'Authenticator'", null);
        emotionCursor = db.rawQuery(getImageIDFromCategory + "'Emotion'", null);

        targetList = (ListView) findViewById(R.id.targetListViewFloatingActivity);
        authenticatorList = (ListView) findViewById(R.id.authenListViewFloatingActivity);
        emotionList = (ListView) findViewById(R.id.emoListViewFloatingActivity);

        targetList.setAdapter(new CustomImageListAdapter(this,targetCursor));
        authenticatorList.setAdapter(new CustomImageListAdapter(this,authenticatorCursor));
        emotionList.setAdapter(new CustomImageListAdapter(this,emotionCursor));
    }
    
    public void close(View v){
        finish();
    }
}
