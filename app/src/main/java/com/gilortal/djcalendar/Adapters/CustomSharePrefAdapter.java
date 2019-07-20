package com.gilortal.djcalendar.Adapters;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class CustomSharePrefAdapter {

    private SharedPreferences sharedPref;

    private static String PREFS_NAME = "PREFS_NAME";
    private static String MY_USER_ID = "user_id";
    private static String DISPLAY_USER_ID = "DISPLAY__USER_ID";
    private static String NEXT_EVENT_ID = "NEXT_EVENT_ID";
    private static String IS_DJ = "IS_DJ";
    private static String IS_SIGNED_IN = "IS_SIGNED_IN";
    private static String FIRST_INITIALIZE = "FIRST_INITIALIZE";

    public CustomSharePrefAdapter(Context context) { //constructor
        this.sharedPref = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
    }

    //region getters and setters

    public String getMyUserId() {
        return sharedPref.getString(MY_USER_ID,null);
    }

    public  void setMyUserId(String myUserId) {
        sharedPref.edit().putString(MY_USER_ID , myUserId).apply();
    }

    public  String getDisplayUserId() {
        return sharedPref.getString( DISPLAY_USER_ID, null);
    }

    public  void setDisplayUserId(String displayUserId) {
        sharedPref.edit().putString(DISPLAY_USER_ID , displayUserId).apply();
    }

    public  String getNextEventId() {
        return sharedPref.getString(  NEXT_EVENT_ID,null);
    }

    public  void setNextEventId(String nextEventId) {
        sharedPref.edit().putString( NEXT_EVENT_ID , nextEventId).apply();
    }

    public  boolean getIsDj() {
        return sharedPref.getBoolean( IS_DJ,false);
    }

    public  void setIsDj(boolean isDj) {
        sharedPref.edit().putBoolean(IS_DJ , isDj).apply();
    }

    public  boolean IsSignedIn() {
        return sharedPref.getBoolean( IS_SIGNED_IN,false);
    }

    public  void setSignedInStatus(boolean isSignedIn) {
        sharedPref.edit().putBoolean(IS_SIGNED_IN ,isSignedIn).apply();
    }

    public  boolean isFirstInitialize() {
        return sharedPref.getBoolean(FIRST_INITIALIZE,true);
    }

    public  void setFirstInitialize(boolean firstInitialize) {
        sharedPref.edit().putBoolean( FIRST_INITIALIZE , firstInitialize).apply();
    }
    public void clearDisplayProfile(){
        sharedPref.edit()
                .remove(DISPLAY_USER_ID).remove(PREFS_NAME).remove(NEXT_EVENT_ID)
                .remove(MY_USER_ID).putBoolean(IS_SIGNED_IN,false)
                .apply();
    }

    //endregion

}
