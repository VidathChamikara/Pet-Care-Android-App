package com.example.madprojectdelta.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.R;
import com.example.madprojectdelta.interfaces.ItemClicklistner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClicklistner itemClicklistner;
    public TextView txtCartItmName, txtCartItmPrice, txtCartItmQty;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCartItmName = itemView.findViewById(R.id.shop_orders_productname);
        txtCartItmQty = itemView.findViewById(R.id.shop_orders_qty);
        txtCartItmPrice = itemView.findViewById(R.id.shop_orders_price);

    }

    @Override
    public void onClick(View view) {
        itemClicklistner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClicklistner(ItemClicklistner itemClicklistner) {
        this.itemClicklistner = itemClicklistner;
    }
}
