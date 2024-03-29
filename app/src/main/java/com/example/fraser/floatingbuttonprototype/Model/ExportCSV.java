package com.example.fraser.floatingbuttonprototype.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Fraser on 09/01/2017.
 */

public class ExportCSV {

    private ArrayList<Authentication> authenList;

    public ExportCSV(ArrayList<Authentication> authenList) {
        this.authenList = authenList;
    }

    public File generateCSV(Context context) {
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/AuthenticationDiary/Floating/floatingButtonPrototypeData.csv");
            File fileDir = new File(Environment.getExternalStorageDirectory().getPath()+"/AuthenticationDiary/Floating");
            if(!fileDir.exists()){
                fileDir.mkdirs();
                Log.e("fileExists","False");
            }
            Log.e("fileExists","true");
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            String [] header = new String[]{"_id","Target","Authenticator","Emotion","Timestamp","Location","Comments"};
            writer.writeNext(header);
            for (Authentication s : authenList) {
                writer.writeNext(createCSVLine(s));
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Cannot generate CSV File", Toast.LENGTH_LONG).show();
        }
        return file;
    }

    public String[] createCSVLine(Authentication s) {
        return new String[]{Long.toString(s.id), s.deviceID, s.authenticatorID, s.emotionID, s.timeStamp, s.location, s.comments};
    }
}

