package com.example.rushi.epic_thrillon.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rushi.epic_thrillon.Adapters.MyActivityAdapter;
import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.BookedActivity;
import com.example.rushi.epic_thrillon.MainPages.AskForSignin;
import com.example.rushi.epic_thrillon.R;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Completed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Completed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean facebook,google,email;
    Date currentDate;
    String formattedDate;

    GoogleSignInAccount acct;
    DatabaseReference mUser,mActivity;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    List<BookedActivity> bookedActivityList;
    List<Activity> activityList;
    MyActivityAdapter myActivityAdapter;
    private OnFragmentInteractionListener mListener;

    public Completed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Completed.
     */
    // TODO: Rename and change types and number of parameters
    public static Completed newInstance(String param1, String param2) {
        Completed fragment = new Completed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_completed, container, false);


        currentDate = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(currentDate);
        sharedPreferences=getActivity().getSharedPreferences(AskForSignin.My_pref,Context.MODE_PRIVATE);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        facebook = sharedPreferences.getBoolean("Facebook", false);
        google = sharedPreferences.getBoolean("Google", false);
        email = sharedPreferences.getBoolean("Email", false);
        sharedPreferences=getActivity().getSharedPreferences(AskForSignin.My_pref,Context.MODE_PRIVATE);
        mActivity= FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        mUser= FirebaseDatabase.getInstance().getReference(Constants.USERS_DATABASE_PATH_UPLOADS);

        activityList=new ArrayList<>();
        bookedActivityList=new ArrayList<>();
        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (facebook) {
            Profile profile = Profile.getCurrentProfile();
            final String facebookID = profile.getId();
            Query query = mUser.orderByChild("email").equalTo(facebookID);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot ds: dataSnapshot1.child("booked_activity").getChildren()){
                            BookedActivity bookedActivity = ds.getValue(BookedActivity.class);
                            bookedActivityList.add(bookedActivity);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mActivity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Activity activity = dataSnapshot1.getValue(Activity.class);
                        for (int i = 0; i < bookedActivityList.size(); i++) {

                            if ((activity.getId().equals( bookedActivityList.get(i).getId()))){

                                    try {
                                        if(currentDate.before(new SimpleDateFormat("dd/MM/yyyy").parse(activity.getActivityDate()))){
                                            activityList.add(activity);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                        }
                    }
                    myActivityAdapter = new MyActivityAdapter(getActivity(), activityList, sharedPreferences);
                    recyclerView.setAdapter(myActivityAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(google && acct!=null){

            final String googleMail = acct.getEmail();
            Query query = mUser.orderByChild("email").equalTo(googleMail);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bookedActivityList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot ds: dataSnapshot1.child("booked_activity").getChildren()){
                            BookedActivity bookedActivity = ds.getValue(BookedActivity.class);
                            bookedActivityList.add(bookedActivity);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mActivity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bookedActivityList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Activity activity = dataSnapshot1.getValue(Activity.class);
                        for (int i = 0; i < bookedActivityList.size(); i++) {

                            if ((activity.getId().equals( bookedActivityList.get(i).getId()))){
                                    String Date=activity.getActivityDate();
                                    try {
                                        Date activityDate=df.parse(Date);
                                        if(activityDate.compareTo(currentDate)>0){
                                            activityList.add(activity);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                        }
                    }
                    myActivityAdapter = new MyActivityAdapter(getActivity(), activityList, sharedPreferences);
                    recyclerView.setAdapter(myActivityAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
