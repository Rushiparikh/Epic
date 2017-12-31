package com.example.rushi.epic_thrillon.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.rushi.epic_thrillon.Adapters.ActivityOfTheDayAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Auxiliaries.MyLocation;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;



public class NearByFragment extends Fragment{
    private GoogleMap mMap;
    private MapView mapView;
    double longitude,latitude ;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ProgressBar progressBar;
    LocationManager lm;
    Handler handler;
    Runnable runnable;
    ValueEventListener activityValueEventListener;
    DatabaseReference mref;
    List<com.example.rushi.epic_thrillon.Classes.Location> locationList;



    ProgressDialog b;


    public NearByFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_near_by, container, false);
        progressBar= rootView.findViewById(R.id.progressBar2);
        mref = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mref.keepSynced(true);
        locationList = new ArrayList<>();
         lm= (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        mapView = (MapView) rootView.findViewById(R.id.map);

        if(mapView!= null) {
            mapView.onCreate(savedInstanceState);
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (checkPermission()) {

                if (!(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
                final MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                    @Override
                    public void gotLocation(Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                };
                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(getActivity(), locationResult);

                activityValueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            locationList.clear();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Activity activity =dataSnapshot1.getValue(Activity.class);
                            com.example.rushi.epic_thrillon.Classes.Location location = activity.getLocation();
                            double latDiff = Math.abs((location.getLatitude()) - latitude);
                            double longDiff = Math.abs((location.getLongitude()) - longitude);
                            if((latDiff < 20) && (longDiff < 20)){
                                locationList.add(location);
                            }





                        }
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                mapView.getMapAsync(new OnMapReadyCallback() {
                                    @SuppressLint("MissingPermission")
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        mMap = googleMap;

                                        // For showing a move to my location button
                                        mMap.setMyLocationEnabled(true);
                                        // For dropping a marker at a point on the Map
                                        LatLng sydney = new LatLng(latitude, longitude);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location").snippet("You are Here !!!!!!!"));

                                        for(int i = 0; i<locationList.size();i++){
                                            LatLng loc = new LatLng(locationList.get(i).getLatitude(),locationList.get(i).getLongitude());
                                            mMap.addMarker(new MarkerOptions().position(loc).title("Your Location").snippet("You are Here !!!!!!!"));
                                        }

                                        // For zooming automatically to the location of the marker
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                                    }
                                });

                            }

                        };
                        handler.postDelayed(runnable,11000);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };


            } else {
                progressBar.isShown();
                requestPermission();
            }
        }


        return rootView;

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }







    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mref.addValueEventListener(activityValueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        handler.removeCallbacksAndMessages(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(runnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}