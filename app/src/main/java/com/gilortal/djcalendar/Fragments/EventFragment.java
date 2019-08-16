package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.RequestDataFromServer;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements SendServerResponeToFrags {

    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    public RequestDataFromServer requestServer;
    public LoginAuth loginAuth;

    TextView nameEvent,dateEvent,locationEvent,attendingNumEvent,aboutEvent;
    ImageView imageEvent;
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
        View v =  inflater.inflate(R.layout.fragment_event, container, false);
        nameEvent = v.findViewById(R.id.name_event_frag);
        dateEvent = v.findViewById(R.id.event_new_form_date);
        locationEvent = v.findViewById( R.id.location_event_frag);
        attendingNumEvent = v.findViewById(R.id.num_attending_event_frag);
        aboutEvent = v.findViewById(R.id.about_tv_event_frag);
        songRequestBtn = v.findViewById(R.id.song_request_btn_event_frag);
        lineup_Event = v.findViewById(R.id.lineup_grid_view_event_frag);
        imageEvent = v.findViewById(R.id.image_event);

        return v;
    }

    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {
        Events event = new Events(document);
        displayEventProf(event);
    }

    @Override
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {

    }

    private void displayEventProf(Events event) {
        //region fetch event's lineup from DB and display in gridview
        HashMap args = new HashMap();
        args.put(Consts.ARG_EVENT_ID,event.getId());
        args.put(Consts.COLUMN_LINEUP_IDS,event.getLineup_ids());
        //requestServer.queryFromServer(Consts.REQ_EVENTS_LIST_QUERY,Consts.USER_PROFILE_FRAG,args);
        //endregion
        Picasso.with(getActivity()).load(String.valueOf(event.getPicture())).into(imageEvent);
        nameEvent.setText(event.getName());
        dateEvent.setText(event.getDate());
        locationEvent.setText(event.getLocation());
        //attendingNumEvent.setText((CharSequence) event.getAttending_ids());
        aboutEvent.setText(String.valueOf(event.getAbout()));
        //lineup_Event TODO: Grid view lineup_Event
    }

}
