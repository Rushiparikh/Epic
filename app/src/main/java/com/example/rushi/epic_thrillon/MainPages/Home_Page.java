package com.example.rushi.epic_thrillon.MainPages;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.rushi.epic_thrillon.Auxiliaries.CircleTransform;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Auxiliaries.MyLocation;
import com.example.rushi.epic_thrillon.Auxiliaries.MyService;
import com.example.rushi.epic_thrillon.Classes.User;
import com.example.rushi.epic_thrillon.Fragments.Completed;
import com.example.rushi.epic_thrillon.Fragments.DestinationFragment;
import com.example.rushi.epic_thrillon.Fragments.HomeFragment;
import com.example.rushi.epic_thrillon.Fragments.MyActivityFragment;
import com.example.rushi.epic_thrillon.Fragments.NearByFragment;
import com.example.rushi.epic_thrillon.Fragments.NotificationFragment;
import com.example.rushi.epic_thrillon.Fragments.Upcoming;
import com.example.rushi.epic_thrillon.Fragments.WishListFragment;
import com.example.rushi.epic_thrillon.R;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Home_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Upcoming.OnFragmentInteractionListener,Completed.OnFragmentInteractionListener

{

    private ImageView nav_image_view;
    private TextView nav_textview_name,nav_textview_email;
    InputStream is;

    private String email=null;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String Login_with;
    String imageUrl,firstName,lastName,Email,name,id;
    private BroadcastReceiver broadcastReceiver;
    public static double longitude,latitude;

    @Override
    protected void onStop() {
        super.onStop();
        Intent i = new Intent(getApplicationContext(),MyService.class);
        stopService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i =new Intent(getApplicationContext(),MyService.class);
        startService(i);


        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    latitude =intent.getExtras().getDouble("latitude");
                    longitude = intent.getExtras().getDouble("longitude");

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    private NavigationView navigationView;
    private DatabaseReference mref;

    boolean facebook,email_login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private View navHeader;
    private Toolbar toolbar;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    public static int navItemIndex = 0;
    private boolean google;

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
    public static int i=9;
    private Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(checkPermission()){

            Intent intent =new Intent(getApplicationContext(),MyService.class);
            startService(intent);
        }else{
            requestPermission();
        }

        sharedPreferences=getSharedPreferences(AskForSignin.My_pref, Context.MODE_PRIVATE);

        mref= FirebaseDatabase.getInstance().getReference(Constants.USERS_DATABASE_PATH_UPLOADS);
      //  activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        editor=sharedPreferences.edit();

        facebook= sharedPreferences.getBoolean("Facebook",false);
        google=sharedPreferences.getBoolean("Google",false);
        email_login=sharedPreferences.getBoolean("Email",false);
        navigationView.setNavigationItemSelectedListener(this);


        //set Default fragment on loading

        Login_with =  getIntent().getStringExtra("Login");
        if( getIntent().getStringExtra("name")!= null){
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            NotificationFragment notificationFragment = new NotificationFragment();
            String s = getIntent().getStringExtra("name");
            String p = getIntent().getStringExtra("payload");
            Bundle b = new Bundle();
            b.putString("name",s);
            b.putString("payload",p);

            notificationFragment.setArguments(b);
            fragmentTransaction.replace(R.id.frame,notificationFragment).commit();
            toolbar.setTitle(TAG_NOTIFICATIONS);
        }else{
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,new HomeFragment()).commit();
            toolbar.setTitle(TAG_HOME);

        }
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
        if(i>9){
            id="00"+i;
        }else{
            id="000"+i;
        }


        if(facebook){
            Profile profile = Profile.getCurrentProfile();
            imageUrl = profile.getProfilePictureUri(200, 200).toString();
            firstName = profile.getFirstName();
            lastName = profile.getLastName();
            Email=profile.getId();
            name = firstName + " " + lastName;

            Log.e("TAG_name", name);
            nav_textview_name.setText(name);
            nav_textview_email.setText("");
           // new Home_Page.DownloadImage(nav_image_view).execute(imageUrl);

            Glide.with(getApplicationContext()).load(imageUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).transform(new CircleTransform(getApplicationContext())).into(nav_image_view);

            query=mref.orderByChild("email").equalTo(Email);
            query.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  if(!dataSnapshot.exists()){
                      String key=mref.push().getKey();
                      User user=new User(null,"",Email,firstName,lastName,-1,"",id,null);
                      mref.child(key).setValue(user);
                      i++;
                  }
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });
        }else if(google){

            if (acct != null) {
                final String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                final String personFamilyName = acct.getFamilyName();
                final String personEmail = acct.getEmail();
                String personId = acct.getId();
                String personPhoto = acct.getPhotoUrl().toString();
                nav_textview_name.setText(personName);
                //  new Home_Page.DownloadImage(nav_image_view).execute(personPhoto);

                Glide.with(getApplicationContext()).load(personPhoto).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).transform(new CircleTransform(getApplicationContext())).into(nav_image_view);

                nav_textview_email.setText(personEmail);
                query=mref.orderByChild("email").equalTo(personEmail);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            String key=mref.push().getKey();
                            User user=new User(null,"",personEmail,personName,personFamilyName,-1,"",id,null);
                            mref.child(key).setValue(user);
                        }
                        i++;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                }

        }else if(email_login) {





        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
            fragmentTransaction.replace(R.id.frame,destinationFragment, "Destination");
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
            if (facebook) {
                LoginManager.getInstance().logOut();
                editor.putBoolean("Facebook",false);
                editor.commit();
                startActivity(new Intent(Home_Page.this, AskForSignin.class));
                finish();
            }else if(email_login){
                startActivity(new Intent(Home_Page.this, AskForSignin.class));
                editor.putBoolean("Email",false);
                editor.commit();
                finish();
            }
            else {
                editor.putBoolean("Google",false);
                editor.commit();
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted) {

                    }
                    else {



                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

}

