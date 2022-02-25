package com.example.madprojectdelta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cus_updateProfile extends AppCompatActivity {
    String userID;
    EditText name,phone;
    TextView email;
    Button updateBtn;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_update_profile);

        name = findViewById(R.id.uname);
        phone = findViewById(R.id.userPhoneInput);
        email = findViewById(R.id.userEmail);
        updateBtn = findViewById(R.id.updateuserBtn);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();

        getUserDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });
    }
    public void getUserDetails()
    {
        DatabaseReference refDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        refDB.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren())
                {
                    name.setText(snapshot.child("username").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void updateDetails()
    {

            //validate userdetails
            if(valideateDetails())
            {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        dbRef.child("username").setValue(name.getText().toString().trim());
                        dbRef.child("phone").setValue(phone.getText().toString());

                        //
                        Toast.makeText(getApplicationContext(), "Succefully updated detils", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Cus_updateProfile.this, Cus_viewProfile.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

    }
    public boolean valideateDetails()
    {
        String usetPhone = phone.getText().toString();

        if(usetPhone.length() == 10)
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}