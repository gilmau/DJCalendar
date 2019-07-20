package com.gilortal.djcalendar.Interfaces;

import java.util.HashMap;

public interface AccessDjDB {
    void updateDjInServer(HashMap updates);
    String createNewDjInServer(HashMap updates);



}
