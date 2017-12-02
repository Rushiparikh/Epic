package com.example.rushi.epic_thrillon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button lgbtn;

    private TextView tv;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    String Login_with;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        lgbtn = (Button) findViewById(R.id.button2);
        tv= (TextView)findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        Intent i = getIntent();
        String s= i.getExtras().getString("user");
        tv.setText(s);

        Login_with =  getIntent().getStringExtra("Login");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {


                }
            }
        };

            lgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Login_with!=null){
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(HomePage.this, AskForSignin.class));
                    }else{
                        mAuth.signOut();
                        startActivity(new Intent(HomePage.this, AskForSignin.class));
                    }

                }
            });


        }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
