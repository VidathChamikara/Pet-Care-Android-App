package com.example.madprojectdelta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registratiion extends AppCompatActivity {

    //creating variables
    Button btnReg;
    TextView navLogin;
    EditText unameInput, pwd1Input, pwd2input, phoneInput, emailInput;
    FirebaseAuth mAuth;
    ProgressDialog loadBar;

    @Override
    protected void onStart() {
        super.onStart();

        //if user already login
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(Registratiion.this, Home.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //initializing id's
        unameInput = findViewById(R.id.username);
        phoneInput = findViewById(R.id.phoneNumber);
        pwd1Input = findViewById(R.id.password1);
        pwd2input = findViewById(R.id.password2);
        emailInput = findViewById(R.id.email);

        btnReg = findViewById(R.id.btn_create_account);
        navLogin = findViewById(R.id.btn_navlogin);

        mAuth = FirebaseAuth.getInstance();
        loadBar = new ProgressDialog(Registratiion.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //create user account
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check fields are empty
                if( TextUtils.isEmpty(unameInput.getText().toString()) ){
                    displayError(unameInput,"Please enter username");
                }
                else if( TextUtils.isEmpty(phoneInput.getText().toString()) ){
                    displayError(phoneInput,"Please enter phone number");
                }
                else if( TextUtils.isEmpty(emailInput.getText().toString()) ){
                    displayError(emailInput,"Please enter email");
                }
                else if( TextUtils.isEmpty(pwd1Input.getText().toString()) ){
                    displayError(pwd1Input,"Please enter Password");
                }
                else if( TextUtils.isEmpty(pwd2input.getText().toString()) ){
                    displayError(pwd2input,"Please confirm Password");
                }
                else{

                    //check phone number
                    boolean chkPhone = checkphonenumber();

                    //check email
                    boolean chkEmail = checkemail();

                    //check passwords are matching
                   boolean chkPwd = checkPasswords();

                   if(chkPhone && chkEmail && chkPwd)
                   {
                       final String uname = unameInput.getText().toString();
                       final String pwd = pwd1Input.getText().toString();
                       final String email = emailInput.getText().toString();
                       final String phone = phoneInput.getText().toString();

                       //display message to user
                       loadBar.setTitle("Register New User");
                       loadBar.setMessage("Please Wait while Validate the Details");
                       loadBar.setCanceledOnTouchOutside(false);
                       loadBar.show();


                       mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful())
                               {
                                   User user = new User();
                                   user.setUsername(uname);
                                   user.setPhone(phone);
                                   user.setEmail(email);

                                   FirebaseDatabase.getInstance().getReference("Users")
                                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                           .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful())
                                           {
                                               loadBar.dismiss();
                                               Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

                                               //navigate to login
                                               navigateToLogin();
                                           }
                                           else {
                                               loadBar.dismiss();
                                               Toast.makeText(getApplicationContext(), "User Details Adding failed", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });

                               }
                               else
                               {
                                   Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   loadBar.dismiss();
                               }
                           }
                       });


                   }

                }

            }
        });

        //already have an accout navigate to login
        navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin();
            }
        });

    }

    public boolean checkPasswords()
    {
        String pwd1 = pwd1Input.getText().toString();
        String pwd2 = pwd2input.getText().toString();

        if(pwd1.equals(pwd2))
        {
            return true;
        }

        else if(pwd1.length() < 8){
            Toast.makeText(getApplicationContext(), "Passwords length must grater than or equal to 8", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            Toast.makeText(getApplicationContext(), "Passwords are Not matching", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean checkphonenumber()
    {
        String phone = phoneInput.getText().toString();

        if(phone.length() == 10)
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean checkemail()
    {
        String email = emailInput.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(Pattern.compile(EmalFormat).matcher(email).matches())
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void navigateToLogin()
    {
        Intent intent = new Intent(Registratiion.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void displayError(EditText input, String error)
    {
        input.setError(error);
        input.requestFocus();
    }

}