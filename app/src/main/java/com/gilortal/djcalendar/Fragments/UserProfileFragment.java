package com.gilortal.djcalendar.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileFragment extends Fragment implements SendServerResponeToFrags {
    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    private CustomSharePrefAdapter sharedPref;
    ImageView imageUserProf;
    TextView genresUserProf_TV,nameUserProf_TV,
            dateNextEventUserProf_TV, locationNextEventUserProf_TV, followingNumUserProf_TV;
    GridLayout genresUserProf_GL;
    ImageView facebookContactDj_btn,instagramContactDj_btn, twitterContactDj_btn,spotifyContactDj_btn;
    ArrayList<Events> nextEvents;
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
        View v = inflater.inflate(R.layout.fragment_dj_profile, container, false);
        nameUserProf_TV = v.findViewById(R.id.name_tv_user_frag);
        dateNextEventUserProf_TV = v.findViewById(R.id.date_next_event_tv_user_frag);
        locationNextEventUserProf_TV = v.findViewById(R.id.location_next_event_tv_user_frag);
        genresUserProf_TV = v.findViewById(R.id.genre_next_event_tv_user_frag);
        followingNumUserProf_TV = v.findViewById(R.id.follow_num_tv_user_frag);
        genresUserProf_GL = v.findViewById(R.id.genres_gridview_user_frag);
        genresUserProf_GL.removeAllViews();
        facebookContactDj_btn = v.findViewById(R.id.facebook_btn_user_frag);
        instagramContactDj_btn = v.findViewById(R.id.instagram_btn_user_frag);
        twitterContactDj_btn = v.findViewById(R.id.twitter_btn_user_frag);
        spotifyContactDj_btn = v.findViewById(R.id.spotify_btn_user_frag);
        imageUserProf = v.findViewById(R.id.thumbnail_iv_user_frag);


        return v;
    }




//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    public MoveToFrag fragChanger;
//    public UpdateToServer dbUpdater;
//    ArrayList<Events> nextEvents,suggestedEvents;
//    TextView nameUserProf_TV,followNumUserProf_TV,
//            nameNextEventUserProf_TV,dateNextEventUserProf_TV,locationNextEventUserProf_TV,genreNextEventUserProf_TV;
//    ListView suggestedEventListViewUserProf_LV;
//    GridLayout genresUserProf_GL;
//    Button facebookContactUser_Btn,instagramContactUser_btn,twitterContactUser_btn,spotifyContactUser_btn;
//    public RequestDataFromServer requestServer;
//
//    public UserProfileFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((MainActivity)getActivity()).serverToFragsListener = this;
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        ((MainActivity)getActivity()).serverToFragsListener = null;
//
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v =  inflater.inflate(R.layout.fragment_user_profile, container, false);
//
//        nameUserProf_TV = v.findViewById(R.id.name_tv_user_frag);
//        followNumUserProf_TV = v.findViewById(R.id.follow_num_tv_user_frag);
//        genresUserProf_GL = v.findViewById(R.id.genres_gridview_prof_frag);
//        nameNextEventUserProf_TV = v.findViewById(R.id.name_next_event_tv_prof_frag);
//        dateNextEventUserProf_TV = v.findViewById(R.id.date_next_event_tv_prof_frag);
//        locationNextEventUserProf_TV = v.findViewById(R.id.location_next_event_tv_prof_frag);
//        genreNextEventUserProf_TV = v.findViewById(R.id.genre_next_event_tv_prof_frag);
//        suggestedEventListViewUserProf_LV = v.findViewById(R.id.suggested_event_list_view_title_prof_frag);
////        facebookContactUser_Btn = v.findViewById(R.id.facebook_btn_prof_frag);
////        instagramContactUser_btn = v.findViewById(R.id.instagram_btn_prof_frag);
////        twitterContactUser_btn = v.findViewById(R.id.twitter_btn_prof_frag);
////        spotifyContactUser_btn = v.findViewById(R.id.spotify_btn_prof_frag);
//return v;
//    }








    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {
        User userProf = new User(document);
        displayUserProf(userProf);

    }
    private void displayUserProf(User user) {
        //region fetch next events from DB and display first one
        HashMap<String,Object> args = new HashMap();
        args.put(Consts.ARG_DJ_ID,user.getId());
        requestServer.queryFromServer(Consts.REQ_EVENTS_LIST_QUERY,Consts.DJ_PROFILE_FRAG,args);
        //endregion
        nameUserProf_TV.setText(user.getName());

        followingNumUserProf_TV.setText(String.valueOf(user.getNumberOfFollwers()));
        int rows = user.getGenres().size() / 3 ;
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

                    dateNextEventUserProf_TV.setText(nextEvents.get(0).getDate());
                }
        }
    }

}
