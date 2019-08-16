package com.gilortal.djcalendar.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Classes.User;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.RequestDataFromServer;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileFragment extends Fragment implements SendServerResponeToFrags {

    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    private CustomSharePrefAdapter sharedPref;
    ImageView imageUserProf;
    TextView genresNextEventUserProf_TV,nameUserProf_TV,
            dateNextEventUserProf_TV, locationNextEventUserProf_TV, followingNumUserProf_TV;
    GridLayout genresUserProf_GL;
    ImageView facebookContactUser_btn,instagramContactUser_btn, twitterContactUser_btn,spotifyContactUser_btn;
    ArrayList<Events> nextEvents;
    ArrayList<String> following;
    FirebaseUser currentUser;
    int NumbersOfFollowing;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_profile, container, false);
        nameUserProf_TV = v.findViewById(R.id.name_tv_user_frag);
        sharedPref = new CustomSharePrefAdapter(getActivity().getBaseContext());
        dateNextEventUserProf_TV = v.findViewById(R.id.date_next_event_tv_user_frag);
        locationNextEventUserProf_TV = v.findViewById( R.id.location_next_event_tv_user_frag);
        genresNextEventUserProf_TV = v.findViewById(R.id.genre_next_event_tv_user_frag);
        followingNumUserProf_TV = v.findViewById(R.id.follow_num_tv_user_frag);
        genresUserProf_GL = v.findViewById(R.id.genres_gridlayout_user_frag);
        genresUserProf_GL.removeAllViews();
        facebookContactUser_btn = v.findViewById(R.id.facebook_btn_user_frag);
        instagramContactUser_btn = v.findViewById(R.id.instagram_btn_user_frag);
        twitterContactUser_btn = v.findViewById(R.id.twitter_btn_user_frag);
        spotifyContactUser_btn = v.findViewById(R.id.spotify_btn_user_frag);
        imageUserProf = v.findViewById(R.id.thumbnail_iv_user_frag);

        return v;

    }


    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {
        User user = new User(document);
        displayUserProf(user);

    }

    @Override
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {
        switch (requestCode){
            case Consts.REQ_EVENTS_LIST_QUERY:
                //result is arraylist of events
                try{nextEvents =  queryResult;} catch (Exception e ){e.printStackTrace();}
                if (nextEvents != null && nextEvents.size() > 0){
                    //display 1st to next event
                    //add rest of events to list of events
                    nameUserProf_TV.setText(nextEvents.get(0).getName());
                    locationNextEventUserProf_TV.setText(nextEvents.get(0).getLocation());
                    genresNextEventUserProf_TV.setText(nextEvents.get(0).getGenres().toString());
                    dateNextEventUserProf_TV.setText(nextEvents.get(0).getDate());
                }
        }
    }





    private void displayUserProf(User user) {
        //region fetch next events from DB and display first one
        HashMap<String,Object> args = new HashMap();
        args.put(Consts.ARG_USER_ID, user.getId());
        requestServer.queryFromServer(Consts.REQ_EVENTS_LIST_QUERY,Consts.USER_PROFILE_FRAG,args);
        //endregion
        nameUserProf_TV.setText(user.getName());
        Picasso.with(getActivity()).load(user.getPicture_url()).into(imageUserProf);
        followingNumUserProf_TV.setText(String.valueOf(user.getNumberOfFollwers()));

        int rows = user.getGenres().size() / 1 ;
        for (int j = 0 , c = 0, r = 0 ; j < user.getGenres().size() ; j++, c++ /*String genre: djUser.getGenres()*/) {
            TextView genreTV = new TextView(getContext());
            genreTV.setText(user.getGenres().get(j));
            GridLayout.LayoutParams layoutParams  = new GridLayout.LayoutParams();
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.rightMargin = 5;
            layoutParams.leftMargin = 5;
            layoutParams.setGravity(Gravity.CENTER);

            layoutParams.columnSpec = GridLayout.spec(c);
            layoutParams.rowSpec = GridLayout.spec(r);
            genreTV.setLayoutParams(layoutParams);
            genresUserProf_GL.addView(genreTV);

        }

    }
}
