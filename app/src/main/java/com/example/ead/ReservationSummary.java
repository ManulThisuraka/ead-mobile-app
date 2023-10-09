package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ead.constants.Constants;
import com.example.ead.models.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationSummary extends AppCompatActivity {

    Button CBtnBack, CBtnConfirm;
    EditText Edate, Efrom, Eto, Eschedule, Ecount;
    String date, NIC, scheduleID, count;

    Reservation reservationobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_summary);

        //back button
        ImageView btnBack = findViewById(R.id.left);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(ReservationSummary.this,CreateReservation.class);
                startActivity(goBack);
            }
        });

        // Back button
        CBtnBack = findViewById(R.id.BtnBack);
        CBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ReservationSummary.this, CreateReservation.class);
                startActivity(home);
            }
        });

        // Confirm button
        CBtnConfirm = findViewById(R.id.BtnConfirm);
        CBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservationAPI(date,NIC,scheduleID,count);
            }
        });

        Edate = findViewById(R.id.Cdate);
        Efrom = findViewById(R.id.Cfrom);
        Eto = findViewById(R.id.Cto);
        Eschedule = findViewById(R.id.Cschedule);
        Ecount = findViewById(R.id.Ccount);

        CBtnConfirm = findViewById(R.id.BtnConfirm);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Edate.setText(extras.getString("Rdate"));
            Efrom.setText(extras.getString("Rfrom"));
            Eto.setText(extras.getString("Rto"));
            Eschedule.setText(extras.getString("Rschedule"));
            Ecount.setText(extras.getString("Rcount"));
            //The key argument here must match that used in the other activity

            date = extras.getString("Rdate");
            NIC = "987654123V";
            scheduleID = "Schedule 1";
            count = extras.getString("Rcount");
        }
    }

    private void addReservationAPI(String date, String NIC, String scheduleID, String count) {
            JSONObject jsonObject = new JSONObject();
            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
            String URL = Constants.BASE_URL + "/api/Reservation/create";
            //final Queue[] joinedQueue = new Queue[1];

        try {
            jsonObject.put("trainScheduleid", scheduleID);
            jsonObject.put("nic", NIC);
            jsonObject.put("reservationDate", date);
            jsonObject.put("reserveCount", count);

            final String mRequestBody = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println("Join Queue : "+response);
                        try {
//                            JSONArray queueArray = new JSONArray(response.getString("queue"));
//                            JSONObject singleObject = queueArray.getJSONObject(queueArray.length()-1);
//                            joinedQueue[0] = new Queue(
//                                    singleObject.getString("id"),
//                                    singleObject.getString("vehicleType"),
//                                    singleObject.getString("vehicleOwner"),
//                                    singleObject.getString("fuelType"),
//                                    singleObject.getString("stationsId"),
//                                    singleObject.getString("arivalTime"),
//                                    singleObject.getString("departTime")
//                            );
//                            System.out.println("Joined Queue Id : "+joinedQueue[0].getId());
//                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                            Date date = new Date();
                            Toast.makeText(getApplicationContext(), "Your Reservation Submitted Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ReservationSummary.this, Dashboard.class);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}