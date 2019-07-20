package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gilortal.djcalendar.Interfaces.AccessDjDB;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DjProfileFragment extends Fragment {

    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;


    public DjProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dj_profile, container, false);
//fragChanger.showFrag();
    }

}
