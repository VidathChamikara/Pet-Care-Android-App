package com.example.madprojectdelta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;



public class Forget_password extends AppCompatActivity {

    EditText email;
    Button back,send_email;
    ProgressDialog loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //initializing values
        email = findViewById(R.id.input_email);
        back = findViewById(R.id.backlogin_btn);
        send_email = findViewById(R.id.sendemail_btn);

        loadBar = new ProgressDialog(Forget_password.this);

    }


    @Override
    protected void onResume() {
        super.onResume();

        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean chk = checkemail();

                if(chk) {

                    loadBar.setTitle("Forget Password");
                    loadBar.setMessage("Please Wait while send the verification email to the user");
                    loadBar.setCanceledOnTouchOutside(false);
                    loadBar.show();

                    String userEmail = email.getText().toString();

                    FirebaseAuth.getInstance()
                            .sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Successfully Send an verification email",Toast.LENGTH_SHORT).show();
                                        //Navigate to login
                                        navigateToLoginactivity();
                                        loadBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        loadBar.dismiss();
                                    }

                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter valid email address", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //back to login page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginactivity();
            }
        });

    }

    public boolean checkemail() {
        String emailInput = email.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if( emailInput.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
            return false;

        }
        else if(Pattern.compile(EmalFormat).matcher(emailInput).matches()) {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void navigateToLoginactivity()
    {
        Intent intent = new Intent(Forget_password.this, login.class);
        startActivity(intent);
    }
}