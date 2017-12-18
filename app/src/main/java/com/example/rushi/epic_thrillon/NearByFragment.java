package com.example.rushi.epic_thrillon;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;


public class NearByFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private MapView mapView;
    Double longitude,latitude ;
    private static final int PERMISSION_REQUEST_CODE = 200;


    ProgressDialog b;


    public NearByFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(checkPermission()){
            LocationManager lm = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);
            if(!(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }



            // Inflate the layout for this fragment
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(Location location) {

                    longitude= location.getLongitude();
                    latitude= location.getLatitude();



                }
            };


            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(getActivity(), locationResult);

        }
        else{
            requestPermission();
        }

        return inflater.inflate(R.layout.fragment_near_by, container, false);

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted)
                        Snackbar.make(getView(), "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(getView(), "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        mapView =  view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.e(">>>>>",""+longitude+latitude );
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Bhai Nu Ghar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));




    }


}