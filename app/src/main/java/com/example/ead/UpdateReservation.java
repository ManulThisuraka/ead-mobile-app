package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.example.ead.constants.HttpsTrustManager;
import com.example.ead.models.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateReservation extends AppCompatActivity {
    EditText Vdate, Vfrom, Vto, Vschedule, Vcount;
    int Scount, Sstatus;
    String Sdate, Sfrom, Sto, Sschedule, SresId, StsID, Snic, ScAt, SuAt;
    Button BTNupdate, BTNdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);
        Vdate = findViewById(R.id.Cdate);
        Vfrom = findViewById(R.id.Cfrom);
        Vto = findViewById(R.id.Cto);
        Vschedule = findViewById(R.id.Cschedule);
        Vcount = findViewById(R.id.Ccount);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SresId = extras.getString("ReservationId");
            StsID = extras.getString("ReservationTSId");
            Snic = extras.getString("ReservationNIC");
            ScAt = extras.getString("ReservationCreatedAt");
            SuAt = extras.getString("ReservationUpdatedAt");
            Sdate = extras.getString("ReservationDate");
            Scount = extras.getInt("ReservationCount");
            Sstatus = extras.getInt("status");
            Sfrom = extras.getString("ReservationFrom");
            Sto = extras.getString("ReservationTo");
            Sschedule = extras.getString("ReservationTime");
            //The key argument here must match that used in the other activity
        }

            Vdate.setText(Sdate);
            Vfrom.setText(Sfrom);
            Vto.setText(Sto);
            Vschedule.setText(Sschedule);
            Vcount.setText(String.valueOf(Scount));


        //Back Button
        ImageView btnBack = findViewById(R.id.left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(UpdateReservation.this,ActiveReservations.class);
                startActivity(goBack);
            }
        });

        // Update button
        BTNupdate = findViewById(R.id.BtnUpdate);
        BTNupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UpdateReservation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Delete button
        BTNdelete = findViewById(R.id.BtnDelete);
        BTNdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DeleteReservation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void UpdateReservation() throws JSONException {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("_id", SresId);
        jsonObject.put("trainScheduleid", StsID);
        jsonObject.put("nic", Snic);
        jsonObject.put("createdAt", ScAt);
        jsonObject.put("updatedAt", SuAt);
        jsonObject.put("reservationDate", Sdate);
        jsonObject.put("reserveCount", Vcount.getText().toString());
        jsonObject.put("status", Sstatus);

        final String mRequestBody = jsonObject.toString();
        Log.d("Reservation JSON", mRequestBody);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String id = SresId;
        String updateURL = Constants.BASE_URL + "/api/Reservation/update/" +id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
                updateURL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Your Reservation Updated Successfully", Toast.LENGTH_SHORT).show();
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

    public void DeleteReservation() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String id = SresId;
        String updateURL = Constants.BASE_URL + "/api/Reservation/delete/" +id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                updateURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Your Reservation Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateReservation.this, ActiveReservations.class);
                startActivity(intent);
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