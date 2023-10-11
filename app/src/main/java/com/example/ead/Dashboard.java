package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {
Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageButton btnNew = findViewById(R.id.btnNew);
        ImageButton btnProfile = findViewById(R.id.btnProfile);
        ImageButton btnExist = findViewById(R.id.btnExist);
//        ImageButton btnInventory = findViewById(R.id.btnInventory);
//        Button logout = findViewById(R.id.btnLogout1);

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {// User is signed in} else {    // No user is signed in}
//                    FirebaseAuth.getInstance().signOut();
//                    Intent intent = new Intent(HomeDashboard.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//            }});


        btnNew.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Dashboard.this, CreateReservation.class);
                intent.putExtra("clearPreferences", true);
                startActivity(intent);
            }
        });

        // Logout button
        logout = findViewById(R.id.btnLogout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//
//        btnInventory.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View view){
//                Intent intent = new Intent(HomeDashboard.this, AddInventoryFirst.class);
//                startActivity(intent);
//            }
//        });
    }
}