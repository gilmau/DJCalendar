package com.gilortal.djcalendar.Interfaces;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public interface SendServerResponeToFrags {
    void broadcastSnapShot(DocumentSnapshot document);
    void broadcastQueryResult(ArrayList queryResult, int requestCode);
}
