package com.gilortal.djcalendar.Fragments;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.RequestDataFromServer;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DjProfileFragment extends Fragment implements SendServerResponeToFrags {

    FirebaseFirestore db;
    DocumentReference documentReference;
    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    private CustomSharePrefAdapter sharedPref;
    public SendServerResponeToFrags serverResponeToFrags;
    ImageView imageDjProf;
    TextView genresNextEventDjProf_TV,nameDjProf_TV,
            dateNextEventDjProf_TV, locationNextEventDjProf_TV, followersNumDjProf_TV, aboutDjProf_TV;
    GridLayout genresDjProf_GL;
    Button followButton;
    ImageView facebookContactDj_btn,instagramContactDj_btn, twitterContactDj_btn,spotifyContactDj_btn;
    ArrayList<Events> nextEvents;
    ArrayList<String> followers;
    ArrayList<String> userFollowing;
    FirebaseUser currentUser;
    String djKey;
    public RequestDataFromServer requestServer;
    HashMap<String,Object> args = new HashMap();


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
        nameDjProf_TV = v.findViewById(R.id.name_tv_dj_frag);
        sharedPref = new CustomSharePrefAdapter(getActivity().getBaseContext());
        dateNextEventDjProf_TV = v.findViewById(R.id.date_next_event_tv_dj_frag);
        locationNextEventDjProf_TV = v.findViewById( R.id.location_next_event_tv_dj_frag);
        genresNextEventDjProf_TV = v.findViewById(R.id.genre_next_event_tv_dj_frag);
        followersNumDjProf_TV = v.findViewById(R.id.follow_num_tv_dj_frag);
        aboutDjProf_TV = v.findViewById(R.id.about_dj_frag);
        genresDjProf_GL = v.findViewById(R.id.genres_gridlayout_dj_frag);
        genresDjProf_GL.removeAllViews();
        followButton = v.findViewById(R.id.follow_button);
        facebookContactDj_btn = v.findViewById(R.id.facebook_btn_dj_frag);
        instagramContactDj_btn = v.findViewById(R.id.instagram_btn_dj_frag);
        twitterContactDj_btn = v.findViewById(R.id.twitter_btn_dj_frag);
        spotifyContactDj_btn = v.findViewById(R.id.spotify_btn_dj_frag);
        imageDjProf = v.findViewById(R.id.thumbnail_iv_dj_frag);
        documentReference = db.collection(Consts.DB_USERS).document(sharedPref.getMyUserId());


//        mainActivity = (MainActivity) getActivity();
//
//        if(mainActivity.sharedPref.IsSignedIn() && !mainActivity.sharedPref.getIsDj()) {
//
//
//
//        }



        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    djKey = String.valueOf(args.get(Consts.ARG_DJ_ID));
                    followers.add(sharedPref.getMyUserId());
                    followersNumDjProf_TV.setText(String.valueOf(followers.size()));
                    userFollowing.add(djKey);

                    db.collection((Consts.DB_DJS)).document(djKey).
                            update(Consts.COLUMN_FOLLOWERS_IDS, followers);

                    db.collection(Consts.DB_USERS).document(sharedPref.getMyUserId()).
                            update(Consts.COLUMN_FOLLOWING_IDS, userFollowing);

            }
        });



        return v;


    }




    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {
        DJUser djUser= new DJUser(document);
        displayDjProf(djUser);

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
                    nameDjProf_TV.setText(nextEvents.get(0).getName());
                    locationNextEventDjProf_TV.setText(nextEvents.get(0).getLocation());
                    genresNextEventDjProf_TV.setText(nextEvents.get(0).getGenres().toString());
                    dateNextEventDjProf_TV.setText(nextEvents.get(0).getDate());
                }
        }
    }

    private void getFollowingOfUser() {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userFollowing = (ArrayList) documentSnapshot.get(Consts.COLUMN_FOLLOWERS_IDS);

            }
        });

    }



    private void displayDjProf(DJUser djUser) {
        //region fetch next events from DB and display first one
       // HashMap<String,Object> args = new HashMap();
        args.put(Consts.ARG_DJ_ID,djUser.getId());
        requestServer.queryFromServer(Consts.REQ_EVENTS_LIST_QUERY,Consts.DJ_PROFILE_FRAG,args);
        //endregion
        nameDjProf_TV.setText(djUser.getName());
        aboutDjProf_TV.setText(djUser.getAbout());
        Picasso.with(getActivity()).load(djUser.getPicture_url()).into(imageDjProf);
        followersNumDjProf_TV.setText(String.valueOf(djUser.getNumberOfFollwers()));

        int rows = djUser.getGenres().size() / 1 ;
        for (int j = 0 , c = 0, r = 0 ; j < djUser.getGenres().size() ; j++, c++ /*String genre: djUser.getGenres()*/) {
            TextView genreTV = new TextView(getContext());
            genreTV.setText(djUser.getGenres().get(j));
            GridLayout.LayoutParams layoutParams  = new GridLayout.LayoutParams();
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.rightMargin = 5;
            layoutParams.leftMargin = 5;
            layoutParams.setGravity(Gravity.CENTER);

            layoutParams.columnSpec = GridLayout.spec(c);
            layoutParams.rowSpec = GridLayout.spec(r);
            genreTV.setLayoutParams(layoutParams);
            genresDjProf_GL.addView(genreTV);

            if(sharedPref.IsSignedIn() && !sharedPref.getIsDj()) {
                userFollowing = new ArrayList<>();
                getFollowingOfUser();
                if(userFollowing.contains(djUser.getId())) {
                    followButton.setClickable(false);
                }




            }

        }

    }



}
