package com.gilortal.djcalendar.Fragments;


import android.content.ContentResolver;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Consts;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.MainActivity;
import com.gilortal.djcalendar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFormFragment extends Fragment implements View.OnClickListener, SendServerResponeToFrags {

    public LoginAuth loginAuth;
    private CustomSharePrefAdapter spref;
    TextInputEditText nameText;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextInputEditText confirmPasswordText;
    CheckBox isDJCheckBox;
    EditText aboutBox;
    Button confirmBox;
    CheckBox electronicGenre, rockGenre, popGenre, reggaeGenre, hiphopGenre, israelGenre;
    List<String> checkedGenres = new ArrayList<>();
    List<String> followersList, followingList;
    ImageView profilePic;
    private Uri imageURI;
    String imageFBStrogeUri;
    private DatabaseReference mDatabaseRef, space;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    final static int GALLERY_PICK = 1;
    final static int RESULT_OK = 1;


    String name = "", email = "", password = "", confirmPassword = "";
    boolean isDJ = false;


    public SignUpFormFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_signupform, container, false);

        container.clearDisappearingChildren();

        mStorageRef = FirebaseStorage.getInstance().getReference("Profile Pictures");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("DJ Profiles DB");
        confirmBox = v.findViewById(R.id.confirmBox_linupeEvent);
        nameText = v.findViewById(R.id.name_sign_up_form_ID);
        emailText = v.findViewById(R.id.email_sign_up_form_ID);
        passwordText = v.findViewById(R.id.password_sign_up_form_ID);
        confirmPasswordText = v.findViewById(R.id.password_confirm_sign_up_form_ID);
        isDJCheckBox = v.findViewById(R.id.dj_or_not_button_ID);
        aboutBox = v.findViewById(R.id.about_text_id);
        profilePic = v.findViewById(R.id.profile_pic);
        //addPicButton = v.findViewById(R.id.add_pic_button);
        electronicGenre = v.findViewById(R.id.checkedbox_electronic_id);
        rockGenre = v.findViewById(R.id.checkedbox_rock_id);
        popGenre = v.findViewById(R.id.checkedbox_pop_id);
        reggaeGenre = v.findViewById(R.id.checkedbox_reggae_id);
        hiphopGenre = v.findViewById(R.id.checkedbox_hiphop_id);
        israelGenre = v.findViewById(R.id.checkedbox_israel_id);
        rockGenre.setOnClickListener(this);
        popGenre.setOnClickListener(this);
        reggaeGenre.setOnClickListener(this);
        hiphopGenre.setOnClickListener(this);
        israelGenre.setOnClickListener(this);
        electronicGenre.setOnClickListener(this);


        isDJCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDJ = ((CheckBox)v).isChecked();
                if (isDJ)
                    aboutBox.setVisibility(View.VISIBLE);
                else
                    aboutBox.setVisibility(View.GONE);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{name = String.valueOf( nameText.getText());}catch (Exception e){e.printStackTrace();}
                try{password =String.valueOf( passwordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{confirmPassword = String.valueOf( confirmPasswordText.getText());}catch (Exception e ){e.printStackTrace();}
                try{email = emailText.getText().toString();}catch (Exception e ){e.printStackTrace();}
             //  Toast.makeText(getActivity().getBaseContext(), password + " " + confirmPassword, Toast.LENGTH_LONG).show();

                if (name == "" || email == "" || password == "" || confirmPassword == "") {

                    Toast.makeText(getActivity().getBaseContext(), "Please Insert All Mandatory fields", Toast.LENGTH_LONG).show();
                }

                else if (!(password.equals(confirmPassword))) {

                    Toast.makeText(getActivity().getBaseContext(), "Please confirm again the password", Toast.LENGTH_LONG).show();
                }
                else {

                    HashMap<String, Object> userData = new HashMap();

                    userData.put(Consts.COLUMN_NAME, name);
                    userData.put(Consts.COLUMN_EMAIL, email);
                    userData.put(Consts.COLUMN_PASSWORD, password);
                    userData.put(Consts.COLUMN_GENRES,checkedGenres );
                    userData.put(Consts.COLUMN_PIC_URL, imageFBStrogeUri);


                    if (isDJ){
                        userData.put(Consts.COLUMN_ABOUT, aboutBox.getText().toString());
                        followersList = new ArrayList<>();
                        userData.put(Consts.COLUMN_FOLLOWERS_IDS, followersList);
                        loginAuth.createNewUser(userData, Consts.DB_DJS);
                    }
                    else {
                        followingList = new ArrayList<>();
                        userData.put(Consts.COLUMN_FOLLOWING_IDS, followingList);
                        loginAuth.createNewUser(userData, Consts.DB_USERS);
                    }
                }
            }
        });

       return v;
    }

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

            Picasso.with(getActivity().getApplicationContext()).load(imageURI).into(profilePic);
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
        checkedGenres.add(((CheckBox)v).getText().toString());
    }

    @Override
    public void broadcastSnapShot(DocumentSnapshot document) {

    }

    @Override
    public void broadcastQueryResult(ArrayList queryResult, int requestCode) {

    }
}
