package com.example.rushi.epic_thrillon;

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

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final List<String> web;
    private final List<String> Imageid;

    public ImageAdapter(Context c,List<String> web ,List<String> Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    public int getCount() {
        return web.size();
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
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(95, 95));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);

            grid= new View(mContext);
            grid = inflater.inflate(R.layout.homepage_buttons,null);

            TextView textView = (TextView)grid.findViewById(R.id.home_text);
            ImageView imageview = (ImageView)grid.findViewById(R.id.home_image);
            textView.setText(web.get(position));


           // imageview.setImageResource(Imageid[position]);
            Glide.with(getApplicationContext()).load(decodeFromBase64ToBitmap(Imageid.get(position))).apply(RequestOptions.circleCropTransform()).into(imageview);


        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }





}