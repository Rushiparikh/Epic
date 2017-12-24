package com.example.rushi.epic_thrillon.Adapters;

/**
 * Created by dhaval on 15/12/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rushi.epic_thrillon.Classes.Destination;
import com.example.rushi.epic_thrillon.R;

import java.util.List;

/**
 * Created by Belal on 2/23/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private Context context;
    private List<Destination> destinations;

    public ActivityAdapter(Context context, List<Destination> destinations) {
        this.destinations = destinations;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dest_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Destination destination = destinations.get(position);

      holder.textViewName.setText(destination.getDestName());

        Glide.with(context).load(destination.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return destinations.size();
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