package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gilortal.djcalendar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    TextView nameEvent,dateEvent,locationEvent,attendingNumEvent,aboutEvent;
    Button songRequestBtn;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_dj_profile, container, false);
        nameEvent = v.findViewById(R.id.name_tv_dj_frag);
        dateEvent = v.findViewById(R.id.date_next_event_tv_dj_frag);
        locationEvent = v.findViewById( R.id.location_next_event_tv_dj_frag);
//        attendingNumEvent;

        return v;
    }

}
