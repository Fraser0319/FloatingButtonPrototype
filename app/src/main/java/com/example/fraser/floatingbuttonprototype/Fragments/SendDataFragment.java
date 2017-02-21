package com.example.fraser.floatingbuttonprototype.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fraser.floatingbuttonprototype.Model.DatabaseHelper;
import com.example.fraser.floatingbuttonprototype.Model.ExportCSV;
import com.example.fraser.floatingbuttonprototype.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendDataFragment extends Fragment {

    private ExportCSV eCSV;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private SQLiteOpenHelper authenticationDatabase;

    public SendDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewInflator = inflater.inflate(R.layout.fragment_send_data, container, false);
        setupSendButtonListener(viewInflator);
        setUpDB();
        //viewCSV(viewInflator);
        return viewInflator;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }

    public void setUpDB() {
        authenticationDatabase = new DatabaseHelper(getActivity());
        db = authenticationDatabase.getReadableDatabase();
        dbHelper = new DatabaseHelper(getActivity());
    }

    public void setupSendButtonListener(View v) {
        Button sendButton = (Button) v.findViewById(R.id.sendData);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataAlertDialog(getView());
            }
        });
    }

//    public void viewCSV(View v) {
//        Button sendButton = (Button) v.findViewById(R.id.viewCSVButton);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("onClick","viewCSV");
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.actvity_main, new ViewCSVFragment());
//                ft.addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
//            }
//        });
//    }

    public boolean checkValidEmail(CharSequence emailInput) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
    }

    public void sendDataAlertDialog(final View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you agree to send your recored data ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText inputEmail = (EditText) v.findViewById(R.id.inputEmailAddress);
                        String email = inputEmail.getText().toString();

                        if (checkValidEmail(email)) {
                            // send data !
                            final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Collected Data From Authentication Diary");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "This the data collected from the study in a CSV file");

                            // send as email attachment
                            ArrayList<Uri> uris = new ArrayList<Uri>();
                            eCSV = new ExportCSV(dbHelper.getAllAuthentications(db));
                            uris.add(Uri.fromFile(eCSV.generateCSV(getActivity())));
                            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                            startActivityForResult(Intent.createChooser(emailIntent, "Sending multiple attachment"), 123);
                        } else {
                            Toast.makeText(getActivity(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You must accept in order to complete the study", Toast.LENGTH_LONG).show();
                    }
                });
        builder.create();
        builder.show();
    }

}