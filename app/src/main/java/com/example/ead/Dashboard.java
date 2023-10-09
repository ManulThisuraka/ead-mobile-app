package com.example.ead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageButton btnNew = findViewById(R.id.btnNew);
//        ImageButton btnService = findViewById(R.id.btnService);
//        ImageButton btnFunds = findViewById(R.id.btnFunds);
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

//        btnService.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View view){
//                Intent intent = new Intent(HomeDashboard.this, AddVolunteering.class);
//                startActivity(intent);
//            }
//        });
//
//        btnFunds.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View view){
//                Intent intent = new Intent(HomeDashboard.this, AddFundsFirst.class);
//                startActivity(intent);
//            }
//        });
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