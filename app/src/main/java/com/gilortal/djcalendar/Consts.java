package com.gilortal.djcalendar;

public class Consts {

    /*******FRAGMENTS ENUM********/
    public static final int LOGIN_SCREEN_FRAG = 0;
    public static final int SIGNUP_FORM_FRAG = 1;
    public static final int DJ_PROFILE_FRAG = 2;
    public static final int USER_PROFILE_FRAG = 3;
    public static final int EVENT_FRAG = 4;

    /*****ARGS FOR HASH******/
    public static final String ARG_DJ_ID = "ARG_DJ_ID";
    public static final String ARG_USER_ID = "ARG_USER_ID";
    public static final String ARG_EVENT_ID = "ARG_EVENT_ID";


    /*****TABLES_NAME******/
    public static final String DB_DJS = "DJ Profiles DB";
    public static final String DB_USERS = "Users Profiles DB";
    public static final String DB_EVENTS = "Events Profile DB";

    /*****COLUMNS NAMES****/
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_FOLLOWERS_IDS = "Followers";
    public static final String COLUMN_FOLLOWING_IDS = "Following";
    public static final String COLUMN_ABOUT = "About";
    public static final String COLUMN_PIC_URL = "Picture Url";
    public static final String COLUMN_FACEBOOK = "Facebook";
    public static final String COLUMN_SPOTIFY = "Spotify";
    public static final String COLUMN_INSTAGRAM = "Instagram";
    public static final String COLUMN_TWITTER = "Twitter";
    public static final String COLUMN_GENRES = "Genres";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_ATTENDING_IDS = "Attending";
    public static final String COLUMN_LINEUP_IDS = "Attending";
    public static final String COLUMN_EVENTS_ID = "events_id";
    public static final String COLUMN_FOLLOWING = "following";

    /********Requset from server codes**********/
    public static final int REQ_EVENTS_LIST_QUERY = 0;
    public static final int REQ_SUGGESTED_EVENTS = 1;
    public static final int REQ_LINEUP_DJ_INFO = 2;
    public static final int REQ_FOLLOWERS_INFO = 3;
    public static final int REQ_ATTENDERS_INFO = 4;
    public static final int REQ_NEXT_EVENT = 5;




    /********GENRES**********/
    public static final String GENRES_ROCK = "Rock";
    public static final String GENRES_POP = "Pop";
    public static final String GENRES_ELECTRONIC = "Electronic";
    public static final String GENRES_RAGGAE = "Reggae";
    public static final String GENRES_ISRAELI = "Israeli Music";
    public static final String GENRES_HIP_HOP = "Hip Hop";



}
