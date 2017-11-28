package com.example.rushi.epic_thrillon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button lgbtn;
    private TextView tv;
    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        lgbtn = (Button) findViewById(R.id.button2);
        tv= (TextView)findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        tv.setText( mAuth.getCurrentUser().getDisplayName());
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser().getDisplayName() == null) {

                        startActivity(new Intent(HomePage.this, AskForSignin.class));
                    }
                }
            };
            lgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    FirebaseAuth.getInstance().signOut();
                }
            });


        }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
