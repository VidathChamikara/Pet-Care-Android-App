package com.example.madprojectdelta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.Dog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Pattern;


public class corner_add_post extends AppCompatActivity {

    EditText type,price,description,contactNo, email;
    Button post,cancel;
    ImageButton imageSelect;
    Uri uri=null;
    DatabaseReference dbRef;
    ImageView logo;

    //Firebase
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    String userID;
    Dog dog;

    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_add_post);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_add_post.this, Home.class);
                startActivity(intent);
            }
        });

        type = (EditText)findViewById(R.id.editTextTextPersonName);
        price = (EditText)findViewById(R.id.editTextTextPersonName2);
        description = (EditText)findViewById(R.id.editTextTextPersonName3);
        contactNo = (EditText)findViewById(R.id.editTextPhone);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress);

        post = (Button)findViewById(R.id.addpost);
        cancel = (Button)findViewById(R.id.edit_post_cancel);

        imageSelect = (ImageButton) findViewById(R.id.imageButton);
        firebaseDatabase = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        dog = new Dog();

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);

            }
        });


        //bottom navigation bar begins


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            uri = data.getData();
            imageSelect.setImageURI(uri);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();


        cancel.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_add_post.this, corner_myAds.class);
               startActivity(intent);
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{
                    if(TextUtils.isEmpty(type.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please input dog breed/type",Toast.LENGTH_SHORT).show();
                        type.setError("Breed/type is required");}
                    else if (TextUtils.isEmpty(price.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please enter description",Toast.LENGTH_SHORT).show();
                        price.setError("Price is required");}
                    else if (TextUtils.isEmpty(description.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please enter description",Toast.LENGTH_SHORT).show();
                        description.setError("Description is required");}
                    else if (TextUtils.isEmpty(contactNo.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Please enter contactNo",Toast.LENGTH_SHORT).show();
                        contactNo.setError("contactNo is required");}
                    else if (TextUtils.isEmpty(email.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                    else{

                        if(checkemail() || checkphonenumber()) if(checkemail() || checkphonenumber())
                        {
                            final String dogType=type.getText().toString().trim();
                            final Double amount = Double.parseDouble(price.getText().toString().trim());
                            final String details = description.getText().toString().trim();
                            final Integer phone = Integer.parseInt(contactNo.getText().toString().trim());
                            final String mail = email.getText().toString().trim();

                            //get userID
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userID = user.getUid();

                            StorageReference filepath = storageReference.child("imagePost").child(uri.getLastPathSegment());
                            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String t = task.getResult().toString();
                                            //Inserting to database
                                            dog.setuID(userID);
                                            dog.setContactNo(phone);
                                            dog.setDescription(details);
                                            dog.setEmail(mail);
                                            dog.setImage(task.getResult().toString());
                                            dog.setType(dogType);
                                            dog.setPrice(amount);

                                            dbRef.push().setValue(dog).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful())
                                                    {
                                                        //feedback to the user
                                                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(corner_add_post.this, corner_myAds.class);
                                                        startActivity(intent);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Error: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

                                        }
                                    });
                                }
                            });
                        }

                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public boolean checkphonenumber()
    {
        String phone = contactNo.getText().toString();

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
        String emailval = email.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$";

        if(Pattern.compile(EmalFormat).matcher(emailval).matches())
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}