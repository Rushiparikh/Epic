package com.example.rushi.epic_thrillon;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //view objects
    private Button buttonChoose;
    private Button buttonUpload;
    private EditText activityname,price,destination,rating,service,description,review,organiserName,mobileNo,longi,leti;
    private TimePicker time;
    private Uri filePath;
    String Image;
    String Timing;


    //uri to store file


    //firebase objects

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        activityname =findViewById(R.id.activityname);
        price =findViewById(R.id.price);
        destination =findViewById(R.id.destination);
        rating =findViewById(R.id.rating);
        service =findViewById(R.id.service);
        description =findViewById(R.id.description);
        review =findViewById(R.id.review);
        organiserName =findViewById(R.id.organiserName);
        mobileNo =findViewById(R.id.mobileNo);
        longi =findViewById(R.id.longi);
        leti =findViewById(R.id.leti);
        time= findViewById(R.id.time);




        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            String ImagePath = com.example.rushi.epic_thrillon.ImagePath.getPath(getApplicationContext(),filePath);
             Image =convertToBase64(ImagePath);

        }
    }

    private String convertToBase64(String imagePath)

    {

        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }

    private void uploadFile() {
        //checking if file is available
        if (Image != null) {
            //displaying progress dialog while image is uploading
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Timing = ""+time.getHour() + time.getMinute() + "";
            }

            //getting the storage reference


            //adding the file to reference


                            //creating the upload object to store uploaded image details
                            Upload upload = new Upload(activityname.getText().toString().trim(),Image,price.getText().toString().trim(),destination.getText().toString().trim(),rating.getText().toString().trim(),Timing,true,service.getText().toString().trim(),description.getText().toString().trim(),review.getText().toString().trim(),organiserName.getText().toString().trim(),mobileNo.getText().toString().trim(),longi.getText().toString(),leti.getText().toString().trim());

                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);


    }
    }
    @Override
    public void onClick(View view) {
        if (view == buttonChoose) {
            showFileChooser();
        } else if (view == buttonUpload) {
            uploadFile();
        }
    }
}


