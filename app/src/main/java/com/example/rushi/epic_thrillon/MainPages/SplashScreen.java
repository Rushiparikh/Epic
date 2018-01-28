package com.example.rushi.epic_thrillon.MainPages;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Auxiliaries.DbVisit;
import com.example.rushi.epic_thrillon.Auxiliaries.DbVisitContract;
import com.example.rushi.epic_thrillon.Auxiliaries.MyService;
import com.example.rushi.epic_thrillon.Classes.Notifiation;
import com.example.rushi.epic_thrillon.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


    long Delay = 1000;
    DbVisit mDbHelper;
    LocationManager locationManager;
    DatabaseReference mNotification;
    String v= null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDbHelper = new DbVisit(this);
        mNotification = FirebaseDatabase.getInstance().getReference(Constants.NOTIFICATION_DATABASE_PATH_UPLOADS);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Intent intent =new Intent(getApplicationContext(),MyService.class);
            startService(intent);
        }
        Bundle b = getIntent().getExtras();
        String s=getIntent().getExtras().getString("name");
        Log.e("TAG",s+" ");
        if(s !=null){
            Intent i = new Intent(this,Home_Page.class);
            i.putExtra("name",getIntent().getExtras().getString("name"));
            Notifiation n = new Notifiation(getIntent().getExtras().getString("name"),getIntent().getExtras().getString("id"));
            mNotification.child(mNotification.push().getKey()).setValue(n);
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
