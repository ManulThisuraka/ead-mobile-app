package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ead.constants.Constants;
import com.example.ead.constants.HttpsTrustManager;
import com.example.ead.models.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ReservationSummary extends AppCompatActivity {

    Button CBtnBack, CBtnConfirm;
    EditText Edate, Efrom, Eto, Eschedule, Ecount;
    String date;
    String NIC;
    String scheduleID;
    int count;

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
                try {
                    addReservationAPI(date,NIC,scheduleID,count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

            //date = extras.getString("Rdate");6523caa7f73de7a9bfb57636
            date = "2023-10-09T17:22:35.391Z";
            NIC = "987654123V";
            scheduleID = "652555fe7a723656e544007e";
            count = 2;
        }
    }

    private void addReservationAPI(String date, String NIC, String scheduleID, int count) throws JSONException {

            date = "2023-10-09T17:22:35.391Z";
            NIC = "987654123V";
            scheduleID = "652555fe7a723656e544007e";
            count = 2;

            HttpsTrustManager.allowAllSSL();
            JSONObject jsonObject = new JSONObject();
            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
            String URL = Constants.BASE_URL + "/api/Reservation/create";
            //final Queue[] joinedQueue = new Queue[1];

            jsonObject.put("trainScheduleId", scheduleID);
            jsonObject.put("nic", NIC);
            jsonObject.put("reservationDate", date);
            jsonObject.put("reserveCount", count);

            final String mRequestBody = jsonObject.toString();
            Log.d("Request JSON", mRequestBody);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Your Reservation Submitted Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ReservationSummary.this, Dashboard.class);
                            startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                // Handle the error here
                String errorMessage = error.getMessage();
                Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                Log.e("LOG_VOLLEY_ERROR_1", error.toString());
                Log.e("LOG_VOLLEY_ERROR_2", String.valueOf(error.networkResponse));

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);

                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.e("LOG_VOLLEY_ERROR_A", statusCode);
                    Log.e("LOG_VOLLEY_ERROR_B", body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                return param;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(jsonObjReq);
    }
}