package com.gilortal.djcalendar.Interfaces;

import java.util.HashMap;

public interface UpdateToServer {
    void sendToServer(HashMap updates, String docId, String collectionName);
}
