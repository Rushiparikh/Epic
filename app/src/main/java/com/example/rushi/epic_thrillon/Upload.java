package com.example.rushi.epic_thrillon;

/**
 * Created by dhaval on 15/12/2017.
 */


import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload {

    public String activity_name;
    public String images;
    public String price;
    public String destination;
    public String rating;
    public String activityTime;
    public boolean wishlist;
    public String service;
    public  String description;
    public String review;
    public String organiserName;
    public String mobileNumber;
    public String longitude;
    public String letitude;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String activity_name, String images, String price, String destination, String rating, String activityTime, boolean wishlist, String service, String description, String review, String organiserName, String mobileNumber, String longitude, String letitude) {
        this.activity_name = activity_name;
        this.images = images;
        this.price = price;
        this.destination = destination;
        this.rating = rating;
        this.activityTime = activityTime;
        this.wishlist = wishlist;
        this.service = service;
        this.description = description;
        this.review = review;
        this.organiserName = organiserName;
        this.mobileNumber = mobileNumber;
        this.longitude = longitude;
        this.letitude = letitude;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getImages() {
        return images;
    }

    public String getPrice() {
        return price;
    }

    public String getDestination() {
        return destination;
    }

    public String getRating() {
        return rating;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public boolean isWishlist() {
        return wishlist;
    }

    public String getService() {
        return service;
    }

    public String getDescription() {
        return description;
    }

    public String getReview() {
        return review;
    }

    public String getOrganiserName() {
        return organiserName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLetitude() {
        return letitude;
    }
}