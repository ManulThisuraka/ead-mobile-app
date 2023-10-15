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
import android.widget.ImageButton;
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

public class Registation extends AppCompatActivity {
    EditText Rname, Rnic, Remail, Rpassword, RCpassword;
    int role = 0;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        Rname = findViewById(R.id.Name);
        Rnic = findViewById(R.id.NIC);
        Remail = findViewById(R.id.Email);
        Rpassword = findViewById(R.id.password);
        RCpassword = findViewById(R.id.password2);

        Register = findViewById(R.id.SignUpBtn);
        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                try {
                    Register();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Helper function to validate NIC
    public boolean isValidNic(String nic) {
        // Check if NIC is either 10 characters long and ends with 'V' or 'v' or is a 12-digit number
        return nic.matches("[0-9]{12}|[0-9]{9}[vV]");
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

    //Redirect to next page
    public void Register() throws JSONException {

        if (TextUtils.isEmpty(Rname.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the Name", Toast.LENGTH_SHORT).show();
        } else if (!isValidNic(Rnic.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter a valid NIC", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(Remail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(Rpassword.getText().toString(), RCpassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            HttpsTrustManager.allowAllSSL();
            RequestQueue mRequestQueue = Volley.newRequestQueue(this);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("nic", Rnic.getText().toString() );
            jsonObject.put("name", Rname.getText().toString());
            jsonObject.put("email", Remail.getText().toString());
            jsonObject.put("role",0 );
            jsonObject.put("password", Rpassword.getText().toString());

            final String mRequestBody = jsonObject.toString();
            Log.d("Register JSON", mRequestBody);

            String URL = Constants.BASE_URL + "/api/User/register";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "Account Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(Registation.this,Login.class);
                    progressDialog.dismiss();
                    startActivity(intent2);
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