package com.example.rushi.epic_thrillon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by dhaval on 18/12/2017.
 */

public class SessionManagement {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginPreference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String FACEBOOK_KEY = "facebook";

    // Email address (make variable public to access from outside)
    public static final String GOOGLE_KEY = "google";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name){
        if(name.equalsIgnoreCase("google")){
            editor.putBoolean(GOOGLE_KEY, true);
        }else if(name.equalsIgnoreCase("facebook")){
            editor.putBoolean(FACEBOOK_KEY, true);

        }else{
            editor.putBoolean(IS_LOGIN, true);
        }
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AskForSignin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN,false);
    }


}
