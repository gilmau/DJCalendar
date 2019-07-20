package com.gilortal.djcalendar.Classes;

import android.util.EventLog;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class Events {

    private String name,id,location,date,about,picture;
    private ArrayList<String> attending_ids,lineup_ids;

    //region functions
    //TODO: toHash

    //endregion


    //region constructors
    public Events(String name, String id, String location, String date, String about, String picture, ArrayList<String> attending_ids, ArrayList<String> lineup_ids) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.date = date;
        this.about = about;
        this.picture = picture;
        this.attending_ids = attending_ids;
        this.lineup_ids = lineup_ids;
    }

    public Events(DocumentSnapshot event){

//        TODO: construct from snapshot
    }

//endregion

    //region getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<String> getAttending_ids() {
        return attending_ids;
    }

    public void setAttending_ids(ArrayList<String> attending_ids) {
        this.attending_ids = attending_ids;
    }

    public ArrayList<String> getLineup_ids() {
        return lineup_ids;
    }

    public void setLineup_ids(ArrayList<String> lineup_ids) {
        this.lineup_ids = lineup_ids;
    }
    //endregion

}