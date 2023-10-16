package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
Button logout;
    String nic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageButton btnNew = findViewById(R.id.btnNew);
        ImageButton btnProfile = findViewById(R.id.btnProfile);
        ImageButton btnExist = findViewById(R.id.btnExist);
        ImageButton btnHistory = findViewById(R.id.btnInventory);

        // Restore the values from the saved state
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nic = preferences.getString("NIC", "");

        btnNew.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Dashboard.this, SelectSchedule.class);
                //intent.putExtra("clearPreferences", true);
                startActivity(intent);
            }
        });

        // Logout button
        logout = findViewById(R.id.btnLogout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Clear the preferences
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear(); // Clear all preferences
                editor.apply();

                Intent home = new Intent(Dashboard.this, Login.class);
                startActivity(home);
            }
        });

        //Profile Navigation
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
            }
        });

        btnExist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Dashboard.this, ActiveReservations.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Dashboard.this, ReservationHistory.class);
                startActivity(intent);
            }
        });

    }
}