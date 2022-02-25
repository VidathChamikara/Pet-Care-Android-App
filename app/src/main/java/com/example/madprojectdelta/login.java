package com.example.madprojectdelta;




import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.Admin;
import com.example.madprojectdelta.models.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    Button loginBtn;
    TextView forgetpwd,register;
    EditText email, pwd;
    ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        //session management for admin
        SessionManagement sessionManagement = new SessionManagement(login.this);
        String isLoginUname = sessionManagement.getSession();

        //if user already login
        if(mAuth.getCurrentUser() != null)
        {
            //navigate to home
            navigateToActivityHome();
        }

        else if(!isLoginUname.equals("E")){
            //user logged in navigate to Admin home
            navigateToActivityAdmin();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //initializing id's
        loginBtn = findViewById(R.id.login_btnlogin);
        forgetpwd = findViewById(R.id.login_registertext);
        email = findViewById(R.id.login_Username);
        pwd = findViewById(R.id.login_Password);
        register = findViewById(R.id.login_btn_register);

        loadingBar = new ProgressDialog(login.this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //display prgresss bar to user
                loadingBar.setTitle("Login User");
                loadingBar.setMessage("Please Wait while Validate the Details");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                //get input details
                String emailInput = email.getText().toString();
                String pwdInput = pwd.getText().toString();

                //check valid email
                if(checkemail())
                {
                    mAuth.signInWithEmailAndPassword(emailInput,pwdInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                navigateToActivityHome();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
                else
                {
                    if(emailInput.equals("admin") && pwdInput.equals("admin"))
                    {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        Admin admin = new Admin("Admin","admin");

                        SessionManagement sessionManagement = new SessionManagement(login.this);
                        sessionManagement.saveSession(admin);
                        Toast.makeText(getApplicationContext(), "Admin Login success",Toast.LENGTH_SHORT).show();

                        loadingBar.dismiss();
                        navigateToActivityAdmin();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Username or Password is Incorrect",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            }
        });
        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToforgetpassword();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivityRegister();
            }
        });

    }
    public boolean checkemail()
    {
        String emailInput = email.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(Pattern.compile(EmalFormat).matcher(emailInput).matches())
        {
            return true;
        }
        else {
            return false;
        }

    }

    public void navigateToActivityHome()
    {
        Intent intent = new Intent(login.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void navigateToActivityAdmin()
    {
        Intent intent = new Intent(login.this, admin_menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void navigateToActivityRegister()
    {
        Intent intent = new Intent(login.this, Registratiion.class);
        startActivity(intent);
    }

    public void navigateToforgetpassword()
    {
        Intent intent = new Intent(login.this, Forget_password.class);
        startActivity(intent);
    }

}