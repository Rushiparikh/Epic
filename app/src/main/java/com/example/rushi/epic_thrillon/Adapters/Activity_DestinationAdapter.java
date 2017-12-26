package com.example.rushi.epic_thrillon.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Destination;
import com.example.rushi.epic_thrillon.R;

import java.util.List;

/**
 * Created by rushi on 12/16/2017.
 */

public class Activity_DestinationAdapter extends RecyclerView.Adapter<Activity_DestinationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Activity> imageList;
    private RatingBar ratingBar;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rupee;
        public ImageView activity_image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.activity_name);
            rupee=view.findViewById(R.id.activity_rupee);
//            count = (TextView) view.findViewById(R.id.count);
            activity_image = (ImageView) view.findViewById(R.id.activityimage);
//            overflow = (ImageView) view.findViewById(R.id.overflow);
            ratingBar=view.findViewById(R.id.ratingbar);
        }
    }

    public Activity_DestinationAdapter(Context mContext,List<Activity> activityList){
        this.mContext=mContext;
        this.imageList=activityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.destination_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Activity destination_activity = imageList.get(position);
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

        ratingBar.setRating((float)destination_activity.getRating());

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
