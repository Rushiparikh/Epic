package com.example.rushi.epic_thrillon.MainPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.BookedActivity;
import com.example.rushi.epic_thrillon.Classes.Wishlist;
import com.example.rushi.epic_thrillon.R;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PaymentDetails extends AppCompatActivity {
    public TextView amount,transid,status;
    DatabaseReference mDatabaseReference;
    boolean google,facebook,email;
    private SharedPreferences sharedPreferences;
    String actId;
    boolean flag = true;
    private GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        amount = findViewById(R.id.transamount);
        transid= findViewById(R.id.transId);
        status = findViewById(R.id.transStatus);
        sharedPreferences = getSharedPreferences(AskForSignin.My_pref, Context.MODE_PRIVATE);
        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.USERS_DATABASE_PATH_UPLOADS);
        Intent i = getIntent();
         actId = i.getStringExtra("activityId");
        try{

            JSONObject jsonObject = new JSONObject( i.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),i.getStringExtra("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        facebook = sharedPreferences.getBoolean("Facebook",false);
        google = sharedPreferences.getBoolean("Google",false);
        email = sharedPreferences.getBoolean("Email",false);
        if(facebook){
            Profile profile =  Profile.getCurrentProfile();
            final String facebookID = profile.getId();
            Query query = mDatabaseReference.orderByChild("email").equalTo(facebookID);
            query.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if(flag) {
                            String Key = mDatabaseReference.push().getKey();
                            BookedActivity bookedActivity = new BookedActivity(actId);
                            mDatabaseReference.child(dataSnapshot1.getKey()).child("booked_activity").child(Key).setValue(bookedActivity);
                            flag = false;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else if(google && acct!=null){

            final String googleMail = acct.getEmail();
            Query query = mDatabaseReference.orderByChild("email").equalTo(googleMail);
            query.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if(flag) {
                            String Key = mDatabaseReference.push().getKey();
                            BookedActivity bookedActivity = new BookedActivity(actId);
                            mDatabaseReference.child(dataSnapshot1.getKey()).child("booked_activity").child(Key).setValue(bookedActivity);
                            flag = false;
                        }
                    }
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{


        }

    }




    private void showDetails(JSONObject response, String paymamount) {
        try {
            transid.setText(response.getString("id"));
            status.setText(response.getString("state"));
            double d = (Double.parseDouble(paymamount)) * 65 + ((Double.parseDouble(paymamount))*0.5);
            amount.setText(String.valueOf(d));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    }

