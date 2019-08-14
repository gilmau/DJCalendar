package com.gilortal.djcalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.DJUser;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Classes.User;
import com.gilortal.djcalendar.Fragments.CreateNewEvent;
import com.gilortal.djcalendar.Fragments.DjListFragment;
import com.gilortal.djcalendar.Fragments.DjProfileFragment;
import com.gilortal.djcalendar.Fragments.EventFragment;
import com.gilortal.djcalendar.Fragments.LoginFragment;
import com.gilortal.djcalendar.Fragments.SignUpFormFragment;
import com.gilortal.djcalendar.Interfaces.LoginAuth;
import com.gilortal.djcalendar.Interfaces.RequestDataFromServer;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gilortal.djcalendar.Fragments.UserProfileFragment;
import com.gilortal.djcalendar.Interfaces.MoveToFrag;
import com.gilortal.djcalendar.Interfaces.SendServerResponeToFrags;
import com.gilortal.djcalendar.Interfaces.UpdateToServer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  UpdateToServer, MoveToFrag, LoginAuth, RequestDataFromServer {
    FirebaseFirestore db;
    public SendServerResponeToFrags serverToFragsListener;
    CustomSharePrefAdapter sharedPref;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    String email;
    String password;
    Bundle savedInstanceState;
    private AlertDialog alertDialog;
    TextView nameNewEvent, locationNewEvent, dateNewEvent, aboutNewEvent;
    ImageView imageNewEvent;
    ListView linupEvent;
    String nameEvent = null, locationEvent, dateEvent, aboutEvent = null;
    Button confirmNewEvent;
    TextView emailtext;

    public static CoordinatorLayout coordinatorLayout;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof DjProfileFragment) {
            ((DjProfileFragment) fragment).fragChanger = this;
            ((DjProfileFragment) fragment).dbUpdater = this;
            ((DjProfileFragment) fragment).requestServer = this;

        } else if (fragment instanceof UserProfileFragment) {
            ((UserProfileFragment) fragment).fragChanger = this;
            ((UserProfileFragment) fragment).dbUpdater = this;
            ((UserProfileFragment) fragment).requestServer = this;

        } else if (fragment instanceof EventFragment) {
            ((EventFragment) fragment).fragChanger = this;
            ((EventFragment) fragment).dbUpdater = this;
            ((EventFragment) fragment).requestServer = this;

        } else if (fragment instanceof LoginFragment) {
            ((LoginFragment) fragment).loginAuth = this;
            ((LoginFragment) fragment).moveToFrag = this;
        } else if (fragment instanceof SignUpFormFragment) {
            ((SignUpFormFragment) fragment).loginAuth = this;
        }
        else if (fragment instanceof CreateNewEvent) {
            ((CreateNewEvent) fragment).loginAuth = this;
            //((CreateNewEvent) fragment).moveToFrag = this;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        this.savedInstanceState = savedInstanceState;
        firebaseAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        sharedPref = new CustomSharePrefAdapter(this);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = getLayoutInflater().inflate(R.layout.reset_password,null);
        emailtext = v.findViewById(R.id.email_rest_password);



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               // Toast.makeText(MainActivity.this, "new user sign up - listening", Toast.LENGTH_SHORT).show();
                View headerView = navigationView.getHeaderView(0); //title of drawer
                final TextView loginTV = headerView.findViewById(R.id.login_tv);
                final TextView userLoginTV = headerView.findViewById(R.id.user_login_tv);
                final ImageView profileImage = headerView.findViewById(R.id.nav__header_image);

                Log.d("STATE LISTENER", "new user sign up - listening");
                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) { //user is logged in
                    sharedPref.setSignedInStatus(true);
                    Log.d("STATE LISTENER", "Signed in");
                    sharedPref.setMyUserId(currentUser.getUid());
                    db.collection(Consts.DB_DJS).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d("onAuthStateChanged", "success");
                                if (task.getResult().exists()) {
                                    sharedPref.setIsDj(true);
                                    Log.d("onAuthStateChanged", "user is a dj");
                                    DJUser djUser= new DJUser();
                                    navigationView.getMenu().findItem(R.id.nav_create_new_event).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_statistic).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_next_event).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_reset_password).setVisible(true);
                                    loginTV.setText("Welcome!!!");
                                    gotToFrag(Consts.DJ_PROFILE_FRAG, currentUser.getUid(), Consts.DB_DJS);

                                } else {
                                    Log.d("onAuthStateChanged", "user NOT a dj");
                                    sharedPref.setIsDj(false);
                                    navigationView.getMenu().findItem(R.id.nav_statistic).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_next_event).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_dj_list).setVisible(true);
                                    navigationView.getMenu().findItem(R.id.nav_reset_password).setVisible(true);
                                    loginTV.setText("Welcome!!!");
