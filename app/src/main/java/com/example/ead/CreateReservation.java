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

public class CreateReservation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText Vdate,Vcount;
    String Vto,Vfrom,Vschedule;
    private DatePickerDialog picker;
    private Spinner todrpdwn, fromdrpdwn, scheduledrpdwn;
    String date, from, to, schedule, count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        //back button
        ImageView btnBack = findViewById(R.id.left);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(CreateReservation.this,Dashboard.class);
                startActivity(goBack);
            }
        });

        Vdate = findViewById(R.id.Cdate);
//        Vto = findViewById(R.id.Etodrpdwn);
//        Vfrom = findViewById(R.id.Efromdrpdwn);
//        Vschedule = findViewById(R.id.Escheduledrpdwn);
        Vcount = findViewById(R.id.Ccount);

        //From Dropdown
        fromdrpdwn = (Spinner) findViewById(R.id.Cfrom);
        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromdrpdwn.setAdapter(adapterFrom);
        fromdrpdwn.setOnItemSelectedListener(this);

        fromdrpdwn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Vfrom = (String) arg0.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //To Dropdown
        todrpdwn = (Spinner) findViewById(R.id.Cto);
        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todrpdwn.setAdapter(adapterTo);
        todrpdwn.setOnItemSelectedListener(this);

        todrpdwn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Vto = (String) arg0.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //Schedule Dropdown
        scheduledrpdwn = (Spinner) findViewById(R.id.Cschedule);
        ArrayAdapter<CharSequence> adapterSchedule = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapterSchedule.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduledrpdwn.setAdapter(adapterSchedule);
        scheduledrpdwn.setOnItemSelectedListener(this);

        scheduledrpdwn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Vschedule = (String) arg0.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //Date
        Vdate.setInputType(InputType.TYPE_NULL);
        Vdate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            //Date Picker Dialog
            picker = new DatePickerDialog(CreateReservation.this, (view1, year1, month1, dayOfMonth) -> Vdate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.show();

        });

        boolean clearPreferences = getIntent().getBooleanExtra("clearPreferences", false);

        if (clearPreferences) {
            // Clear the preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // Clear all preferences
            editor.apply();
        }

            //Save date when going to next page
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


            // Restore the values from the saved state
            Vdate.setText(preferences.getString("date", ""));
            fromdrpdwn.setSelection(preferences.getInt("fromIndex", 0));
            todrpdwn.setSelection(preferences.getInt("toIndex", 0));
            scheduledrpdwn.setSelection(preferences.getInt("scheduleIndex", 0));
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
        editor.putInt("fromIndex", fromdrpdwn.getSelectedItemPosition());
        editor.putInt("toIndex", todrpdwn.getSelectedItemPosition());
        editor.putInt("scheduleIndex", scheduledrpdwn.getSelectedItemPosition());
        editor.putString("count", Vcount.getText().toString());

        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Redirect to next page
    public void Reserve(View view) {

        if (TextUtils.isEmpty(Vdate.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the the Date", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Vfrom)) {
            Toast.makeText(getApplicationContext(), "Please enter the Departure", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Vto)) {
            Toast.makeText(getApplicationContext(), "Please enter the Destination", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Vschedule)) {
            Toast.makeText(getApplicationContext(), "Please select the schedule", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Vcount.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the number of reservations", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(getApplicationContext(), "Confirm reservation details", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this,ReservationSummary.class);
            intent2.putExtra("Rdate",Vdate.getText().toString());
            intent2.putExtra("Rfrom",Vfrom);
            intent2.putExtra("Rto",Vto);
            intent2.putExtra("Rschedule",Vschedule);
            intent2.putExtra("Rcount",Vcount.getText().toString());
            startActivity(intent2);
        }
    }
}