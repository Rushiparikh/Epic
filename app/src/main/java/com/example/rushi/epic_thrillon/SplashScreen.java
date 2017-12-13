package com.example.rushi.epic_thrillon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


    long Delay = 1000;
    DbVisit  mDbHelper;
    String v= null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDbHelper = new DbVisit(this);








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
                    startActivity(myIntent);

                }else{
                    // Start MainActivity.class

                    Intent myIntent = new Intent(SplashScreen.this,
                            Home_Page.class);
                    startActivity(myIntent);
                }

            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }
}
