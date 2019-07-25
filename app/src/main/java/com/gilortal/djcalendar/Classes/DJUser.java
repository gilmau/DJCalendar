package com.gilortal.djcalendar.Classes;

import com.gilortal.djcalendar.Consts;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DJUser  {
    private String id,name,picture_url,facebook,spotify,instagram,twitter;
    private List<String> events_id;

    Map<String,Object> data;
    String about;
    List<String> follwersList, genresList;


    public DJUser(DocumentSnapshot user) {
        id = user.getId();
//      facebook = user.getString(Consts.COLUMN_FACEBOOK);
//      instagram = user.getString(Consts.COLUMN_INSTAGRAM);
//      twitter = user.getString(Consts.COLUMN_TWITTER);
//      spotify = user.getString(Consts.COLUMN_SPOTIFY);
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
                    follwersList = (List) entry.getValue(); break;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
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
        return follwersList.size();
    }


    public List<String> getGenres() {
        return genresList;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genresList = genres;
    }

//endregion

}
