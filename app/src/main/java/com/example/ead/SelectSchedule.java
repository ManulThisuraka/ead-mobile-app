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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ead.constants.CommonMethods;
import com.example.ead.constants.Constants;
import com.example.ead.constants.HttpsTrustManager;
import com.example.ead.models.CardModel;
import com.example.ead.models.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectSchedule extends AppCompatActivity {
    private RecyclerView sList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Schedule> ScheduleList;
    String nic;
    private RecyclerView.Adapter adapter;
    Button Reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_schedule);

        Reserve = findViewById(R.id.BTNReserve);

        sList = findViewById(R.id.Srecycler);

        ScheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter(getApplicationContext(),ScheduleList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(sList.getContext(), linearLayoutManager.getOrientation());

        sList.setHasFixedSize(true);
        sList.setLayoutManager(linearLayoutManager);
        sList.addItemDecoration(dividerItemDecoration);
        sList.setAdapter(adapter);

        //back button
        ImageView btnBack = findViewById(R.id.left);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SelectSchedule.this,Dashboard.class);
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

        //volly request call
        String URL1 = Constants.BASE_URL + "/api/TrainSchedule/getAll";
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
                        if(jsonObject.getInt("status") == 0) {
                            Schedule CreatedSchedule = new Schedule();
                            CreatedSchedule.setScheduleDate(CommonMethods.convertToDate(jsonObject.getString("scheduleDate")));
                            CreatedSchedule.setDeparture(jsonObject.getString("departure"));
                            CreatedSchedule.setDestination(jsonObject.getString("destination"));
                            CreatedSchedule.setStartTime(jsonObject.getString("startTime"));
                            CreatedSchedule.setAvailableSeatCount(jsonObject.getInt("availableSeatCount"));
                            CreatedSchedule.set_id(jsonObject.getString("_id"));
                            ScheduleList.add(CreatedSchedule);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                final String message = ScheduleList.toString();
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
}