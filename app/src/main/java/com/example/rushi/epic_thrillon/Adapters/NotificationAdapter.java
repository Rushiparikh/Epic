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
import com.example.rushi.epic_thrillon.Auxiliaries.CircleTransform;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.Destination;
import com.example.rushi.epic_thrillon.R;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by dhaval on 06/01/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context context;
    private List<Activity> activities;
    Activity activity;
    NotificationAdapter.ViewHolder viewHolder;
    View v;


    public NotificationAdapter(Context context, List<Activity> activities) {
        this.activities = activities;
        this.context = context;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);
        viewHolder = new NotificationAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        activity = activities.get(position);
        viewHolder =holder;
        viewHolder.actName.setText(activity.getActivityName());
        viewHolder.actDestName.setText(activity.getDestination());
        viewHolder.actDate.setText(activity.getActivityDate());
        viewHolder.actTiming.setText(activity.getActivityTime());

        //Glide.with(context).load(destination.getImage()).into(holder.imageView);
        Glide.with(context)
                .load(activity.getImages().getImg4())
                .placeholder(R.drawable.logo) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .transform(new CircleTransform(getApplicationContext()))
                .into(viewHolder.actImage);


    }

    @Override
    public int getItemCount() {
        return activities.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView actName,actDate,actTiming,actDestName;
        public ImageView actImage;

        public ViewHolder(View itemView) {
            super(itemView);
            actDestName = (TextView) itemView.findViewById(R.id.actDestName);
            actName = (TextView) itemView.findViewById(R.id.actName);
            actTiming = (TextView) itemView.findViewById(R.id.actTiming);
            actDate = (TextView) itemView.findViewById(R.id.actDate);
            actImage = (ImageView) itemView.findViewById(R.id.actImage);
        }
    }
}

