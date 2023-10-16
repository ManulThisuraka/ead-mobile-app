package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.Calendar;

public class CreateReservation extends AppCompatActivity{

    EditText Vdate,Vcount,Vto,Vfrom,Vschedule;
    private DatePickerDialog picker;
    String Sid;
    int Acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        //back button
        ImageView btnBack = findViewById(R.id.left);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(CreateReservation.this,SelectSchedule.class);
                startActivity(goBack);
            }
        });

        Vdate = findViewById(R.id.Rdate);
        Vto = findViewById(R.id.Rto);
        Vfrom = findViewById(R.id.Rfrom);
        Vschedule = findViewById(R.id.Rschedule);
        Vcount = findViewById(R.id.Rcount);

            //Save date when going to next page
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Vdate.setText(extras.getString("Date"));
            Vfrom.setText(extras.getString("From"));
            Vto.setText(extras.getString("To"));
            Vschedule.setText(extras.getString("Time"));
            Sid = extras.getString("Sid");
            Acount = extras.getInt("Seats");
            //The key argument here must match that used in the other activity
        }

        boolean clearP = getIntent().getBooleanExtra("clearPreferences", false);
            if (clearP) {
                // Clear specified preferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("count");
                editor.apply();
            }

        // Restore the values from the saved state
        Vdate.setText(preferences.getString("date", ""));
        Vfrom.setText(preferences.getString("from", ""));
        Vto.setText(preferences.getString("to", ""));
        Vschedule.setText(preferences.getString("time", ""));
        Vcount.setText(preferences.getString("count", ""));
    }

    //Save date when going to next page
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current values of your fields to SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("date", Vdate.getText().toString());
        editor.putString("from", Vfrom.getText().toString());
        editor.putString("to", Vto.getText().toString());
        editor.putString("time", Vschedule.getText().toString());
        editor.putString("count", Vcount.getText().toString());

        editor.apply();
    }

    //Redirect to next page
    public void Reserve(View view) {

        String countText = Vcount.getText().toString();
        if (TextUtils.isEmpty(countText)) {
            Toast.makeText(getApplicationContext(), "Please enter the number of reservations", Toast.LENGTH_SHORT).show();
        } else {
            int reservationCount = Integer.parseInt(countText);
            if (reservationCount > 4) {
                Toast.makeText(getApplicationContext(), "You cannot reserve more than 4 seats per reservation", Toast.LENGTH_SHORT).show();
            } else if (reservationCount > Acount) {
                Toast.makeText(getApplicationContext(), "Sorry, Available seats are not sufficient", Toast.LENGTH_SHORT).show();
            } else {

            Toast.makeText(getApplicationContext(), "Confirm reservation details", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this,ReservationSummary.class);
            intent2.putExtra("Rdate",Vdate.getText().toString());
            intent2.putExtra("Rfrom",Vfrom.getText().toString());
            intent2.putExtra("Rto",Vto.getText().toString());
            intent2.putExtra("Rschedule",Vschedule.getText().toString());
            intent2.putExtra("Rcount",Vcount.getText().toString());
            intent2.putExtra("Rsid",Sid);
            startActivity(intent2);
        }
    }
}}