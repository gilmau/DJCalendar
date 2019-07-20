package com.gilortal.djcalendar;

import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gilortal.djcalendar.Adapters.CustomSharePrefAdapter;
import com.gilortal.djcalendar.Classes.Events;
import com.gilortal.djcalendar.Fragments.DjProfileFragment;
import com.gilortal.djcalendar.Fragments.EventFragment;
import com.gilortal.djcalendar.Fragments.LoginFragment;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  UpdateToServer, MoveToFrag, LoginAuth, RequestDataFromServer {
    FirebaseFirestore db ;
    public SendServerResponeToFrags serverToFragsListener;
    CustomSharePrefAdapter sharedPref;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    String email;
    String password;
    Bundle savedInstanceState;

    @Override
    public void onAttachFragment (Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof DjProfileFragment) {
            ((DjProfileFragment)fragment).fragChanger = this;
            ((DjProfileFragment)fragment).dbUpdater = this;
            ((DjProfileFragment)fragment).requestServer = this;

        }else if (fragment instanceof UserProfileFragment){
            ((UserProfileFragment)fragment).fragChanger = this;
            ((UserProfileFragment)fragment).dbUpdater = this;
            ((UserProfileFragment)fragment).requestServer = this;

        }else if (fragment instanceof EventFragment){
            ((EventFragment)fragment).fragChanger = this;
            ((EventFragment)fragment).dbUpdater = this;
            ((EventFragment)fragment).requestServer = this;

        }else if (fragment instanceof LoginFragment){
            ((LoginFragment)fragment).loginAuth = this;
            //     ((LoginFragment)fragment).loginAuth = this;

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                View headerView = navigationView.getHeaderView(0); //title of drawer
//                TextView userNameDrawerTV = headerView.findViewById(R.id.);
//                TextView userTypeDrawerTV = headerView.findViewById(R.id.);
                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null){ //user is logged in
                    sharedPref.setSignedInStatus(true);
                    //     userNameDrawerTV.setText(currentUser.getDisplayName());
                    sharedPref.setMyUserId(currentUser.getUid());
//                    userTypeDrawerTV.setText("");
                    db.collection(Consts.DB_DJS).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    sharedPref.setIsDj(true);
                                    gotToFrag(Consts.DJ_PROFILE_FRAG, currentUser.getUid(), Consts.DB_DJS );
                                } else {
                                    sharedPref.setIsDj(false);
                                    gotToFrag(Consts.DJ_PROFILE_FRAG, currentUser.getUid(), Consts.DB_USERS);
                                }
                            }
                        }
                    });


//                    navigationView.getMenu().findItem(R.id.sign_in).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.sign_up).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.reset_password).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.sign_out).setVisible(true);

                }
                else { //signed out
                    sharedPref.setSignedInStatus(false);
                    sharedPref.setMyUserId("");
//                    userNameDrawerTV.setText(getResources().getString(R.string.login_please));
//                    userTypeDrawerTV.setText(getResources().getString(R.string.wait_for_you));
//                    navigationView.getMenu().findItem(R.id.sign_in).setVisible(true);
//                    navigationView.getMenu().findItem(R.id.sign_up).setVisible(true);
//                    navigationView.getMenu().findItem(R.id.reset_password).setVisible(true);
//                    navigationView.getMenu().findItem(R.id.sign_out).setVisible(false);
                }

            }
        };




        changeFragmentDisplay(Consts.LOGIN_SCREEN_FRAG);

    }

    private void changeFragmentDisplay(int displayFragment) {
        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState!=null) {
                return;
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch(displayFragment){
                case Consts.DJ_PROFILE_FRAG:
                    fragmentTransaction.add(R.id.fragment_container, new DjProfileFragment(), null); break;
                case Consts.EVENT_FRAG:
                    fragmentTransaction.add(R.id.fragment_container, new EventFragment(), null); break;
                case Consts.SIGNUP_FORM_FRAG:
//                    fragmentTransaction.add(R.id.fragment_container, new LoginFragment(), null); break;
                case Consts.LOGIN_SCREEN_FRAG:
                    fragmentTransaction.add(R.id.fragment_container, new LoginFragment(), null); break;
                case Consts.USER_PROFILE_FRAG:
                    fragmentTransaction.add(R.id.fragment_container, new UserProfileFragment(), null); break;

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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void sendToServer(HashMap updates, String docId, String collectionName) {
        db.collection(collectionName).document(docId).update(updates);
    }

    @Override
    public void gotToFrag(int moveToFragment, String docId, String collectionName) {
        switch (moveToFragment){
            case Consts.DJ_PROFILE_FRAG:
                getSnapshotFromServer(docId,collectionName);


        }
    }

    private void getSnapshotFromServer(String docId, String collectionName){
        db.collection(collectionName).document(docId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()&&task.getResult().exists()){
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
    public void signUpForm() {

    }

    @Override
    public void queryFromServer(int requestCode, int fromFragment, HashMap args) {
        switch(requestCode){
            case Consts.REQ_FOLLOWERS_INFO:
                //get name, id, picture  | args : followers []
                break;
            case Consts.REQ_LINEUP_DJ_INFO:
                //get name , id , picture | args : lineup ids []
                break;
            case Consts.REQ_EVENTS_LIST_QUERY:
                //get Events[] | args :  events id
                if (fromFragment == Consts.DJ_PROFILE_FRAG) { //requsting events that dj is in their lineup:
                    final ArrayList<Events> eventsList = new ArrayList<>();
                    db.collection(Consts.DB_EVENTS)
                            .whereEqualTo(Consts.COLUMN_EVENTS_ID, args.get(Consts.ARG_DJ_ID))//all events where dj id in lineupIDs
                            .orderBy(Consts.COLUMN_DATE) //order by date milliseconds
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Log.d("EVENT QUERY", doc.getId() + " == >" + doc.getData());
                                    eventsList.add(new Events(doc));
                                }
                                serverToFragsListener.broadcastQueryResult(eventsList, Consts.REQ_EVENTS_LIST_QUERY);

                            }
                        }
                    });
                }



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


