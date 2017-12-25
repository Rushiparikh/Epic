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
import com.example.rushi.epic_thrillon.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.example.rushi.epic_thrillon.MainPages.Home_Page.i;

public class OneTimePass extends AppCompatActivity {
    EditText phoneEdit,oneTimePass;
    Button phonebt,Ontetimebt;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String id;
    String ValidationId,code,phoneNumber;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    String firstName_register,lastName_register,email_register,password_register,confirmPass_register,mobile_register;

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

        mref= FirebaseDatabase.getInstance().getReference("user");
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
                                String key=mref.push().getKey();
                                Intent intent = getIntent();

                                if(i>9){
                                    id="00"+i;
                                }else{
                                    id="000"+i;
                                }

                                firstName_register = intent.getStringExtra("FirstName");
                                lastName_register = intent.getStringExtra("LastName");
                                mobile_register = intent.getStringExtra("Number");
                                email_register = intent.getStringExtra("Email");
                                password_register = intent.getStringExtra("Password");
                                confirmPass_register = intent.getStringExtra("ConfirmPassword");
                                User userdta=new User(null,confirmPass_register,email_register,firstName_register,lastName_register,Long.parseLong(mobile_register),password_register,id,null);
                                mref.child(key).setValue(userdta);

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



