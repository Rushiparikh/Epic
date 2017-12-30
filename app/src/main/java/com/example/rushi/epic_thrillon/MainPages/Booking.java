package com.example.rushi.epic_thrillon.MainPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Booking extends AppCompatActivity {

    String actId,person;
    Button makepayment;
    TextView actDate,actTime,actName,actPerson,total;
    EditText mobileNo,firstName,lastName,email;
    DatabaseReference mref;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actId=getIntent().getExtras().getString("activityId");
        person=getIntent().getExtras().getString("people");
        actDate=findViewById(R.id.actName);
        actTime=findViewById(R.id.actTiming);
        actName=findViewById(R.id.actName);
        mobileNo=findViewById(R.id.mobile_no);
        firstName=findViewById(R.id.first_name);
        lastName=findViewById(R.id.last_name);
        email=findViewById(R.id.email_id);
        actPerson=findViewById(R.id.person);
        total=findViewById(R.id.price);
        makepayment = findViewById(R.id.make_payment_button);
        mref= FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mref.orderByChild("id").equalTo(actId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    activity=dataSnapshot1.getValue(Activity.class);

                }
                actName.setText(activity.getActivityName());
                actPerson.setText(person);
                actDate.setText(activity.getActivityDate());
                actTime.setText(activity.getActivityTime());
                total.setText(String.valueOf((activity.getPrice())* Integer.parseInt(person)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        makepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
