package com.gilortal.djcalendar.Interfaces;

import com.google.firebase.firestore.DocumentSnapshot;

public interface SendServerResponeToFrags {
    void BroadcastSnapShot(DocumentSnapshot document);
}
