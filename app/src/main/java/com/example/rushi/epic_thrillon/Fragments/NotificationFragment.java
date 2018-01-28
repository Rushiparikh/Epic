package com.example.rushi.epic_thrillon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rushi.epic_thrillon.Adapters.NotificationAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Auxiliaries.RecyclerItemClickListener;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Notifiation;
import com.example.rushi.epic_thrillon.MainPages.ActivityDetails;
import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class NotificationFragment extends Fragment {
    private DatabaseReference mNotification,mActivityReference;
    AdView adView;
    List<String> mNotifiationList;
    NotificationAdapter mAdapter;
    List<Activity> mActivityList;
    RecyclerView recyclerView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_notification, container, false);
        mNotifiationList = new ArrayList<>();
        mActivityList=new ArrayList<>();
        adView = v.findViewById(R.id.adView);
        MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Inflate the layout for this fragment
        adView.loadAd(adRequest);
        mActivityReference=FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mNotification=FirebaseDatabase.getInstance().getReference(Constants.NOTIFICATION_DATABASE_PATH_UPLOADS);
        recyclerView = v.findViewById(R.id.notificationRecycler);
        mActivityReference.keepSynced(true);
        mNotification.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
         recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 Intent i = new Intent(getActivity(), ActivityDetails.class);
                 i.putExtra("activityId", mActivityList.get(position).getId());
                 startActivity(i);
             }
             @Override
             public void onLongItemClick(View view, int position) {

             }
         }));
        mNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotifiationList.clear();

                    for(DataSnapshot mDataSnapshot1 : dataSnapshot.getChildren()){
                        Notifiation n = mDataSnapshot1.getValue(Notifiation.class);
                        mNotifiationList.add(n.getId());
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mActivityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mActivityList.clear();

                for (DataSnapshot mDataSnapshot1 : dataSnapshot.getChildren()){
                    Activity activity = mDataSnapshot1.getValue(Activity.class);
                    for(int i = 0; i < mNotifiationList.size();i++){
                        if(activity.getId().equals(mNotifiationList.get(i))){
                            mActivityList.add(activity);
                            break;
                        }
                    }
                }
                mAdapter = new NotificationAdapter(getActivity(),mActivityList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return v;
    }



}
