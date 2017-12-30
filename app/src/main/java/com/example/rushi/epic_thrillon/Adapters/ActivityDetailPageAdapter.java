package com.example.rushi.epic_thrillon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rushi.epic_thrillon.Classes.Images;
import com.example.rushi.epic_thrillon.R;

import java.util.ArrayList;

/**
 * Created by dhaval on 29/12/2017.
 */

public class ActivityDetailPageAdapter extends  PageAdapter{
    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ActivityDetailPageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.activity_image_slider, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);

        Glide.with(context).load(images.get(position)).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}



