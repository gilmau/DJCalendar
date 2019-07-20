package com.gilortal.djcalendar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.gilortal.djcalendar.R;

import org.w3c.dom.Text;



public class LoginFragment extends Fragment {

    String email = null;
    String password = null;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    public LoginAuth loginAuth;


    // insteface to deal with login

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);


        emailText = view.findViewById(R.id.email_layout);
        email = emailText.getText().toString();
        passwordText = view.findViewById(R.id.password_layout);
        password = passwordText.getText().toString();

        loginAuth.singInUser(email, password);




        return view;
    }
}
