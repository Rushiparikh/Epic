package com.example.rushi.epic_thrillon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class DestinationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Activity_DestinationAdapter adapter;
    private List<Destination> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycleview);
        imageList = new ArrayList<>();
        adapter = new Activity_DestinationAdapter(this, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        prepareImage();


    }

    private void prepareImage() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
        };

        Destination a = new Destination("True Romance", 13, covers[0]);
        imageList.add(a);

        a = new Destination("Xscpae", 8, covers[1]);
        imageList.add(a);

        a = new Destination("Maroon 5", 11, covers[2]);
        imageList.add(a);

        a = new Destination("Born to Die", 12, covers[3]);
        imageList.add(a);

        adapter.notifyDataSetChanged();

    }
}

