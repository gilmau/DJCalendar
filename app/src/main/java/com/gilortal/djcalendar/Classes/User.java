package com.gilortal.djcalendar.Classes;

import com.gilortal.djcalendar.Consts;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class User {
    private String id,name,picture_url,facebook,spotify,instagram,twitter;
    private ArrayList<String> events_id,genres,following;


    //region functions
    //TODO: toHash

    //endregion

    //region constructors

    public User(String id, String name, String picture_url, ArrayList<String> events_id, ArrayList<String> genres) {
        this.id = id;
        this.name = name;
        this.picture_url = picture_url;
        this.events_id = events_id;
        this.genres = genres;
        following = null;
        facebook = null;
        instagram = null;
        spotify = null;
        twitter = null;
    }

    public User(String id, String name, String picture_url, String facebook, String spotify, String instagram, String twitter, ArrayList<String> events_id, ArrayList<String> genres, ArrayList<String> following) {
        this.id = id;
        this.name = name;
        this.picture_url = picture_url;
        this.facebook = facebook;
        this.spotify = spotify;
        this.instagram = instagram;
        this.twitter = twitter;
        this.events_id = events_id;
        this.genres = genres;
        this.following = following;
    }

    public User(DocumentSnapshot user){
        //TODO: build constructor from snapshot
       // id,name,picture_url,facebook,spotify,instagram,twitter;//string
       //   events_id,genres,following;//ArrayList
        try {
            id = user.getId(); }catch (Exception e) { e.printStackTrace(); }
        try {
            name = user.getString(Consts.COLUMN_NAME); } catch (Exception e) { e.printStackTrace();name = "No Name"; }
        try {
            picture_url = user.getString(Consts.COLUMN_PIC_URL); } catch (Exception e) { e.printStackTrace();name = "No Url"; }
        try {
            facebook = user.getString(Consts.COLUMN_FACEBOOK); } catch (Exception e) { e.printStackTrace();name = "No Facebook"; }
        try {
            spotify = user.getString(Consts.COLUMN_SPOTIFY); } catch (Exception e) { e.printStackTrace();name = "No Spotify"; }
        try {
            instagram = user.getString(Consts.COLUMN_INSTAGRAM); } catch (Exception e) { e.printStackTrace();name = "No Instagram"; }
        try {
            twitter = user.getString(Consts.COLUMN_TWITTER); } catch (Exception e) { e.printStackTrace();name = "No Twitter"; }
        try {
            events_id = (ArrayList<String>) user.get(Consts.COLUMN_EVENS_ID); }catch (Exception e) { e.printStackTrace();events_id = new ArrayList<>(); }
        try {
            genres = (ArrayList<String>) user.get(Consts.COLUMN_GENRES); }catch (Exception e) { e.printStackTrace();genres = new ArrayList<>(); }
        try {
            following = (ArrayList<String>) user.get(Consts.COLUMN_FOLLOWING); }catch (Exception e) { e.printStackTrace();following = new ArrayList<>(); }
    }
 //endregion

    //region getters and setters

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

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

    public ArrayList<String> getEvents_id() {
        return events_id;
    }

    public void setEvents_id(ArrayList<String> events_id) {
        this.events_id = events_id;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

//    endregion
}
