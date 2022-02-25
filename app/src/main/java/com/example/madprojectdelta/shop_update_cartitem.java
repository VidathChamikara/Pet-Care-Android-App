package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.ProductItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class shop_update_cartitem extends AppCompatActivity {

    EditText newQtyInput;
    Button updateQtyBtn;
    String itmID,itmName, itemDes, userID;
    ImageView itemPic,logo;
    TextView itemNameTxt, itemPriceTxt, itemDesTxt, itemQtytxt;
    int itmQty,oldQty,newInputQty;
    float itmPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_cartitem);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shop_update_cartitem.this, Home.class);
                startActivity(intent);
            }
        });

        //initializing id
        itemNameTxt = findViewById(R.id.viewItem_name);
        itemQtytxt = findViewById(R.id.viewItem_qty);
        itemPriceTxt= findViewById(R.id.viewItem_price);
        itemDesTxt = findViewById(R.id.viewItem_description);
        itemPic = findViewById(R.id.viewItem_iamge);
        newQtyInput = findViewById(R.id.updateCart_inputqty);
        updateQtyBtn = findViewById(R.id.button_updateCartQty);

        //get item id & quantity
        itmID = getIntent().getStringExtra("itmID");
        oldQty = Integer.parseInt(getIntent().getStringExtra("itmQty"));

        //assign item details
        newQtyInput.setText(String.valueOf(oldQty));
        getItemDetails(itmID);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


    }

    @Override
    protected void onResume() {
        super.onResume();




        //update detials
        updateQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get new quantity
                newInputQty = Integer.parseInt(newQtyInput.getText().toString());
                int oldQuantity = oldQty;
                int stock = itmQty;

                int currentStock = stock + oldQuantity - newInputQty;

                if(itmQty < newInputQty)
                {
                    Toast.makeText(getApplicationContext(), "Invalid item quantity", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //updateCart Details
                    updateCartDetails();

                    //update item stock details
                    updateProductItemStock(currentStock);

                    Toast.makeText(getApplicationContext(), "Successfully Updated Item", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(shop_update_cartitem.this, shop_show_products.class);
                    startActivity(intent);
                }

            }
        });


    }

    //get item details to show in view
    private void getItemDetails(String itemID) {

        DatabaseReference itmRed = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        itmRed.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    ProductItem item = snapshot.getValue(ProductItem.class);

                    itmName = item.getProductName();
                    itmPrice = item.getUnitPrice();
                    itmQty = item.getQty();
                    itemDes = item.getDescription();

                    itemNameTxt.setText(itmName);
                    itemPriceTxt.setText(String.valueOf(itmPrice));
                    itemDesTxt.setText(itemDes);
                    itemQtytxt.setText(String.valueOf(itmQty));
                    Picasso.get().load(item.getImage()).into(itemPic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });

    }

    //update cart
    private void updateCartDetails() {

        final DatabaseReference dbUpdate = FirebaseDatabase.getInstance().getReference().child("CartList").child("User")
                .child(userID).child("ProductItem");

        dbUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(itmID).exists())
                {
                    dbUpdate.child(itmID).child("quantity").setValue(newInputQty);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //update stock
    public void updateProductItemStock(final int currentStock)
    {
        final DatabaseReference dbUpdateQty = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        dbUpdateQty.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    dbUpdateQty.child(itmID).child("qty").setValue(currentStock);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}