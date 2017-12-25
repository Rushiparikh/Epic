package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class User {

    private BookedActivity bookedActivity;
    private String confirmPass;
    private String email;
    private String firstName;
    private String lastName;
    private long mobileNo;
    private String password;
    private String userId;
    private Wishlist wishlist;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public User(){

    }

    public User(BookedActivity bookedActivity, String confirmPass, String email, String firstName, String lastName, long mobileNo, String password, String userId, Wishlist wishlist) {
        this.bookedActivity = bookedActivity;
        this.confirmPass = confirmPass;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.password = password;
        this.userId = userId;
        this.wishlist = wishlist;
    }

    public BookedActivity getBookedActivity() {
        return bookedActivity;
    }

    public void setBookedActivity(BookedActivity bookedActivity) {
        this.bookedActivity = bookedActivity;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}