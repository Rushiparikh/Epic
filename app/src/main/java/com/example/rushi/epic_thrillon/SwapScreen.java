package com.example.rushi.epic_thrillon;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SwapScreen extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    DbVisit  mDbHelper;
    private static final Integer[] XMEN= {R.drawable.na1,R.drawable.na2,R.drawable.na3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_screen);
        mDbHelper = new DbVisit(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbVisitContract.COLUMN_NAME_TITLE, "True");


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DbVisitContract.TABLE_NAME, null, values);

        if(newRowId > 0){
            Log.e("DOOOO","DATA INSRETED");

        }
        db.close();
        init();
    }
    public void getStartedButtonClick(View view){
        Intent intent=new Intent(this,AskForSignin.class);
        startActivity(intent);

    }
    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PageAdapter(SwapScreen.this,XMENArray));


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

}
