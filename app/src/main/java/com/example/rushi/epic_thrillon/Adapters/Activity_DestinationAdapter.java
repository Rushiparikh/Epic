package com.example.rushi.epic_thrillon.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.User;
import com.example.rushi.epic_thrillon.Classes.Wishlist;
import com.example.rushi.epic_thrillon.Destination;
import com.example.rushi.epic_thrillon.MainPages.Home_Page;
import com.example.rushi.epic_thrillon.R;
import com.facebook.Profile;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rushi on 12/16/2017.
 */

public class Activity_DestinationAdapter extends RecyclerView.Adapter<Activity_DestinationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Activity> imageList;
    SharedPreferences sharedPreferences;
    boolean google,facebook,email;
    DatabaseReference mwishlist , mUser;
    Activity destination_activity;
    boolean flag = true;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rupee;
        public ImageView activity_image;
        public ImageButton heart;
        public RatingBar ratingBar;



        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.activity_name);
            rupee=view.findViewById(R.id.activity_rupee);
//            count = (TextView) view.findViewById(R.id.count);
            activity_image = (ImageView) view.findViewById(R.id.activityimage);
//            overflow = (ImageView) view.findViewById(R.id.overflow);
            ratingBar=view.findViewById(R.id.ratingbar);
            heart = view.findViewById(R.id.heart);
            mUser = FirebaseDatabase.getInstance().getReference(Constants.USERS_DATABASE_PATH_UPLOADS);
            acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        }
    }

    public Activity_DestinationAdapter(Context mContext,List<Activity> activityList,SharedPreferences sharedPreferences){
        this.mContext=mContext;
        this.imageList=activityList;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.destination_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

          destination_activity = imageList.get(position);
        holder.name.setText(destination_activity.getActivityName());
        // holder.count.setText(album.getNumOfSongs() + " songs");
       //holder.activity_image.setImageURI(Uri.parse(destination_activity.getImages().getImg1()));

        holder.rupee.setText(destination_activity.getPrice()+"");
        // loading album cover using Glide library

        Glide.with(mContext)
                .load(destination_activity.getImages().getImg1())
                .placeholder(R.drawable.logo) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.activity_image);

        holder.ratingBar.setRating((float)destination_activity.getRating());
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                holder.heart.setImageTintList(ColorStateList.valueOf(Color.RED));
                Toast.makeText(mContext,"Added to Wishlist",Toast.LENGTH_LONG).show();
                facebook = sharedPreferences.getBoolean("Facebook",false);
                google = sharedPreferences.getBoolean("Google",false);
                email = sharedPreferences.getBoolean("Email",false);
                if(facebook){
                    Profile profile =  Profile.getCurrentProfile();
                    final String facebookID = profile.getId();
                    mUser.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                User user= dataSnapshot1.getValue(User.class);

                                if(user.getEmail().equals(facebookID) && flag){
                                    dataSnapshot1.getKey();
                                    Wishlist wishlist = new Wishlist(destination_activity.getActivityId(),destination_activity.getOrganizerId());
                                    String Key= mUser.push().getKey();
                                    mUser.child(dataSnapshot1.getKey()).child("wishlist").child(Key).setValue(wishlist);
                                    flag = false;
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else if(google && acct!=null){

                    final String googleMail = acct.getEmail();
                    mUser.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                User user= dataSnapshot1.getValue(User.class);

                                if(user.getEmail().equals(googleMail) && flag){
                                    dataSnapshot1.getKey();
                                    Wishlist wishlist = new Wishlist(destination_activity.getActivityId(),destination_activity.getOrganizerId());
                                    String Key= mUser.push().getKey();
                                    mUser.child(dataSnapshot1.getKey()).child("wishlist").child(Key).setValue(wishlist);
                                    flag = false;
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{


                }




            }
        });


//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // showPopupMenu(holder.overflow);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
