package com.example.rushi.epic_thrillon.MainPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rushi.epic_thrillon.Auxiliaries.Constants;
import com.example.rushi.epic_thrillon.Classes.Activity;
import com.example.rushi.epic_thrillon.Classes.PublicReviews;
import com.example.rushi.epic_thrillon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReviewActivity extends AppCompatActivity {

    DatabaseReference mActivity;
    Button submitReview;
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        final String id=getIntent().getExtras().getString("id");
        submitReview=findViewById(R.id.submitReview);

        mActivity= FirebaseDatabase.getInstance().getReference(Constants.ACIVITY_DATABASE_PATH_UPLOADS);
        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  EditText comment=findViewById(R.id.editText);
                String CommnetTitle=comment.getText().toString();
                final EditText desc=findViewById(R.id.editText3);
                String commentDesc=desc.getText().toString();
                final PublicReviews publicReviews=new PublicReviews(CommnetTitle,commentDesc);


                Query query = mActivity.orderByChild("id").equalTo(id);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(flag) {
                                String id = mActivity.push().getKey();
                                mActivity.child(dataSnapshot1.getKey()).child("publicReviews").child(id).setValue(publicReviews);
                                Toast.makeText(ReviewActivity.this, "Added Review", Toast.LENGTH_SHORT).show();
                                comment.setText("");
                                desc.setText("");
                                flag = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
