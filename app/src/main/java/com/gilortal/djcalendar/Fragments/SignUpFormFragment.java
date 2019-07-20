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

import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFormFragment extends Fragment {

    TextInputEditText nameText;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextInputEditText ConfirmPasswordText;
    CheckBox isDJCheckBox;
    EditText aboutBox;
    Button confirmBox;


    String name, email, password, confirmPassword, about;
    boolean isDJ;






    public SignUpFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_signupform, container, false);


        nameText = v.findViewById(R.id.name_sign_up_form_ID);
        emailText = v.findViewById(R.id.email_sign_up_form_ID);
        passwordText = v.findViewById(R.id.password_sign_up_form_ID);
        ConfirmPasswordText = v.findViewById(R.id.password_confirm_sign_up_form_ID);
        isDJCheckBox = v.findViewById(R.id.dj_or_not_button_ID);
        aboutBox = v.findViewById(R.id.about_text_id);
        confirmBox = v.findViewById(R.id.confirmBox);


        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object> userData = new HashMap();

                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_EM,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());
                userData.put(Consts.COLUMN_NAME,  name = nameText.getText().toString());




                name = nameText.getText().toString();
                email = emailText.getText().toString();
                password = passwordText.getText().toString();


            }
        });







        return v;
    }
}
