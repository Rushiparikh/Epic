package com.example.rushi.epic_thrillon.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rushi.epic_thrillon.Adapters.ActivityAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Destination;
import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;


public class DestinationFragment extends Fragment {
    AdView adView;
    DatabaseReference mref;
    List<Destination> destinationList;
    RecyclerView recyclerView;
    ValueEventListener valueEventListener;
    public DestinationFragment() {
        // Required empty public constructor
    }

    public void onResume() {
        super.onResume();
        mref.addValueEventListener(valueEventListener);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        adView = view.findViewById(R.id.adView);
        mref= FirebaseDatabase.getInstance().getReference(Constants.DESTINATION_DATABASE_PATH_UPLOADS);
        destinationList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recycler_view);
        MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Inflate the layout for this fragment
        adView.loadAd(adRequest);
        // Inflate the layout for this fragment
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Destination destination=dataSnapshot1.getValue(Destination.class);
                    destinationList.add(destination);
                }
                Set<Destination> set = new HashSet<>();
                set.addAll(destinationList);
                destinationList.clear();
                destinationList.addAll(set);

                recyclerView.setAdapter(new ActivityAdapter(getActivity(), destinationList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return view;
    }


}
