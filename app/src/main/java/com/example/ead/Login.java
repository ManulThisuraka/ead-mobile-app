package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ead.constants.Constants;
import com.example.ead.constants.HttpsTrustManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button Login;
    TextView Register;
    EditText Remail, Rpassword;
    String Rnic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Remail = findViewById(R.id.Lemail);
        Rpassword = findViewById(R.id.Lpassword);

        // Login button
        Login = findViewById(R.id.mLoginBtn);
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                try {
                    Login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent home = new Intent(Login.this, Dashboard.class);
//                startActivity(home);
//            }
//        });

        // Register button
        Register = findViewById(R.id.mRegisterTxt);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Login.this, Registation.class);
                startActivity(home);
            }
        });

        // Clear the preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Clear all preferences
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current values of your fields to SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NIC", Rnic);
        editor.apply();
    }

    //Redirect to next page
    public void Login() throws JSONException {

        if (TextUtils.isEmpty(Remail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Rpassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
        } else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("logging in...");
            progressDialog.show();

            HttpsTrustManager.allowAllSSL();
            RequestQueue mRequestQueue = Volley.newRequestQueue(this);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("email", Remail.getText().toString() );
            jsonObject.put("password", Rpassword.getText().toString());

            final String mRequestBody = jsonObject.toString();
            Log.d("Login JSON", mRequestBody);

            String URL = Constants.BASE_URL + "/api/User/login";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.getString("value"));
                        if(jsonObject1.getString("success")=="true") {
                            JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("data"));
                            Rnic = jsonObject2.getString("nic");
                            Intent home = new Intent(Login.this, Dashboard.class);
                            startActivity(home);
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }catch (JSONException e) {
                    e.printStackTrace();
                }
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
}