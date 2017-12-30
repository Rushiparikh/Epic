package com.example.rushi.epic_thrillon.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Organizer;
import com.example.rushi.epic_thrillon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckAvailability extends AppCompatActivity {
    TextView actTiming,actName;
    EditText actDate,actPerson;
    ImageButton plus,minus;
    DatabaseReference mDatabaseReference,mref;
    Activity activity;
    Organizer organizer;
    Button next;
    public static  int PERSON_AVAIL = 1;
    int available;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availability);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actTiming = findViewById(R.id.actTiming);
        actPerson = findViewById(R.id.actPesron);
        actName = findViewById(R.id.actName);
        actDate = findViewById(R.id.actDate);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        next = findViewById(R.id.next);
        final String Id = getIntent().getStringExtra("activityId");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mref = FirebaseDatabase.getInstance().getReference(Constants.ORGANZIER_DATABASE_PATH_UPLOADS);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PERSON_AVAIL<available) {
                    PERSON_AVAIL++;
                    actPerson.setText(String.valueOf(PERSON_AVAIL));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Maximum limit reached",Toast.LENGTH_SHORT).show();

                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PERSON_AVAIL >1) {
                    PERSON_AVAIL--;
                    actPerson.setText(String.valueOf(PERSON_AVAIL));
                }else{
                    Toast.makeText(getApplicationContext(),"Minimum One Person Required",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDatabaseReference.orderByChild("id").equalTo(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                     activity = ds.getValue(Activity.class);
                    }
                    actDate.setText(activity.getActivityDate());
                    actTiming.setText(activity.getActivityTime());
                    actName.setText(activity.getActivityName());


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mref.orderByChild("id").equalTo(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        organizer = ds.getValue(Organizer.class);
                    }
                   available= organizer.getAvailability();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CheckAvailability.this,Booking.class);
                intent.putExtra("activityId",Id);
                intent.putExtra("people",String.valueOf(actPerson.getText()));
                startActivity(intent);
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
