package com.example.ead;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ead.constants.CommonMethods;
import com.example.ead.constants.Constants;
import com.example.ead.constants.HttpsTrustManager;
import com.example.ead.models.CardModel;
import com.example.ead.models.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActiveReservations extends AppCompatActivity {

    private RecyclerView rList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<CardModel> ReservationList;
    String nic;
    private RecyclerView.Adapter adapter;
    Button Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_reservations);

        Edit = findViewById(R.id.BTNedit);

        rList = findViewById(R.id.VMrecycler);

        ReservationList = new ArrayList<>();
        adapter = new ReservationsAdapter(getApplicationContext(),ReservationList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rList.getContext(), linearLayoutManager.getOrientation());

        rList.setHasFixedSize(true);
        rList.setLayoutManager(linearLayoutManager);
        rList.addItemDecoration(dividerItemDecoration);
        rList.setAdapter(adapter);

        //back button
        ImageView btnBack = findViewById(R.id.left);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(ActiveReservations.this,Dashboard.class);
                startActivity(goBack);
            }
        });

        // Restore the values from the saved state
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nic = preferences.getString("NIC", "");

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();

        //String id = "987654123V";
        String URL1 = Constants.BASE_URL + "/api/Reservation/get/"+nic;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL1, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                JSONArray objectArray = null;
                final String mRequestBody = response.toString();
                Log.d("JSON ARRAY", mRequestBody);

                try {
                    objectArray = new JSONArray(response.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < objectArray.length(); i++) {
                    try {
                        JSONObject jsonObject = objectArray.getJSONObject(i);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date todayDate = new Date();
                        String todayDateString = dateFormat.format(todayDate);
                        String Rdate = CommonMethods.convertToDateNormal(jsonObject.getString("reservationDate"));

                        if(Rdate.compareTo(todayDateString) >= 0) {
                            CardModel CreatedReservation = new CardModel();
                            CreatedReservation.setId(jsonObject.getString("_id"));
                            CreatedReservation.setDate(CommonMethods.convertToDateNormal(jsonObject.getString("reservationDate")));
//                            CreatedReservation.setTrainScheduleid(jsonObject.getString("trainScheduleid"));
//                            CreatedReservation.setNic(jsonObject.getString("nic"));
//                            CreatedReservation.setCreatedAt(jsonObject.getString("createdAt"));
//                            CreatedReservation.setUpdatedAt(jsonObject.getString("updatedAt"));
                            CreatedReservation.setCount(jsonObject.getInt("reserveCount"));
//                            CreatedReservation.setStatus(jsonObject.getInt("status"));
                            getTrainScheduleDetails(jsonObject.getString("trainScheduleid"),CreatedReservation);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                final String message = ReservationList.toString();
                Log.d("List ARRAY", message);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void getTrainScheduleDetails(String id, CardModel CreatedReservation) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String URL2 = Constants.BASE_URL + "/api/TrainSchedule/getById/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.getString("data"));
                        CreatedReservation.setFrom(jsonObject.getString("departure"));
                        CreatedReservation.setTo(jsonObject.getString("destination"));
                        CreatedReservation.setTime(jsonObject.getString("startTime"));
                        ReservationList.add(CreatedReservation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}