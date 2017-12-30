package com.example.rushi.epic_thrillon.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rushi.epic_thrillon.Adapters.WishlistAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.User;
import com.example.rushi.epic_thrillon.Classes.Wishlist;
import com.example.rushi.epic_thrillon.MainPages.AskForSignin;
import com.example.rushi.epic_thrillon.R;
import com.facebook.Profile;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static com.facebook.FacebookSdk.getApplicationContext;


public class WishListFragment extends Fragment {
    AdView adView;
    DatabaseReference mUser,mActivity;
    boolean flag=true;
    List<Wishlist> wishlists;
    List<Activity> activityList;
    SharedPreferences sharedPreferences;
    GoogleSignInAccount acct;
    WishlistAdapter wishlistAdapter;
    RecyclerView recyclerView;

    boolean facebook,google,email;
    public WishListFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        recyclerView = view.findViewById(R.id.wishlistRecycler);
        adView = view.findViewById(R.id.adView);
        MobileAds.initialize(getActivity(), "ca-app-pub-4689037977247733~9439374585");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // Inflate the layout for this fragment
        adView.loadAd(adRequest);

        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        wishlists = new ArrayList<>();
        activityList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(AskForSignin.My_pref, Context.MODE_PRIVATE);
        // Inflate the layout for this fragment
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mActivity = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mUser = FirebaseDatabase.getInstance().getReference(Constants.USERS_DATABASE_PATH_UPLOADS);
        facebook = sharedPreferences.getBoolean("Facebook", false);
        google = sharedPreferences.getBoolean("Google", false);
        email = sharedPreferences.getBoolean("Email", false);
        if (facebook) {
            Profile profile = Profile.getCurrentProfile();
            final String facebookID = profile.getId();
            Query query = mUser.orderByChild("email").equalTo(facebookID);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    wishlists.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot ds: dataSnapshot1.child("wishlist").getChildren()){
                            Wishlist wishlist = ds.getValue(Wishlist.class);
                            wishlists.add(wishlist);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mActivity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    activityList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Activity activity = dataSnapshot1.getValue(Activity.class);
                        for (int i = 0; i < wishlists.size(); i++) {

                            if ((activity.getActivityId().equals( wishlists.get(i).getActId()))){
                                    if( (activity.getOrganizerId().equals( wishlists.get(i).getOrgId()))) {
                                activityList.add(activity);
                            }}
                        }
                    }
                    wishlistAdapter = new WishlistAdapter(getActivity(), activityList, sharedPreferences);
                    recyclerView.setAdapter(wishlistAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(google && acct!=null){

            final String googleMail = acct.getEmail();
            Query query = mUser.orderByChild("email").equalTo(googleMail);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    wishlists.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot ds: dataSnapshot1.child("wishlist").getChildren()){
                            Wishlist wishlist = ds.getValue(Wishlist.class);
                            wishlists.add(wishlist);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mActivity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    activityList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Activity activity = dataSnapshot1.getValue(Activity.class);
                        for (int i = 0; i < wishlists.size(); i++) {

                            if ((activity.getActivityId().equals( wishlists.get(i).getActId()))){
                                if( (activity.getOrganizerId().equals( wishlists.get(i).getOrgId()))) {
                                    activityList.add(activity);
                                }}
                        }
                    }
                    wishlistAdapter = new WishlistAdapter(getActivity(), activityList, sharedPreferences);
                    recyclerView.setAdapter(wishlistAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
            return view;
        }

    }



