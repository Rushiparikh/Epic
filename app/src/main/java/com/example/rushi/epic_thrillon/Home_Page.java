package com.example.rushi.epic_thrillon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rushi.epic_thrillon.HomeFragment;
import com.facebook.AccessToken;
import com.facebook.FacebookBroadcastReceiver;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Home_Page extends AppCompatActivity
         {
    private ImageView nav_image_view;
    private TextView nav_textview_name,nav_textview_email;
    InputStream is;
    AccessToken accessToken;
    private String email=null;
    String Login_with;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_NEARBY = "Near By";
    private static final String TAG_DESTINATION = "Dsstination";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_WISHLIST = "Wishlist";
    private static final String TAG_MYACITIVITY = "My Activity";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
         navigationView=(NavigationView)findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
         Login_with =  getIntent().getStringExtra("Login");
        nav_image_view = (ImageView) header.findViewById(R.id.navheader_imageView);
        nav_textview_name = (TextView) header.findViewById(R.id.navheader_name);
        nav_textview_email = (TextView) header.findViewById(R.id.navheader_email);

         acct = GoogleSignIn.getLastSignedInAccount(Home_Page.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient =new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Home_Page.this,"Went wrong",Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        if(Login_with.equalsIgnoreCase("Facebook")){
            Profile profile = Profile.getCurrentProfile();
            String imageUrl = profile.getProfilePictureUri(200, 200).toString();
            String firstname = profile.getFirstName();
            String lastname = profile.getLastName();

            String name = firstname + " " + lastname;

            Log.e("TAG_name", name);
            nav_textview_name.setText(name);
            new Home_Page.DownloadImage(nav_image_view).execute(imageUrl);
            GraphRequest req = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Toast.makeText(getApplicationContext(), "graph request completed", Toast.LENGTH_SHORT).show();

                    try {
                        email = object.getString("email");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            });

         //  nav_textview_email.setText(email);
        }else{
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                String personPhoto = acct.getPhotoUrl().toString();
                nav_textview_name.setText(personName);
                new Home_Page.DownloadImage(nav_image_view).execute(personPhoto);
                nav_textview_email.setText(personEmail);


            }
        }

    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                NearByFragment nearByFragment = new NearByFragment();
                return nearByFragment;
            case 2:
                // movies fragment
                DestinationFragment destinationFragment = new DestinationFragment();
                return destinationFragment;
            case 3:
                // notifications fragment
                WishListFragment wishListFragment = new WishListFragment();
                return wishListFragment;

            case 4:
                // settings fragment
                MyActivityFragment myActivityFragment = new MyActivityFragment();
                return myActivityFragment;

            case 5:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app


        // If mPendingRunnable is not null, then add to the message queue
        Fragment fragment = getHomeFragment();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        // show or hide the fab button


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

        public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__page, menu);
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

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu

            @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();

            // Handle the camera action
        } else if (id == R.id.nav_near_by) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_NEARBY;
            loadHomeFragment();

        } else if (id == R.id.nav_my_activity) {
            navItemIndex = 4;
            CURRENT_TAG = TAG_MYACITIVITY;
            loadHomeFragment();
        } else if (id == R.id.nav_destination) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_DESTINATION;
            loadHomeFragment();
        } else if (id == R.id.nav_notification) {
            navItemIndex = 5;
            CURRENT_TAG = TAG_NOTIFICATIONS;
            loadHomeFragment();
        }else if(id == R.id.nav_wishlist){
            navItemIndex = 3;
            CURRENT_TAG = TAG_WISHLIST;
            loadHomeFragment();
        }
        else if (id == R.id.nav_logout) {
            if (Login_with.equalsIgnoreCase("Facebook")) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(Home_Page.this, AskForSignin.class));
                finish();
            } else {
                signOut();
            }
        }else{
            navItemIndex = 0;

        }

       /* case R.id.nav_about_us:
        // launch new intent instead of loading fragment
        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        drawer.closeDrawers();
        return true;
        case R.id.nav_privacy_policy:
        // launch new intent instead of loading fragment
        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
        drawer.closeDrawers();
        return true;

        */
        loadHomeFragment();




        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
            }
        });
    }
    private void signOut() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {

                @Override
                public void onResult(Status status) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    startActivity(new Intent(Home_Page.this,AskForSignin.class));
                }
            });

        }
    }
}

