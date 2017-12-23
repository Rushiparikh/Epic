package com.example.rushi.epic_thrillon.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.R;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final List<Activity> activityList;


    public ImageAdapter(Context c,List<Activity> activityList) {
        mContext = c;
        this.activityList = activityList;
    }

    public int getCount() {
        return activityList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid= new View(mContext);
            grid = inflater.inflate(R.layout.homepage_buttons,null);

            TextView textView = (TextView)grid.findViewById(R.id.home_text);
            ImageView imageview = (ImageView)grid.findViewById(R.id.home_image);
            textView.setText(activityList.get(position).getActivityName());
           // imageview.setImageResource(Imageid[position]);
            Glide.with(getApplicationContext()).load(activityList.get(position).getImages().getImg1()).apply(RequestOptions.circleCropTransform()).into(imageview);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }







}