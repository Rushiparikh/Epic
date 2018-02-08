package com.example.rushi.epic_thrillon.Adapters;

import android.content.Context;
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
import com.example.rushi.epic_thrillon.R;

import java.util.List;

/**
 * Created by dhaval on 24/12/2017.
 */

public class NearByYouAdapter extends  RecyclerView.Adapter<NearByYouAdapter.MyViewHolder>{
    private Context mContext;
    private List<Activity> activityList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView actName,actDest,actPrice;
        public RatingBar actRatingBar;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            actName = (TextView) view.findViewById(R.id.activityName);
            actDest = (TextView) view.findViewById(R.id.actDestination);
            actPrice = (TextView) view.findViewById(R.id.actPrice);
            actRatingBar = view.findViewById(R.id.actRating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public NearByYouAdapter(Context mContext, List<Activity> activityList) {
        this.mContext = mContext;
        this.activityList = activityList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detailing, parent, false);
       return  new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.actName.setText(activity.getActivityName());
        holder.actDest.setText(activity.getDestination());
        holder.actPrice.setText(String.valueOf( activity.getPrice()));
        holder.actRatingBar.setRating((int) activity.getRating());
        //Glide.with(mContext).load(activityList.get(position).getImages().getImg2()).into(holder.thumbnail);

        Glide
                .with(mContext)
                .load(activityList.get(position).getImages().getImg2())
                .placeholder(R.drawable.logo) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.thumbnail);
    }
    @Override
    public int getItemCount() {
        return activityList.size();
    }
}