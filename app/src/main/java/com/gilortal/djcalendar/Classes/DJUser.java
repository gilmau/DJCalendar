package com.gilortal.djcalendar.Classes;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class DJUser extends User {
    String about;


    //region functions
    //TODO: toHash

    //endregion

    //region constructors

    public DJUser(String id, String name, String picture_url, String facebook, String spotify, String instagram, String twitter, ArrayList<String> events_id, ArrayList<String> genres, ArrayList<String> following, String about) {
        super(id, name, picture_url, facebook, spotify, instagram, twitter, events_id, genres, following);
        this.about = about;
    }

    public DJUser(String id, String name, String picture_url, ArrayList<String> events_id, ArrayList<String> genres) {
        super(id, name, picture_url, events_id, genres);
    }

    public DJUser(String id, String name, String picture_url, String facebook, String spotify, String instagram, String twitter, ArrayList<String> events_id, ArrayList<String> genres, ArrayList<String> following) {
        super(id, name, picture_url, facebook, spotify, instagram, twitter, events_id, genres, following);
    }

    public DJUser(DocumentSnapshot user) {
        super(user);
    }
//endregion

    //region getters and setters
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
//endregion

}
