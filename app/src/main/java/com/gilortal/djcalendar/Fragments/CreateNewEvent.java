package com.gilortal.djcalendar.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gilortal.djcalendar.Adapters.DJAdapter;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class CreateNewEvent  extends Fragment implements View.OnClickListener, SendServerResponeToFrags {

    public MoveToFrag moveToFrag;
    TextView nameNewEvent, locationNewEvent,dateNewEvent,aboutNewEvent;
    ImageView imageNewEvent;
    ListView linupEvent ;
    Button confirmNewEvent;
    DatePickerDialog dialog;
    String nameEvent ="",locationEvent = "",dateEvent = "",aboutEvent = "";
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public LoginAuth loginAuth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    final static int GALLERY_PICK = 1;
    private Uri imageURI;
    String imageFBStrogeUri;
    String dbDate;

    PlacesClient placesClient;








    public CreateNewEvent() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).serverToFragsListener = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).serverToFragsListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_event_form, container, false);

        container.clearDisappearingChildren();

        mStorageRef = FirebaseStorage.getInstance().getReference("Event Pictures");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Events Profile DB");
        nameNewEvent = v.findViewById(R.id.event_new_form_name);
        //locationNewEvent = v.findViewById(R.id.event_new_form_location);
        dateNewEvent = v.findViewById(R.id.event_new_form_date);
        aboutNewEvent = v.findViewById(R.id.about_new_event);
        imageNewEvent = v.findViewById(R.id.event_new_form_image);
        linupEvent = v.findViewById(R.id.event_new_form_lineup_list);
        confirmNewEvent = v.findViewById(R.id.event_new_form_confirmbtn);



        imageNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        dateNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();

            }
        });
        String apiKey  = "AIzaSyDMjJa22vJvyyBMFgi4hD9gHan6me7XMzU";




        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
        getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                //CreateNewEvent.super.onActivityResult(requestCode, resultCode, data);
               // final LatLng latLng  = place.getLatLng();
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                locationEvent = place.getName();
               // Log.i("PlacesApi","onPlaceSelected: "+latLng.latitude+"\n"+latLng.longitude);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);


            }
        });



        confirmNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nameEvent = String.valueOf(nameNewEvent.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    locationEvent = String.valueOf(locationNewEvent.getText());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    dateEvent = String.valueOf(dateNewEvent.getText());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                try {
                    aboutEvent = String.valueOf(aboutNewEvent.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (nameEvent == "" || aboutEvent == "" || dateNewEvent.equals("")) {
                    Toast.makeText(getActivity().getBaseContext(), "Please Insert All Mandatory fields", Toast.LENGTH_LONG).show();
                } else {
                    dbDate = dateNewEvent.getText().toString();


                    HashMap<String, Object> eventData = new HashMap();

                    eventData.put(Consts.COLUMN_NAME_EVENT, nameEvent);
                    eventData.put(Consts.COLUMN_DATE,dbDate );
                    eventData.put(Consts.COLUMN_LOCATION, locationEvent);
                    eventData.put(Consts.COLUMN_ABOUT, aboutEvent);
                    eventData.put(Consts.COLUMN_PIC_URL, imageFBStrogeUri);
                    eventData.put(Consts.COLUMN_LINEUP_IDS, "");
                    loginAuth.NewEventForm(eventData, Consts.DB_EVENTS);
                }


            }

        });
        return v;
    }






    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateNewEvent.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };





    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_PICK);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode  == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            StorageReference reference = mStorageRef;

            Picasso.with(getActivity().getApplicationContext()).load(imageURI).into(imageNewEvent);
            uploadFile();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getBaseContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile () {
        if (imageURI != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageURI));

            fileReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity().getBaseContext(), "Image Upload Success", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageFBStrogeUri = uri.toString();
                                }
                            });
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(getActivity().getBaseContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {

    }

    @Override
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {

    }
}
