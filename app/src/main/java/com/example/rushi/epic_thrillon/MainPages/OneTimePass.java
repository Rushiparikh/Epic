package com.example.rushi.epic_thrillon.MainPages;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OneTimePass extends AppCompatActivity {
    EditText phoneEdit,oneTimePass;
    Button phonebt,Ontetimebt;
    FirebaseAuth mAuth;
    String ValidationId,code,phoneNumber;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_pass);
        mAuth =FirebaseAuth.getInstance();
        Intent i = getIntent();
        phoneNumber = i.getExtras().getString("Number");
        phoneEdit = (EditText) findViewById(R.id.phone);
        phonebt = (Button) findViewById(R.id.phoneSummit);
        oneTimePass = (EditText) findViewById(R.id.otp);
        Ontetimebt = (Button) findViewById(R.id.otpSummit);

        phoneEdit.setText(phoneNumber);


        phonebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCode();
            }
        });

        Ontetimebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyPhone();
            }
        });
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e(">>>>>>>>", "erorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                ValidationId =s;
                token = forceResendingToken;
            }
        };
    }

    public void GetCode(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallBack);
        phonebt.setVisibility(View.GONE);
        phoneEdit.setVisibility(View.GONE);
        Ontetimebt.setVisibility(View.VISIBLE);
        oneTimePass.setVisibility(View.VISIBLE);

    }
    public void VerifyPhone(){
        code = oneTimePass.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(ValidationId,code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e(">>>>>>>>>>", "signInWithCredential:success");

                                FirebaseUser user = task.getResult().getUser();

                                startActivity(new Intent(OneTimePass.this,Login.class));
                                finish();
                                // ...
                            } else {
                                // Sign in failed, display a message and update the UI
                                Log.e(">>>>>>>>", "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                }
                            }
                        }
                    });
        }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    }



