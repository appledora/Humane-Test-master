package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonatorProfile extends AppCompatActivity {
    private TextView name ;
    private TextView email;
    private Button butt;
    DatabaseReference ref;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authListener;
    String uid;




    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.donator_ui_skull);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        name = (TextView)findViewById(R.id.name_display);
        email = (TextView)findViewById(R.id.mail_display);
        butt = (Button)findViewById(R.id.start_donation);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nm = dataSnapshot.child("Name").getValue().toString();
                    String em = dataSnapshot.child("Email").getValue(String.class);
                    Log.i("my: ",nm);
                    Log.i("my",em);

                    name.setText(nm);
                    email.setText(em);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DonatorProfile.this, SlideActivity.class);
                startActivity(intent);
            }
        });
        setupBottomNavigationView();

        }
    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        auth.signOut();
                        Intent intent = new Intent(DonatorProfile.this,loginActivity.class);
                        startActivity(intent);
                    case R.id.current_profile:
                        Toast.makeText(DonatorProfile.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.feed:
                        Log.i("my","Badges clicked");
                       Intent i = new Intent(DonatorProfile.this,FeedBase.class);
                        startActivity(i);
                }
                return true;
            }
        });
    }

}
