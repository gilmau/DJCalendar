package com.gilortal.djcalendar.Fragments;


import android.app.usage.UsageDjProfs;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.DjProfs;
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







        return v;


    }

    @Override
    public void BroadcastSnapShot(DocumentSnapshot document) {
        DjProfs DjProf= new DjProfs(document);
        displayDjProf(DjProf);

    }

    private void displayDjProf(DjProfs DjProf) {

    }
}
