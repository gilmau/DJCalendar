package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements SendServerResponeToFrags {

    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;

    TextView nameEvent,dateEvent,locationEvent,attendingNumEvent,aboutEvent;
    Button songRequestBtn;
    GridView lineup_Event;
    public EventFragment() {
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
        nameEvent = v.findViewById(R.id.name_tv_dj_frag);
        dateEvent = v.findViewById(R.id.date_next_event_tv_dj_frag);
        locationEvent = v.findViewById( R.id.location_next_event_tv_dj_frag);
        attendingNumEvent = v.findViewById(R.id.num_attending_event_frag);
        aboutEvent = v.findViewById(R.id.about_tv_event_frag);
        songRequestBtn = v.findViewById(R.id.song_request_btn_event_frag);
        lineup_Event = v.findViewById(R.id.lineup_grid_view_event_frag);
        return v;
    }

    @Override
    public void BroadcastSnapShot(DocumentSnapshot document) {
        Events event = new Events(document);
        displayEventProf(event);
    }
    private void displayEventProf(Events event) {
        nameEvent.setText(event.getName());
        dateEvent.setText(event.getDate());
        locationEvent.setText(event.getLocation());
        attendingNumEvent.setText((CharSequence) event.getAttending_ids());
        aboutEvent.setText(event.getAbout());
        //lineup_Event TODO: Grid view lineup_Event





    }
}
