package com.example.rushi.epic_thrillon.MainPages;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rushi.epic_thrillon.Auxiliaries.DbVisit;
import com.example.rushi.epic_thrillon.Auxiliaries.DbVisitContract;
import com.example.rushi.epic_thrillon.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


    long Delay = 1000;
    DbVisit mDbHelper;
    String v= null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDbHelper = new DbVisit(this);



        String s=getIntent().getExtras().getString("name");
        Log.e("TAG",s+" ");
        if(s !=null){
            Intent i = new Intent(this,Home_Page.class);
            i.putExtra("name",getIntent().getExtras().getString("name"));
            startActivity(i);
            finish();
        }else{
            SQLiteDatabase db1 = mDbHelper.getReadableDatabase();
            String[] projection = {
                    DbVisitContract._ID,
                    DbVisitContract.COLUMN_NAME_TITLE,

            };

            Cursor cursor = db1.query(DbVisitContract.TABLE_NAME,projection,null,null,null,null,null);
            while(cursor.moveToNext()) {
                v= cursor.getString(1);

            }
            cursor.close();
            //Log.e("VALUE",v);



            Timer RunSplash = new Timer();

            // Task to do when the timer ends
            TimerTask ShowSplash = new TimerTask() {
                @Override
                public void run() {
                    // Close SplashScreenActivity.class
                    finish();
                    if(v != null){
                        Intent myIntent = new Intent(SplashScreen.this,
                                AskForSignin.class);

                        Log.e(">>>",getIntent().getExtras().getString("name")+"");
                        startActivity(myIntent);

                    }else{
                        // Start MainActivity.class

                        Intent myIntent = new Intent(SplashScreen.this,
                                SwapScreen.class);
                        myIntent.putExtra("not",getIntent().getExtras());
                        startActivity(myIntent);

                    }

                }
            };

            // Start the timer
            RunSplash.schedule(ShowSplash, Delay);


        }




            }
}