package com.example.madprojectdelta.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder {

    public TextView receiver, phone, address,postal, status,price, date,userCurrent;
    public Button viewAllBtn;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        receiver = itemView.findViewById(R.id.Orders_receiverName);
        phone = itemView.findViewById(R.id.Orders_phone);
        postal = itemView.findViewById(R.id.Orders_postal);
        price = itemView.findViewById(R.id.Orders_price);
        address = itemView.findViewById(R.id.Orders_address);
        date = itemView.findViewById(R.id.Orders_date);
        status = itemView.findViewById(R.id.Order_status);
        viewAllBtn = itemView.findViewById(R.id.adminOdershowAll);

    }

}

