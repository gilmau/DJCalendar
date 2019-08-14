package com.gilortal.djcalendar.Interfaces;

import java.util.HashMap;

public interface LoginAuth {

    void signInUser(String email, String password);
    void createNewUser(HashMap userData, String collection);
    void NewEventForm(HashMap eventData, String collection);



}
