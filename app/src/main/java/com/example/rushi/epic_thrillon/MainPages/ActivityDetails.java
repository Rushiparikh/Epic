package com.example.rushi.epic_thrillon.MainPages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.rushi.epic_thrillon.Adapters.ActivityDetailPageAdapter;
import com.example.rushi.epic_thrillon.Adapters.PageAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Auxiliaries.DbVisit;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Images;
import com.example.rushi.epic_thrillon.Classes.Location;
import com.example.rushi.epic_thrillon.Classes.PublicReviews;
import com.example.rushi.epic_thrillon.Classes.Service;
import com.example.rushi.epic_thrillon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityDetails extends AppCompatActivity implements OnMapReadyCallback{
    ViewPager mPager;
    Activity activity;
    Images images;
    Service service;
    PublicReviews publicReviews;
    Location location;
    DatabaseReference mActivityReference;
    TextView activityname,destination,activityPrice,activityTiming,activityDate,reviewTitle,reviews,organizerName,organizerContact;
    ReadMoreTextView description;
    RatingBar ratingBar;
    ImageView food,travel,guide,acomodation;
    GoogleMap mMap;
    MapView mapView;
    Button checkavailabily;
    double latitude,longitude;

    private ArrayList<String> imageLists = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        final String actId = getIntent().getStringExtra("activityId");
        mActivityReference = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mPager = (ViewPager) findViewById(R.id.pager);
        activityname = findViewById(R.id.actName);
        destination = findViewById(R.id.actDestName);
        description = findViewById(R.id.readmore);
        activityPrice = findViewById(R.id.actPrice);
        activityTiming = findViewById(R.id.actTiming);
        activityDate = findViewById(R.id.actDate);
        reviews = findViewById(R.id.actReviews);
        reviewTitle = findViewById(R.id.actReviewTitle);
        organizerName = findViewById(R.id.actOrgName);
        organizerContact = findViewById(R.id.actOrgContact);
        ratingBar = findViewById(R.id.actRating);
        mapView = findViewById(R.id.actMap);
        food = findViewById(R.id.actFood);
        travel = findViewById(R.id.actTravelling);
        acomodation = findViewById(R.id.actAcomodation);
        checkavailabily = findViewById(R.id.checkavailability);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(getApplicationContext());
        mActivityReference.orderByChild("id").equalTo(actId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                     for(DataSnapshot ds : dataSnapshot.getChildren())
                     {
                         activity = ds.getValue(Activity.class);
                         images = ds.child("images").getValue(Images.class);
                         publicReviews = ds.child("publicReviews").getValue(PublicReviews.class);
                         location = ds.child("location").getValue(Location.class);
                         service = ds.child("service").getValue(Service.class);
                     }
                     imageLists.add(images.getImg1());
                     imageLists.add(images.getImg2());
                     imageLists.add(images.getImg3());
                     imageLists.add(images.getImg4());
                     activityname.setText( activity.getActivityName().toString());
                     destination.setText(activity.getDestination());
                     activityPrice.setText(String.valueOf((int) activity.getPrice()));
                     ratingBar.setRating((float) activity.getRating());
                     ratingBar.isIndicator();
                     activityDate.setText(activity.getActivityDate());
                     activityTiming.setText(String.valueOf(activity.getActivityTime()));
                     description.setText(activity.getDescription());
                     description.setTrimCollapsedText("Read More");
                     description.setTrimExpandedText("Read less");
                     reviews.setText(publicReviews.getComment());
                     reviewTitle.setText(publicReviews.getCommentTitle());
                     organizerName.setText(activity.getOrganizerName());
                     organizerContact.setText(String.valueOf( activity.getOrganizerContact()));
                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                     if(service.getFood()){
                         food.setVisibility(View.VISIBLE);
                    }
                    else{
                         food.setVisibility(View.GONE);
                     }
                     if(service.getHotel()){
                            acomodation.setVisibility(View.VISIBLE);
                     }else{
                         acomodation.setVisibility(View.GONE);
                     }
                     if(service.getTravelling()){
                            travel.setVisibility(View.VISIBLE);
                    }else{
                         travel.setVisibility(View.GONE);
                     }


                mPager.setAdapter(new ActivityDetailPageAdapter(ActivityDetails.this,imageLists));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mapView.getMapAsync(this);
        checkavailabily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityDetails.this,CheckAvailability.class);
                i.putExtra("activityId",actId);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    collapsingToolbar.setTitle(activity.getActivityName());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(activity.getActivityName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    isShow = false;
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(latitude, longitude);
                MarkerOptions op = new MarkerOptions();
                op.position(sydney)
                        .title(activityname.getText().toString())
                        .snippet(destination.getText().toString())
                        .draggable(true);
                mMap.addMarker(op);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( sydney, 15));

                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);      //  mMap.addMarker(new MarkerOptions().position(sydney).title(activity.getActivityName()));




            }
        }, 2000);


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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
