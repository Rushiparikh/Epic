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
import android.widget.Toast;

import com.example.rushi.epic_thrillon.Auxiliaries.Config;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Booking extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_ID);

    String actId,person;
    Button makepayment;
    TextView actDate,actTime,actName,actPerson,total;
    EditText mobileNo,firstName,lastName,email;
    DatabaseReference mref;
    Activity activity;
    String amount;

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
                processPayment();
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

    public void processPayment(){
        String t =String.valueOf(total.getText().toString());
        double value= (Double.parseDouble(t))/65;
        amount = String.valueOf(value);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Donate To Dv",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(Booking.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String paymentdetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(Booking.this, PaymentDetails.class)
                                .putExtra("paymentDetails",paymentdetails).putExtra("amount",amount).putExtra("activityId",actId));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(resultCode == android.app.Activity.RESULT_CANCELED){
                    Toast.makeText(Booking.this,">>>>",Toast.LENGTH_LONG).show();
                }

            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
