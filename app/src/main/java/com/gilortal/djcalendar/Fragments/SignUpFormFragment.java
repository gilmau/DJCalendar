package com.gilortal.djcalendar.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFormFragment extends Fragment implements View.OnClickListener, SendServerResponeToFrags {

    public LoginAuth loginAuth;
    TextInputEditText nameText;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextInputEditText confirmPasswordText;
    CheckBox isDJCheckBox;
    EditText aboutBox;
    Button confirmBox;
    CheckBox electronicGenre, rockGenre, popGenre, reggaeGenre, hiphopGenre, israelGenre;
    List<String> checkedGenres = new ArrayList<>();
    List<String> followersList, followingList;
    ImageView profilePic;
    final static int GALLERY_PICK = 1;
    final static int RESULT_OK = 1;


    String name = "", email = "", password = "", confirmPassword = "";
    boolean isDJ = false;


    public SignUpFormFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_signupform, container, false);

        container.clearDisappearingChildren();


        confirmBox = v.findViewById(R.id.confirmBox);
        nameText = v.findViewById(R.id.name_sign_up_form_ID);
        emailText = v.findViewById(R.id.email_sign_up_form_ID);
        passwordText = v.findViewById(R.id.password_sign_up_form_ID);
        confirmPasswordText = v.findViewById(R.id.password_confirm_sign_up_form_ID);
        isDJCheckBox = v.findViewById(R.id.dj_or_not_button_ID);
        aboutBox = v.findViewById(R.id.about_text_id);
        profilePic = v.findViewById(R.id.profile_picture_sign_up_id);
        electronicGenre = v.findViewById(R.id.checkedbox_electronic_id);
        rockGenre = v.findViewById(R.id.checkedbox_rock_id);
        popGenre = v.findViewById(R.id.checkedbox_pop_id);
        reggaeGenre = v.findViewById(R.id.checkedbox_reggae_id);
        hiphopGenre = v.findViewById(R.id.checkedbox_hiphop_id);
        israelGenre = v.findViewById(R.id.checkedbox_israel_id);
        rockGenre.setOnClickListener(this);
        popGenre.setOnClickListener(this);
        reggaeGenre.setOnClickListener(this);
        hiphopGenre.setOnClickListener(this);
        israelGenre.setOnClickListener(this);
        electronicGenre.setOnClickListener(this);





        isDJCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDJ = ((CheckBox)v).isChecked();
                if (isDJ)
                    aboutBox.setVisibility(View.VISIBLE);
                else
                    aboutBox.setVisibility(View.GONE);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_PICK);
            }
        });



        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{name = String.valueOf( nameText.getText());}catch (Exception e){e.printStackTrace();}
                try{password =String.valueOf( passwordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{confirmPassword = String.valueOf( confirmPasswordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{email = emailText.getText().toString();}catch (Exception e ){e.printStackTrace();}
             //  Toast.makeText(getActivity().getBaseContext(), password + " " + confirmPassword, Toast.LENGTH_LONG).show();

                if (name == "" || email == "" || password == "" || confirmPassword == "") {

                    Toast.makeText(getActivity().getBaseContext(), "Please Insert All Mandatory fields", Toast.LENGTH_LONG).show();
                }

                else if (!(password.equals(confirmPassword))) {

                    Toast.makeText(getActivity().getBaseContext(), "Please confirm again the password", Toast.LENGTH_LONG).show();
                }
                else {

                    HashMap<String, Object> userData = new HashMap();

                    userData.put(Consts.COLUMN_NAME, name);
                    userData.put(Consts.COLUMN_EMAIL, email);
                    userData.put(Consts.COLUMN_PASSWORD, password);
                    userData.put(Consts.COLUMN_GENRES,checkedGenres );
                    userData.put(Consts.COLUMN_PIC_URL, "");


                    if (isDJ){
                        userData.put(Consts.COLUMN_ABOUT, aboutBox.getText().toString());
                        followersList = new ArrayList<>();
                        userData.put(Consts.COLUMN_FOLLOWERS_IDS, followersList);
                        loginAuth.signUpForm(userData, Consts.DB_DJS);
                    }
                    else {
                        loginAuth.signUpForm(userData, Consts.DB_USERS);
                        followingList = new ArrayList<>();
                        userData.put(Consts.COLUMN_FOLLOWERS_IDS, followingList);
                    }



                }
            }
        });

       return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK && data!= null) {
            Uri ImageUri = data.getData();

        }

    }


    @Override
    public void onClick(View v) {
        checkedGenres.add(((CheckBox)v).getText().toString());
    }

    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {

    }

    @Override
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {

    }
}
