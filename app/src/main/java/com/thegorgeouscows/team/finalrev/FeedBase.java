package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedBase extends AppCompatActivity {
    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private FloatingActionButton addPostBtn;

    private BottomNavigationView mainbottomNav;

    private FeedFragment feedFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_base);
        Log.i("my","REACHED FEEDBASE");

        mAuth = FirebaseAuth.getInstance();
        Log.i("my","got mAuth");

        if(mAuth.getCurrentUser() != null){
            Log.i("my","CURRENT USER CHECKED");
            mainbottomNav = findViewById(R.id.mainBottomNav);
            feedFragment = new FeedFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            Log.i("my","clicked badges");
            transaction.replace(R.id.main_container,feedFragment);
            transaction.commit();

            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch(item.getItemId()){
                        case R.id.home:
                            Log.i("my","clicked Profile");
                           /* transaction.replace(R.id.main_container,feedFragment);
                            transaction.commit();*/
                            return true;
                        case R.id.user_profile:
                            Intent i = new Intent(FeedBase.this,DonatorProfile.class);
                            startActivity(i);
                            return true;
                        case R.id.logout_feed:
                            mAuth.signOut();
                            Intent intent = new Intent(FeedBase.this,loginActivity.class);
                            startActivity(intent);


                        default:
                             return false;
                    }
                }
            });

            addPostBtn = findViewById(R.id.add_post_btn);
            addPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FeedBase.this,FoodPostForm.class);
                    startActivity(intent);
                }
            });
        }
    }


}
