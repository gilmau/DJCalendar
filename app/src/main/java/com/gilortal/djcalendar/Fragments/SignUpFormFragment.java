package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFormFragment extends Fragment implements View.OnClickListener {

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

    String name = null, email = null, password = null, confirmPassword = null;
    boolean isDJ = false;


    public SignUpFormFragment() {
        // Required empty public constructor
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



        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{name = String.valueOf( nameText.getText());}catch (Exception e){e.printStackTrace();}
                try{password =String.valueOf( passwordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{confirmPassword = String.valueOf( confirmPasswordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{email = emailText.getText().toString();}catch (Exception e ){e.printStackTrace();}
             //  Toast.makeText(getActivity().getBaseContext(), password + " " + confirmPassword, Toast.LENGTH_LONG).show();

                if (name == null || email == null || password == null || confirmPassword == null) {

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
                        loginAuth.signUpForm(userData, Consts.DB_DJS);
                    }
                    else loginAuth.signUpForm(userData, Consts.DB_USERS);


                }
            }
        });







        return v;
    }

    @Override
    public void onClick(View v) {
        checkedGenres.add(((CheckBox)v).getText().toString());
    }
}
