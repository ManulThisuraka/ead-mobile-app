package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import com.example.ead.models.CardModel;
import com.example.ead.models.CustomJsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    EditText Vnic, Vname, Vemail, Vpassword, Vpassword2;
    int Vrole;
    String Sname, Semail, Spassword, Snic;
    Button BTNupdate, BTNdeactivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Restore the values from the saved state
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Snic = preferences.getString("NIC", "");

        Vnic = findViewById(R.id.Tnic);
        Vname = findViewById(R.id.Tname);
        Vemail = findViewById(R.id.Temail);
        Vpassword = findViewById(R.id.Tpassword);
        Vpassword2 = findViewById(R.id.Tconfirm);

        Log.d("Loading JSON", "pago"+Sname);
        Log.d("Loading JSON", "pago"+Semail);
        Log.d("Loading JSON", "pago"+Spassword);

        try {
            Vnic.setText(Snic);
        }catch (Exception e){
        e.printStackTrace();
        }

        //Back Button
        ImageView btnBack = findViewById(R.id.left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(Profile.this,Dashboard.class);
                startActivity(goBack);
            }
        });

        // Update button
        BTNupdate = findViewById(R.id.updateBtn);
        BTNupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UpdateProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Deactivate button
//        BTNdeactivate = findViewById(R.id.deactivateBtn);
//        BTNdeactivate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    DeactivateProfile();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        BTNdeactivate = findViewById(R.id.deactivateBtn);
        BTNdeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);

                // Set the title and message
                builder.setTitle("Deactivate Account");
                builder.setMessage("Are you sure? If you deactivate your account, you have to contact your traveler agent to activate your account again.");

                // Add a positive button (OK)
                builder.setPositiveButton("Deactivate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            DeactivateProfile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Add a negative button (Cancel)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog if the user cancels
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //get data form nic and set
        loadData();

    }
    // Helper function to validate Email
    public boolean isValidEmail(String email) {
        // You can use a regular expression to validate email format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    // Function to validate Password
    public boolean isValidPassword(String password, String confirmPassword) {
        return password.length() >= 8 && password.equals(confirmPassword);
    }

    public void loadData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();


        String URL1 = Constants.BASE_URL + "/api/User/get/"+Snic;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Sname = response.getString("name");
                    Semail = response.getString("email");
                    Spassword = response.getString("password");
                    Vname.setText(Sname);
                    Vemail.setText(Semail);
                    Vpassword.setText(Spassword);
                    Vpassword2.setText(Spassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
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
    public void UpdateProfile() throws JSONException {
        if (TextUtils.isEmpty(Vname.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the Name", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(Vemail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(Vpassword.getText().toString(), Vpassword2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.show();

            HttpsTrustManager.allowAllSSL();
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", Vname.getText().toString());
            jsonObject.put("email", Vemail.getText().toString());
            jsonObject.put("password", Vpassword.getText().toString());

            final String mRequestBody = jsonObject.toString();
            Log.d("Reservation JSON", mRequestBody);

            RequestQueue mRequestQueue = Volley.newRequestQueue(this);

            String updateURL = Constants.BASE_URL + "/api/User/updateuser/" + Snic;

            CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.PATCH,
                    updateURL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Your Profile Updated Successfully", Toast.LENGTH_SHORT).show();
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

    public void DeactivateProfile() throws JSONException{
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deactivating...");
        progressDialog.show();

        HttpsTrustManager.allowAllSSL();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("password", Vpassword.getText().toString());
        jsonObject.put("status", 1);

        final String mRequestBody = jsonObject.toString();
        Log.d("Reservation JSON", mRequestBody);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String updateURL = Constants.BASE_URL + "/api/User/updateuser/" +Snic;

        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.PATCH,
                updateURL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Your Profile Deactivated Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
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