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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener

{
    private ImageView nav_image_view;
    private TextView nav_textview_name,nav_textview_email;
    InputStream is;
    AccessToken accessToken;
    private String email=null;
    String Login_with;
    private NavigationView navigationView;

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

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      //  activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        //set Default fragment on loading
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,new HomeFragment()).commit();
        Login_with =  getIntent().getStringExtra("Login");

       View header = navigationView.getHeaderView(0);

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





        if(Login_with.equalsIgnoreCase("Facebook")){
            Profile profile = Profile.getCurrentProfile();
            String imageUrl = profile.getProfilePictureUri(200, 200).toString();
            String firstname = profile.getFirstName();
            String lastname = profile.getLastName();

            String name = firstname + " " + lastname;

            Log.e("TAG_name", name);
            nav_textview_name.setText(name);
           // new Home_Page.DownloadImage(nav_image_view).execute(imageUrl);
            Glide.with(getApplicationContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(nav_image_view);
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
              //  new Home_Page.DownloadImage(nav_image_view).execute(personPhoto);
                Glide.with(getApplicationContext()).load(personPhoto).apply(RequestOptions.circleCropTransform()).into(nav_image_view);
                nav_textview_email.setText(personEmail);


            }
        }

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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("Home");
            HomeFragment fragment=new HomeFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment, "Home");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            // Handle the camera action
        } else if (id == R.id.nav_near_by) {
            setTitle("Near By");
            NearByFragment nearByFragment=new NearByFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,nearByFragment, "Near By");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_my_activity) {
            setTitle("My Activity");
            MyActivityFragment myActivityFragment=new MyActivityFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,myActivityFragment, "My Activity");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_destination) {
            setTitle("Destination");
            DestinationFragment destinationFragment=new DestinationFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,destinationFragment, "Near By");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_notification) {
            setTitle("Notification");
            NotificationFragment notificationFragment=new NotificationFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,notificationFragment, "Notification");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else if(id == R.id.nav_wishlist){
            setTitle("WishList");
            WishListFragment wishListFragment=new WishListFragment();
            fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,wishListFragment, "WishList");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

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






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
            }






    private void signOut() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //mGoogleApiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {

              //  @Override
              //  public void onResult(Status status) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    startActivity(new Intent(Home_Page.this,AskForSignin.class));
                    finish();
            //    }
            //});

        }
    }
}

