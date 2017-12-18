package com.example.rushi.epic_thrillon;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewfirst,recyclerViewsecond,recyclerViewthird;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private Button mExpandButton;
    private ExpandableRelativeLayout mExpandLayout;
    private Button mOverlayText,n;
    int i =0;

    LinearLayout linearLayout;
    AdView adView;
    String[] web;
    String[] mThumbIds;
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

            mref = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            adView = view.findViewById(R.id.adView);
            MobileAds.initialize(getActivity(),"ca-app-pub-4689037977247733~9439374585");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            // Inflate the layout for this fragment
            adView.loadAd(adRequest);
            albumList = new ArrayList<>();
            gridView = (GridView) view.findViewById(R.id.gridview);

            adapter = new AlbumsAdapter(getActivity(), albumList);

            //  getSupportActionBar().setTitle(MainActivity.class.getSimpleName());
           valueEventListener=new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   web=new String[(int) dataSnapshot.getChildrenCount()];
                   mThumbIds= new String[(int) dataSnapshot.getChildrenCount()];
                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       u = dataSnapshot1.getValue(Upload.class);
                       web[i] = u.getActivity_name();
                       mThumbIds[i] = u.getImages();
                       i++;

                   }
                   gridView.setAdapter(new ImageAdapter(getActivity(), web, mThumbIds));

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           };







            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent intent=new Intent(getActivity(),ActivityClick.class);
                    intent.putExtra("ActivityName",web[position]);
                    intent.putExtra("ActivityImage",mThumbIds[position]);

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
        mref.removeEventListener(valueEventListener);
    }
}


