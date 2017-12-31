package com.example.rushi.epic_thrillon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Destination;
import com.example.rushi.epic_thrillon.R;

import java.util.List;

/**
 * Created by dhaval on 31/12/2017.
 */

public class DestinationClickAdapter extends RecyclerView.Adapter<DestinationClickAdapter.ViewHolder>{

    private Context context;
    private List<Activity> activityList;
    Activity activity;
    ViewHolder viewHolder;
    View v;


    public DestinationClickAdapter(Context context, List<Activity> activityList) {
        this.activityList = activityList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dest_card, parent, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        activity = activityList.get(position);
        viewHolder =holder;
        viewHolder.textViewName.setText(activity.getActivityName());

        //Glide.with(context).load(destination.getImage()).into(holder.imageView);
        Glide.with(context)
                .load(activity.getImages().getImg1())
                .placeholder(R.drawable.logo) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
