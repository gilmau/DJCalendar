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

import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Classes.User;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment implements SendServerResponeToFrags {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public MoveToFrag fragChanger;
    public UpdateToServer dbUpdater;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView nameUserProf_TV,followNumUserProf_TV,
            nameNextEventUserProf_TV,dateNextEventUserProf_TV,locationNextEventUserProf_TV,genreNextEventUserProf_TV;
    ListView suggestedEventListViewUserProf_LV;
    GridView genresUserProf_GV;
    Button facebookContactUser_Btn,instagramContactUser_btn,twitterContactUser_btn,spotifyContactUser_btn;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void BroadcastSnapShot(DocumentSnapshot document) {
        User userProf = new User(document);
        displayUserProf(userProf);


    }
    private void displayUserProf(User userProf) {
        nameUserProf_TV.setText(userProf.getName());
        followNumUserProf_TV.setText( userProf.getFollowing().size());


        //lineup_Event TODO: Grid view lineup_Event
    }
    //TextView nameUserProf_TV,followNumUserProf_TV,
     //       nameNextEventUserProf_TV,dateNextEventUserProf_TV,locationNextEventUserProf_TV,genreNextEventUserProf_TV;
    //ListView suggestedEventListViewUserProf_LV;
   // GridView genresUserProf_GV;


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
