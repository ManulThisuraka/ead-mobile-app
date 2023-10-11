package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.ead.constants.Constants;
import com.example.ead.constants.HttpsTrustManager;
import com.example.ead.models.CardModel;
import com.example.ead.models.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActiveReservations extends AppCompatActivity {

    private RecyclerView rList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<CardModel> ReservationList;
    private RecyclerView.Adapter adapter;
    CardModel CreatedReservation = new CardModel();
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

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();

        String id = "987654123V";
        String URL1 = Constants.BASE_URL + "/api/Reservation/get/"+id;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                URL1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray objectArray = null;
                try {
                    objectArray = new JSONArray(response.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < objectArray.length(); i++) {
                    try {
                        JSONObject jsonObject = objectArray.getJSONObject(i);
                        if(jsonObject.getInt("status") == 0) {
                            CreatedReservation.setDate(jsonObject.getString("reservationDate"));
                            CreatedReservation.setCount(jsonObject.getInt("reserveCount"));
                            getTrainScheduleDetails(jsonObject.getString("trainScheduleid"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
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
        requestQueue.add(jsonArrayRequest);
    }

    private void getTrainScheduleDetails(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //String id = "";
        String URL2 = Constants.BASE_URL + "/api/TrainSchedule/getById/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.getString("data"));
                            CreatedReservation.setFrom(jsonObject.getString("departure"));
                            CreatedReservation.setTo(jsonObject.getString("destination"));
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