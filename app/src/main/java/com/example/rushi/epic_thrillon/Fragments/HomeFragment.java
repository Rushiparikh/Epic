package com.example.rushi.epic_thrillon.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.rushi.epic_thrillon.Adapters.ActivityOfTheDayAdapter;
import com.example.rushi.epic_thrillon.Adapters.ImageAdapter;
import com.example.rushi.epic_thrillon.Adapters.PopularDestinationAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.MyLocation;
import com.example.rushi.epic_thrillon.Auxiliaries.RecyclerItemClickListener;
import com.example.rushi.epic_thrillon.Classes.Activities;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Destination;
import com.example.rushi.epic_thrillon.MainPages.ActivityClick;
import com.example.rushi.epic_thrillon.Adapters.AlbumsAdapter;
import com.example.rushi.epic_thrillon.Classes.Album;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.MainPages.ActivityDetails;
import com.example.rushi.epic_thrillon.MainPages.DestinationClick;
import com.example.rushi.epic_thrillon.R;
import com.example.rushi.epic_thrillon.Upload;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewfirst,recyclerViewsecond,recyclerViewthird;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    AdView adView;
    List<Activities> activityList;
    List<Activity> activityofthedayList;
    List<Activity> nearByActivityList;
    List<Destination> destinationList;
    Date currentDate;
    double longitude,latitude;

    Upload u;
    DatabaseReference mref,mdatabse,mdestination;
    GridView gridView;
    ValueEventListener valueEventListener,activityValueEventListener,destinationValueEventListner;
    String formattedDate;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        statusCheck();
        mref.addValueEventListener(valueEventListener);
        mdatabse.addValueEventListener(activityValueEventListener);
        mdestination.addValueEventListener(destinationValueEventListner);

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);


    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mref = FirebaseDatabase.getInstance().getReference(Constants.ACIVITIES_DATABASE_PATH_UPLOADS);
            mdatabse = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
            mdestination=FirebaseDatabase.getInstance().getReference(Constants.DESTINATION_DATABASE_PATH_UPLOADS);
            mref.keepSynced(true);
            mdatabse.keepSynced(true);
            mdestination.keepSynced(true);
            currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
             formattedDate = df.format(currentDate);
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            adView = view.findViewById(R.id.adView);
            MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);
            statusCheck();
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);
            albumList = new ArrayList<>();
            gridView = (GridView) view.findViewById(R.id.gridview);
            activityList = new ArrayList<>();
            activityofthedayList = new ArrayList<>();
            nearByActivityList = new ArrayList<>();
            destinationList=new ArrayList<>();



            recyclerViewfirst = (RecyclerView) view.findViewById(R.id.recycler_view_first);
            recyclerViewfirst.setHasFixedSize(true);
            recyclerViewfirst.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewsecond = (RecyclerView) view.findViewById(R.id.recycler_view_second);
            recyclerViewsecond.setHasFixedSize(true);
            recyclerViewsecond.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewthird = (RecyclerView) view.findViewById(R.id.recycler_view_third);
            recyclerViewthird.setHasFixedSize(true);
            recyclerViewthird.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //  getSupportActionBar().setTitle(MainActivity.class.getSimpleName());
           valueEventListener=new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                        activityList.clear();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Activities activity =dataSnapshot1.getValue(Activities.class);
                            activityList.add(activity);


                        }
                      gridView.setAdapter(new ImageAdapter(getActivity(),activityList));

                   }
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           };
         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent intent=new Intent(getActivity(),ActivityClick.class);
                    intent.putExtra("ActivityName",activityList.get(position).getName());
                    intent.putExtra("ActivityImage",activityList.get(position).getImage());

                    startActivity(intent);

                }
            });



        activityValueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activityofthedayList.clear();
                nearByActivityList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Activity activity =dataSnapshot1.getValue(Activity.class);

                    if(activity.getActivityDate().equals(formattedDate)){
                        activityofthedayList.add(activity);
                    }

                    com.example.rushi.epic_thrillon.Classes.Location location = activity.getLocation();

                        double latDiff = Math.abs((location.getLatitude()) - latitude);
                        double longDiff = Math.abs((location.getLongitude()) - longitude);
                        if((latDiff < 20) && (longDiff < 20)){
                            nearByActivityList.add(activity);
                        }





                }
                recyclerViewfirst.setAdapter(new ActivityOfTheDayAdapter(getActivity(), activityofthedayList));
                ActivityOfTheDayAdapter adapter=new ActivityOfTheDayAdapter(getActivity(),nearByActivityList);
                recyclerViewsecond.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        destinationValueEventListner=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                destinationList.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Destination destination=dataSnapshot1.getValue(Destination.class);
                    destinationList.add(destination);
                }
                recyclerViewthird.setAdapter(new PopularDestinationAdapter(getActivity(),destinationList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

            recyclerViewfirst.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewfirst, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent i = new Intent(getActivity(), ActivityDetails.class);
                    i.putExtra("activityId",activityofthedayList.get(position).getId());
                    startActivity(i);
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));

        recyclerViewsecond.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewsecond, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), ActivityDetails.class);
                i.putExtra("activityId",nearByActivityList.get(position).getId());
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        recyclerViewthird.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewthird, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), DestinationClick.class);
                i.putExtra("destinationName",destinationList.get(position).getDestName());
                i.putExtra("destinationImage",destinationList.get(position).getImage());

                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));







            return  view;
        }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}


