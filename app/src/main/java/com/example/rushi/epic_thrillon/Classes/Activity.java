package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 21/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class Activity {

    private String activityDate;
    private String activityId;
    private String activity_name;
    private String activityTime;
    private String description;
    private String destination;
    private Images images;
    private Location location;
    private long organizerContact;
    private String organizerId;
    private String organizerName;
    private double price;
    private PublicReviews publicReviews;
    private double rating;
    private Service service;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Activity() {
    }

    public Activity(String activityDate, String activityId, String activity_name, String activityTime, String description, String destination, Images images, Location location, long organizerContact, String organizerId, String organizerName, double price, PublicReviews publicReviews, double rating, Service service) {
        this.activityDate = activityDate;
        this.activityId = activityId;
        this.activity_name = activity_name;
        this.activityTime = activityTime;
        this.description = description;
        this.destination = destination;
        this.images = images;
        this.location = location;
        this.organizerContact = organizerContact;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.price = price;
        this.publicReviews = publicReviews;
        this.rating = rating;
        this.service = service;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activity_name;
    }

    public void setActivityName(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getOrganizerContact() {
        return organizerContact;
    }

    public void setOrganizerContact(long organizerContact) {
        this.organizerContact = organizerContact;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PublicReviews getPublicReviews() {
        return publicReviews;
    }

    public void setPublicReviews(PublicReviews publicReviews) {
        this.publicReviews = publicReviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}