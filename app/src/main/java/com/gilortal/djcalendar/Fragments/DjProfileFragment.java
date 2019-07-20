package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 */
public class DjProfileFragment extends Fragment implements SendServerResponeToFrags {

    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    private CustomSharePrefAdapter sharedPref;
    TextView genresNextEventDjProf_TV,nameNextEventDjProf_TV, dateNextEventDjProf_TV, locationNextEventDjProf_TV, followerNumDjProf_TV, aboutDjProf_TV;
    GridView genresDjProf_GV;
    Button facebookContactDj_btn,instagramContactDj_btn, twitterContactDj_btn,spotifyContactDj_btn;




    public DjProfileFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_dj_profile, container, false);
        nameNextEventDjProf_TV = v.findViewById(R.id.name_tv_dj_frag);
        dateNextEventDjProf_TV = v.findViewById(R.id.date_next_event_tv_dj_frag);
        locationNextEventDjProf_TV = v.findViewById( R.id.location_next_event_tv_dj_frag);
        genresNextEventDjProf_TV = v.findViewById(R.id.genre_next_event_tv_dj_frag);
        followerNumDjProf_TV = v.findViewById(R.id.follow_ro_tv_dj_frag);
        aboutDjProf_TV = v.findViewById(R.id.about_dj_frag);
        genresDjProf_GV = v.findViewById(R.id.genres_gridlayout_dj_frag);
        facebookContactDj_btn = v.findViewById(R.id.facebook_btn_dj_frag);
        instagramContactDj_btn = v.findViewById(R.id.instagram_btn_dj_frag);
        twitterContactDj_btn = v.findViewById(R.id.twitter_btn_dj_frag);
        spotifyContactDj_btn = v.findViewById(R.id.spotify_btn_dj_frag);
        return v;
    }

    @Override
    public void BroadcastSnapShot(DocumentSnapshot document) {
        DJUser djUser= new DJUser(document);
        displayDjProf(djUser);

    }

    private void displayDjProf(DJUser djUser) {
        //create event display method from server
        nameNextEventDjProf_TV.setText(djUser.getName());
        followerNumDjProf_TV.setText(djUser.getFollowing().size());
        aboutDjProf_TV.setText(djUser.getAbout());
        for (String genre:djUser.getGenres()) {
            TextView genreTV = new TextView(getContext());
            genreTV.setText(genre);
            genreTV.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            genreTV.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            genreTV.setGravity(Gravity.CENTER);
            genresDjProf_GV.addView(genreTV);
        }
        facebookContactDj_btn.setTag(0,djUser.getFacebook());
        instagramContactDj_btn.setTag(0,djUser.getInstagram());
        twitterContactDj_btn.setTag(0,djUser.getTwitter());
        spotifyContactDj_btn.setTag(0,djUser.getSpotify());
    }
}
