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

public class PopularDestinationAdapter extends RecyclerView.Adapter<PopularDestinationAdapter.MyViewHolder> {
    private Context mContext;
    private List<Destination> destinationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public PopularDestinationAdapter(Context mContext, List<Destination> activityList) {
        this.mContext = mContext;
        this.destinationsList = activityList;
    }


    @Override
    public PopularDestinationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dest_card, parent, false);
        return  new PopularDestinationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PopularDestinationAdapter.MyViewHolder holder, int position) {
        Destination destination = destinationsList.get(position);
        holder.title.setText(destination.getDestName());

        //Glide.with(mContext).load(activityList.get(position).getImages().getImg2()).into(holder.thumbnail);

        Glide
                .with(mContext)
                .load(destination.getImage())
                .placeholder(R.drawable.logo) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.thumbnail);
    }
    @Override
    public int getItemCount() {
        return destinationsList.size();
    }
    
    
}
