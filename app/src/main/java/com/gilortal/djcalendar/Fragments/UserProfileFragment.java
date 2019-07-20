package com.gilortal.djcalendar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    TextView nameUserProf_TV,followNumUserProf_TV,genres_UserProf_TV,
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
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {

    }

}
