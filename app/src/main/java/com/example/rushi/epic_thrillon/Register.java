package com.example.rushi.epic_thrillon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText firstName,lastName;
    private EditText mobile;
    private EditText email;
    private EditText password,confirmPass;
    private Button register;
    private CheckBox checkbox;
    private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mref= FirebaseDatabase.getInstance().getReference("users");

        firstName=(EditText)findViewById(R.id.first_name_edittext);
        lastName=(EditText)findViewById(R.id.last_name_edittext);
        mobile=(EditText)findViewById(R.id.mobile_number_edittext);
        email=(EditText)findViewById(R.id.email_editText);
        password= (EditText)findViewById(R.id.password_edittext);
        confirmPass=(EditText)findViewById(R.id.confirm_password_edittext);
        checkbox = (CheckBox)findViewById(R.id.checkedTextView);
        register =(Button)findViewById(R.id.registeration_button);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName_register=firstName.getText().toString();
                String lastName_register=firstName.getText().toString();
                String mobile_register=mobile.getText().toString();
                String email_register=email.getText().toString();
                String password_register=password.getText().toString();
                String confirmPass_register=confirmPass.getText().toString();

                if(!TextUtils.isEmpty(firstName_register) && !TextUtils.isEmpty(mobile_register) && !TextUtils.isEmpty(email_register) && !TextUtils.isEmpty(lastName_register) && !TextUtils.isEmpty(password_register)&& !TextUtils.isEmpty(confirmPass_register) ){
                    if(password_register.equals(confirmPass_register)){
                        String id=mref.push().getKey();
                        User user=new User(firstName_register,lastName_register,mobile_register,email_register,password_register,confirmPass_register);
                        mref.child(id).setValue(id,user);
                        startActivity(new Intent(Register.this,Login.class));
                    }else{
                        Toast.makeText(Register.this,"Password and Confirm Password are not same", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(Register.this,"Pls enter all values", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}
