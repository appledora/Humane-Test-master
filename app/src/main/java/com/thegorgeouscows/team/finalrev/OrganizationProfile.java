package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class OrganizationProfile extends AppCompatActivity {

    private CardView pendingVerification;
    private CardView availableTasks;
    private CardView volunteerList;
    FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_ui_skull);

        pendingVerification = (CardView) findViewById(R.id.pending);
        availableTasks = (CardView)findViewById(R.id.available);
        volunteerList = (CardView)findViewById(R.id.volunteer);
        auth = FirebaseAuth.getInstance();




        pendingVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"PENDING",Toast.LENGTH_SHORT).show();

            }
        });


        availableTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"AVAILABLE",Toast.LENGTH_SHORT).show();
            }
        });

        volunteerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"Volunteer",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        auth.signOut();
                        Intent intent = new Intent(OrganizationProfile.this,loginActivity.class);
                        startActivity(intent);
                    case R.id.profile:
                        Toast.makeText(OrganizationProfile.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.badges:
                        Toast.makeText(OrganizationProfile.this,"ACHIEVEMENT",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
