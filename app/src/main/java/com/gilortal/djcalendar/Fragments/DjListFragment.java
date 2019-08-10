package com.gilortal.djcalendar.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gilortal.djcalendar.Adapters.DJAdapter;
import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DjListFragment extends Fragment {

    private Handler mHandler = new Handler();
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;

    DJAdapter adapter;
    FirebaseFirestore db;
    List<String> DJnames = new ArrayList<>();
    List<String> imagesUri = new ArrayList<>();



    public DjListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dj_list, container, false);



        listView = view.findViewById(R.id.dj_list_view);
        db = FirebaseFirestore.getInstance();




        db.collection(Consts.DB_DJS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc : task.getResult()) {
                        Log.i("TAG", doc.getId().toString() + " " + doc.get(Consts.COLUMN_NAME) + " " +
                                doc.get(Consts.COLUMN_PIC_URL));
                        DJnames.add((String)doc.get(Consts.COLUMN_NAME));
                        imagesUri.add((String)doc.get(Consts.COLUMN_PIC_URL));
                    }
                }
            }
        });

        mHandler.postDelayed(mAdapterRunnable, 2000);

        return view;
    }

    private Runnable mAdapterRunnable = new Runnable() {
        @Override
        public void run() {
            adapter = new DJAdapter(getActivity().getBaseContext(), DJnames, imagesUri);
            listView.setAdapter(adapter);
        }
    };
}