//                                    User user= new User();
//                                    Picasso.with(MainActivity.this).load(djUser.getPicture_url()).into(profileImage);
                                    gotToFrag(Consts.USER_PROFILE_FRAG, currentUser.getUid(), Consts.DB_USERS);
                                }
                            } else {
                                Log.d("onAuthStateChanged", "failed connecting to DB");
                            }
                        }
                    });


                } else { //signed out
                    sharedPref.setSignedInStatus(false);
                    sharedPref.setMyUserId("");

                }

            }
        };
        changeFragmentDisplay(Consts.LOGIN_SCREEN_FRAG);

    }

    private void changeFragmentDisplay(int displayFragment) {

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (displayFragment) {
                case Consts.LOGIN_SCREEN_FRAG:
                    LoginFragment loginFragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.fragment_container, loginFragment);
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.SIGNUP_FORM_FRAG:
                    SignUpFormFragment signUpFormFragment = new SignUpFormFragment();
                    fragmentTransaction.replace(R.id.fragment_container, signUpFormFragment);
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.DJ_PROFILE_FRAG:
                    fragmentTransaction.replace(R.id.fragment_container, new DjProfileFragment());
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.USER_PROFILE_FRAG:
                    fragmentTransaction.replace(R.id.fragment_container, new UserProfileFragment());
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.EVENT_FRAG:
                    fragmentTransaction.replace(R.id.fragment_container, new EventFragment());
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.DJ_LIST_FRAG:
                    fragmentTransaction.replace(R.id.fragment_container, new DjListFragment());
                    fragmentTransaction.addToBackStack(null);
                    break;
                case Consts.CREATE_NEW_EVENT:
                    fragmentTransaction.replace(R.id.fragment_container, new CreateNewEvent());
                    fragmentTransaction.addToBackStack(null);
                    break;
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogSignView = getLayoutInflater().inflate(R.layout.reset_password,null);
        int id = item.getItemId();


        if (id == R.id.nav_statistic) {
            statistic();

        } else if (id == R.id.nav_profile) {
            show_Profile();

        } else if (id == R.id.nav_dj_list) {
            changeFragmentDisplay(Consts.DJ_LIST_FRAG);

        } else if (id == R.id.nav_reset_password) {
            builder.setView(dialogSignView);
            alertDialog = builder.create();
            resetPassword();
            alertDialog.show();

        } else if (id == R.id.nav_next_event) {
            show_next_event();

        } else if (id == R.id.nav_create_new_event) {
            changeFragmentDisplay(Consts.CREATE_NEW_EVENT);

        } else if (id == R.id.nav_about) {


        } else if (id == R.id.nav_logout) {
            signOutUser();
            changeFragmentDisplay(Consts.LOGIN_SCREEN_FRAG);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void statistic() {
    }

    private void show_Profile() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (sharedPref.getIsDj()) {
            gotToFrag(Consts.DJ_PROFILE_FRAG, currentUser.getUid(), Consts.DB_DJS);

        } else {
            gotToFrag(Consts.USER_PROFILE_FRAG, currentUser.getUid(), Consts.DB_USERS);
        }
    }

    private void resetPassword() {

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String email  = currentUser.getEmail();
        emailtext.setText(email);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Check your email "+ email +" account to restore your user and sign in again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
        });
        alertDialog.dismiss();
    }




    private void show_next_event() {
    }

    private void signOutUser() {
        Toast.makeText(this, "Bye bye " , Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();
    }

    @Override
    public void sendToServer(HashMap updates, String docId, String collectionName) {
        db.collection(collectionName).document(docId).update(updates);
    }

    @Override
    public void gotToFrag(int moveToFragment, String docId, String collectionName) {
        Toast.makeText(this, "i'm in goToFrag", Toast.LENGTH_SHORT).show();
        changeFragmentDisplay(moveToFragment);
        if(docId != null)
            getSnapshotFromServer(docId, collectionName);
        }



    @Override
    public void goToSignUpFrag(int fragment) {
        changeFragmentDisplay(fragment);

    }

    private void getSnapshotFromServer(String docId, String collectionName){
            db.collection(collectionName).document(docId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                serverToFragsListener.broadcastSnapShot(task.getResult());

                            }
                        }
                    });

    }

    @Override
    public void signInUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){

                        }
                        else {
                            Toast.makeText(MainActivity.this, "E-Mail or Password Incorrect", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    public void createNewUser(final HashMap userData, final String collection) {
        email = userData.get(Consts.COLUMN_EMAIL).toString();
        password = userData.get(Consts.COLUMN_PASSWORD).toString();
        userData.remove(Consts.COLUMN_EMAIL);
        userData.remove(Consts.COLUMN_PASSWORD);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser curUser = firebaseAuth.getCurrentUser();
                            db.collection(collection).document(curUser.getUid()).set(userData);
                            Toast.makeText(MainActivity.this, "Signed Up Successfully ", Toast.LENGTH_SHORT).show();
                            Log.d("signup in activity" , "signed up successfuly");
                        }
                        else {
                            Toast.makeText(MainActivity.this, "signed up failed ", Toast.LENGTH_SHORT).show();
                            Log.d("signup in activity" , "signed up failed");
                        }
                    }
                });



    }


    @Override
    public void NewEventForm(final HashMap eventData, final String collection) {
        nameEvent = eventData.get(Consts.COLUMN_NAME_EVENT).toString();
        db.collection(collection).add(eventData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    FirebaseUser eventid = firebaseAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, " The Event save  Successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "The Event save failed ", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void queryFromServer(final int requestCode, int fromFragment, HashMap args) {
        switch(requestCode){
            case Consts.REQ_FOLLOWERS_INFO:
                //get name, id, picture  | args : followers []
                break;
            case Consts.REQ_LINEUP_DJ_INFO:
                //get name , id , picture | args : lineup ids []
                break;
            case Consts.REQ_EVENTS_LIST_QUERY:
                //get Events[] | args :  events id
                final ArrayList<Events> eventsList = new ArrayList<>();
                String fromColumn = "";
                String fromHash= "";
                if (fromFragment == Consts.DJ_PROFILE_FRAG) { //requsting events that dj is in their lineup:
                    fromHash = Consts.ARG_DJ_ID;
                    fromColumn = Consts.COLUMN_LINEUP_IDS;
                }else if (fromFragment == Consts.USER_PROFILE_FRAG){ //find events userId is in attending column
                    fromHash = Consts.ARG_USER_ID;
                    fromColumn = Consts.COLUMN_ATTENDING_IDS;
                }
                if (!(fromColumn.isEmpty() || fromHash.isEmpty()))
                    db.collection(Consts.DB_EVENTS)
                            .whereArrayContains(fromColumn, args.get(fromHash))
                            .orderBy(Consts.COLUMN_DATE) //order by date milliseconds
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Log.d("EVENT QUERY", doc.getId() + " == >" + doc.getData());
                                    eventsList.add(new Events(doc));
                                }
                                serverToFragsListener.broadcastQueryResult(eventsList, requestCode);
                            }
                        }
                    });



                break;
            case Consts.REQ_ATTENDERS_INFO:
                //get name, id, picture | attenders []
                break;
            case Consts.REQ_SUGGESTED_EVENTS:
                //get Events[] | args : genres[]
                break;
            case Consts.REQ_NEXT_EVENT:
                //get next event's --> name,id,location,date,genres[] |args : user \ dj id
                break;
        }
    }


}


