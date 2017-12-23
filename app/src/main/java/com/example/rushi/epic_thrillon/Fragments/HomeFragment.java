package com.example.rushi.epic_thrillon.Fragments;

import android.content.Intent;
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

import com.example.rushi.epic_thrillon.Adapters.ImageAdapter;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.MainPages.ActivityClick;
import com.example.rushi.epic_thrillon.Adapters.AlbumsAdapter;
import com.example.rushi.epic_thrillon.Classes.Album;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
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
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewfirst,recyclerViewsecond,recyclerViewthird;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    AdView adView;
    List<Activity> activityList;

    Upload u;
    DatabaseReference mref;
    GridView gridView;
    ValueEventListener valueEventListener;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        mref.addValueEventListener(valueEventListener);
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mref = FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
            mref.keepSynced(true);
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            adView = view.findViewById(R.id.adView);
            MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);

            albumList = new ArrayList<>();
            gridView = (GridView) view.findViewById(R.id.gridview);
            activityList = new ArrayList<>();
            adapter = new AlbumsAdapter(getActivity(), albumList);
            //  getSupportActionBar().setTitle(MainActivity.class.getSimpleName());
           valueEventListener=new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                        activityList.clear();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Activity activity =dataSnapshot1.getValue(Activity.class);
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
                    intent.putExtra("ActivityName",activityList.get(position).getActivityName());
                    intent.putExtra("ActivityImage",activityList.get(position).getImages().getImg2());
                    startActivity(intent);

                }
            });



            //   RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            //   recyclerView.setLayoutManager(mLayoutManager);
            //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            //   recyclerView.setItemAnimator(new DefaultItemAnimator());
            //   recyclerView.setAdapter(adapter);

            recyclerViewfirst = (RecyclerView) view.findViewById(R.id.recycler_view_first);
            recyclerViewfirst.setHasFixedSize(true);


            recyclerViewfirst.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recyclerViewfirst.setAdapter(adapter);

            prepareAlbums();

            recyclerViewsecond = (RecyclerView) view.findViewById(R.id.recycler_view_second);

            albumList = new ArrayList<>();
            adapter = new AlbumsAdapter(getActivity(), albumList);

            //   RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            //   recyclerView.setLayoutManager(mLayoutManager);
            //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            //   recyclerView.setItemAnimator(new DefaultItemAnimator());
            //   recyclerView.setAdapter(adapter);

            recyclerViewsecond.setHasFixedSize(true);


            recyclerViewsecond.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recyclerViewsecond.setAdapter(adapter);

            prepareAlbums();

            recyclerViewthird = (RecyclerView) view.findViewById(R.id.recycler_view_third);

            albumList = new ArrayList<>();
            adapter = new AlbumsAdapter(getActivity(), albumList);

            //   RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            //   recyclerView.setLayoutManager(mLayoutManager);
            //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            //   recyclerView.setItemAnimator(new DefaultItemAnimator());
            //   recyclerView.setAdapter(ad
            // apter);

            recyclerViewthird.setHasFixedSize(true);


            recyclerViewthird.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recyclerViewthird.setAdapter(adapter);

            prepareAlbums();


            return  view;
        }
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Album a = new Album("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new Album("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new Album("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new Album("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new Album("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        a = new Album("Loud", 11, covers[6]);
        albumList.add(a);

        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);

        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);

        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }


    /**
     * Adding few albums for testing
     */
    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}


