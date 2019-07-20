package com.gilortal.djcalendar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Classes.User;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.RequestDataFromServer;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class UserProfileFragment extends Fragment implements SendServerResponeToFrags {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;

    TextView nameUserProf_TV,followNumUserProf_TV,
            nameNextEventUserProf_TV,dateNextEventUserProf_TV,locationNextEventUserProf_TV,genreNextEventUserProf_TV;
    ListView suggestedEventListViewUserProf_LV;
    GridView genresUserProf_GV;
    Button facebookContactUser_Btn,instagramContactUser_btn,twitterContactUser_btn,spotifyContactUser_btn;
    public RequestDataFromServer requestServer;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).serverToFragsListener = this;

    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).serverToFragsListener = null;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_profile, container, false);
        nameUserProf_TV = v.findViewById(R.id.name_tv_user_frag);
        followNumUserProf_TV = v.findViewById(R.id.follow_num_tv_user_frag);
        genresUserProf_GV = v.findViewById(R.id.genres_gridview_prof_frag);
        nameNextEventUserProf_TV = v.findViewById(R.id.name_next_event_tv_prof_frag);
        dateNextEventUserProf_TV = v.findViewById(R.id.date_next_event_tv_prof_frag);
        locationNextEventUserProf_TV = v.findViewById(R.id.location_next_event_tv_prof_frag);
        genreNextEventUserProf_TV = v.findViewById(R.id.genre_next_event_tv_prof_frag);
        suggestedEventListViewUserProf_LV = v.findViewById(R.id.suggested_event_list_view_title_prof_frag);
        facebookContactUser_Btn = v.findViewById(R.id.facebook_btn_prof_frag);
        instagramContactUser_btn = v.findViewById(R.id.instagram_btn_prof_frag);
        twitterContactUser_btn = v.findViewById(R.id.twitter_btn_prof_frag);
        spotifyContactUser_btn = v.findViewById(R.id.spotify_btn_prof_frag);
        return v;
    }





    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {

    }



    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {
        User userProf = new User(document);
        displayUserProf(userProf);


    }
    private void displayUserProf(User userProf) {
        //create event display method from server
        nameUserProf_TV.setText(userProf.getName());
        followNumUserProf_TV.setText(userProf.getFollowing().size());
        for (String genre:userProf.getGenres()) {
            TextView genreTV = new TextView(getContext());
            genreTV.setText(genre);
            genreTV.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            genreTV.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            genreTV.setGravity(Gravity.CENTER);
            genresUserProf_GV.addView(genreTV);
        }
        facebookContactUser_Btn.setTag(0,userProf.getFacebook());
        instagramContactUser_btn.setTag(0,userProf.getInstagram());
        twitterContactUser_btn.setTag(0,userProf.getTwitter());
        spotifyContactUser_btn.setTag(0,userProf.getSpotify());
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {


        //lineup_Event TODO: Grid view lineup_Event
    }
    //TextView nameUserProf_TV,followNumUserProf_TV,
     //       nameNextEventUserProf_TV,dateNextEventUserProf_TV,locationNextEventUserProf_TV,genreNextEventUserProf_TV;
    //ListView suggestedEventListViewUserProf_LV;
   // GridView genresUserProf_GV;
    //Button facebookContactUser_Btn,instagramContactUser_btn,twitterContactUser_btn,spotifyContactUser_btn;


}
