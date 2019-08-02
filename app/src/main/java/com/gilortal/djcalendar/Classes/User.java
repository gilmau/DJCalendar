package com.gilortal.djcalendar.Classes;

import com.gilortal.djcalendar.Consts;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User  {
    private String id,name,picture_url;
//    facebook,spotify,instagram,twitter;
    private List<String> events_id;

    Map<String,Object> data;
    String about;
    List<String> followingList, genresList;


    public User(DocumentSnapshot user) {
        id = user.getId();
        data = user.getData();
        for(Map.Entry<String, Object> entry : data.entrySet())
        {
            switch(entry.getKey()) {
                case Consts.COLUMN_NAME:
                    name = (String) entry.getValue(); break;
                case Consts.COLUMN_PIC_URL:
                    picture_url = (String) entry.getValue(); break;
                case Consts.COLUMN_ABOUT:
                    about = (String) entry.getValue(); break;
                case Consts.COLUMN_FOLLOWERS_IDS:
                    followingList = (List) entry.getValue(); break;
                case Consts.COLUMN_GENRES:
                    genresList = (List) entry.getValue(); break;
            }

        }

    }

//endregion

    //region getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }



    public List<String> getEvents_id() {
        return events_id;
    }

    public void setEvents_id(List<String> events_id) {
        this.events_id = events_id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getNumberOfFollwers() {
        return followingList.size();
    }

    public List getFollowingList() {
        return followingList;
    }

    public List<String> getGenres() {
        return genresList;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genresList = genres;
    }

//endregion

}
