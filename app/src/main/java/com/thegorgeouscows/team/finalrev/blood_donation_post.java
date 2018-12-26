package com.thegorgeouscows.team.finalrev;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class blood_donation_post extends AppCompatActivity implements View.OnClickListener {

   // private Calendar mCalendar = Calendar.getInstance();
    private EditText production_date,expiration_date,pickup_time,quantity,pickup_address,contact_no;
    private int year,month,date,hour,minute;
    private Button post_button;
    private Uri postImageUri = null;
    //private ProgressBar postProgress;
    private String currentUserID;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_donation_post);

        production_date = (EditText)findViewById(R.id.production);
        expiration_date = (EditText)findViewById(R.id.expiration_date);
        pickup_time = (EditText)findViewById(R.id.time);
        quantity = (EditText)findViewById(R.id.quantity);
        pickup_address = (EditText)findViewById(R.id.address);
        contact_no = (EditText)findViewById(R.id.contact_number);
        post_button = (Button)findViewById(R.id.donate);

        storageReference =FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();

        production_date.setOnClickListener(this);
        expiration_date.setOnClickListener(this);
        pickup_time.setOnClickListener(this);
        post_button.setOnClickListener(this);
    }



    @Override
    public void onClick(final View v) {


        if(v == production_date || v == expiration_date){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            date = c.get(Calendar.DAY_OF_MONTH);


            final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if(v== production_date){
                                production_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                            if(v==expiration_date){
                                expiration_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }

                        }
                    }, year, month, date);
            datePickerDialog.show();
        }

        if(v == pickup_time){
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            pickup_time.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }
        if(v == post_button){
            Log.i("my","post button pressed");
            final String quan = quantity.getText().toString().trim();
            final String prod = production_date.getText().toString().trim();
            final String expi = expiration_date.getText().toString().trim();
            final String pick = pickup_address.getText().toString().trim();
            final String cont = contact_no.getText().toString().trim();
            final String add = pickup_address.getText().toString().trim();

            if(!TextUtils.isEmpty(quan) && !TextUtils.isEmpty(prod) && !TextUtils.isEmpty(expi) && !TextUtils.isEmpty(pick) && !TextUtils.isEmpty(cont) && !TextUtils.isEmpty(add) ){
                //postProgress.setVisibility(View.VISIBLE);
                Log.i("my", "nothing is empty");
                String randomName = UUID.randomUUID().toString();
                final StorageReference filePath = storageReference.child("post_images").child(randomName + ".jpg");
                filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Log.i("my","putting file?");


                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                           @Override
                           public void onSuccess(Uri uri) {
                               Log.i("my","got through downloadUri");
                               final String downloadUri = uri.toString();
                               Map<String,Object> postMap = new HashMap<>();
                               postMap.put("image_url",downloadUri);
                               postMap.put("quantity",quan);
                               postMap.put("production date",prod);
                               postMap.put("expiration date",expi);
                               postMap.put("pickup time",pick);
                               postMap.put("address",add);
                               postMap.put("contact",cont);
                               postMap.put("user id",currentUserID);
                               postMap.put("timestamp", FieldValue.serverTimestamp());


                               firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                   @Override
                                   public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(blood_donation_post.this,"POST WAS ADDED",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            //postProgress.setVisibility(View.INVISIBLE);
                                            Toast.makeText(blood_donation_post.this,"POST WAS NOOOOT ADDED",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                   }
                               });
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               //postProgress.setVisibility(View.INVISIBLE);
                           }
                       });
                    }
                });
            }

            else{
                Toast.makeText(blood_donation_post.this,"POST WAS NOOOOT ADDED",Toast.LENGTH_LONG).show();
            }

        }


        }
    }

