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
import com.gilortal.djcalendar.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFormFragment extends Fragment {

    TextInputEditText nameText;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextInputEditText confirmPasswordText;
    CheckBox isDJCheckBox;
    EditText aboutBox;
    Button confirmBox;


    String name = null, email = null, password = null, confirmPassword = null;
    boolean isDJ = false;






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
        confirmPasswordText = v.findViewById(R.id.password_confirm_sign_up_form_ID);
        isDJCheckBox = v.findViewById(R.id.dj_or_not_button_ID);
        aboutBox = v.findViewById(R.id.about_text_id);
        confirmBox = v.findViewById(R.id.confirmBox);


        isDJCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDJ = !isDJ;
            }
        });



        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameText.getText().toString();
                password = passwordText.getText().toString();
                confirmPassword = confirmPasswordText.getText().toString();
                email = emailText.getText().toString();

                if (name == null || email == null || password == null || confirmPassword == null) {

                    Toast.makeText(getActivity().getBaseContext(), "Please Insert All Mandatory fields", Toast.LENGTH_LONG).show();
                }

                else if (password != confirmPassword)

                    Toast.makeText(getActivity().getParent(), "Please confirm again the password", Toast.LENGTH_LONG).show();
                else {

                    HashMap<String, Object> userData = new HashMap();

                    userData.put(Consts.COLUMN_NAME, name);
                    userData.put(Consts.COLUMN_EMAIL, email);
                    userData.put(Consts.COLUMN_PASSWORD, password);

                    if (isDJ)
                        userData.put(Consts.COLUMN_ABOUT, aboutBox.getText().toString());


                }
            }
        });







        return v;
    }
}
