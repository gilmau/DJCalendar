package com.gilortal.djcalendar.Classes;

import android.util.EventLog;

import com.gilortal.djcalendar.Consts;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Events {

    private String name, id, location, date, about, picture;
    private ArrayList<String> attending_ids, lineup_ids,genres;
    Map<String,Object> data;


    //region constructors

    public Events(DocumentSnapshot event) {
        id = event.getId();
        data = event.getData();
        for(Map.Entry<String, Object> entry : data.entrySet())
        {
            switch(entry.getKey()) {
                case Consts.COLUMN_NAME:
                    name = (String) entry.getValue(); break;
                case Consts.COLUMN_PIC_URL:
                    picture = (String) entry.getValue(); break;
                case Consts.COLUMN_ABOUT:
                    about = (String) entry.getValue(); break;
                case Consts.COLUMN_DATE:
                    date = (String) entry.getValue(); break;
                case Consts.COLUMN_LOCATION:
                    location = (String) entry.getValue(); break;

            }

        }

    }
//    public Events(DocumentSnapshot event) {
////        name,id,location,date,about,picture; //strings
////        attending_ids,lineup_ids; //array lists
//        try {id = event.getId(); }catch (Exception e) { e.printStackTrace(); }
//        try {name = event.getString(Consts.COLUMN_NAME);
//        }catch (Exception e) { e.printStackTrace();name = "No Name"; }
//        try {location = event.getString(Consts.COLUMN_LOCATION); }
//        catch (Exception e) { e.printStackTrace();location = "No Location"; }
//        try {date = event.getString(Consts.COLUMN_DATE); }
//        catch (Exception e) { e.printStackTrace();date = "No Date"; }
//        try {about = event.getString(Consts.COLUMN_ABOUT);
//        }catch (Exception e) {e.printStackTrace();about = "No About"; }
//        try { picture = event.getString(Consts.COLUMN_PIC_URL);
//        }catch (Exception e) {e.printStackTrace();picture = "No Picture"; }
////        try {attending_ids = (ArrayList<String>) event.get(Consts.COLUMN_ATTENDING_IDS);
////        }catch (Exception e) {e.printStackTrace();attending_ids = new ArrayList<>(); }
////        try {lineup_ids = (ArrayList<String>) event.get(Consts.COLUMN_LINEUP_IDS);
////        }catch (Exception e) {e.printStackTrace();lineup_ids = new ArrayList<>(); }
////        try {genres = (ArrayList<String>)event.get(Consts.COLUMN_GENRES);}
////        catch (Exception e){e.printStackTrace();genres = new ArrayList<>();}
//
//
//
////        TODO: construct from snapshot
//    }

//endregion

    //region getters and setters


    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

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
