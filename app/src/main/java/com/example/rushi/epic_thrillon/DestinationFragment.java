package com.example.rushi.epic_thrillon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class DestinationFragment extends Fragment {
    AdView adView;

    public DestinationFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        adView = view.findViewById(R.id.adViewDEst);
        MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Inflate the layout for this fragment
        adView.loadAd(adRequest);
        // Inflate the layout for this fragment
        return view;
    }


}
