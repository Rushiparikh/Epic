package com.example.rushi.epic_thrillon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Notifiation;
import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URISyntaxException;


public class NotificationFragment extends Fragment {
    private DatabaseReference mDatabase;
    AdView adView;
    DatabaseReference notificationReference;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_notification, container, false);
        adView = v.findViewById(R.id.adView);
        MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Inflate the layout for this fragment
        adView.loadAd(adRequest);
        if(getArguments() !=null){
            Bundle b = getArguments().getBundle("DATA");
            String Data= b.getString("payload");
            String name = b.getString("name");
            Notifiation notifiation = new Notifiation(name,Data);
            notificationReference = FirebaseDatabase.getInstance().getReference(Constants.NOTIFICATION_DATABASE_PATH_UPLOADS);
            String Key= notificationReference.push().getKey();
            notificationReference.child(Key).setValue(notifiation);
        }





        return v;
    }



}
