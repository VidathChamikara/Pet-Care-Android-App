package com.example.madprojectdelta.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.R;
import com.example.madprojectdelta.interfaces.ItemClicklistner;

public class AdminProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView pnameTxt, pdesTxt, priceTxt;
    public ImageView imageView;
    public ItemClicklistner listner;

    public AdminProductViewholder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.RV_row_img);
        pnameTxt = (TextView) itemView.findViewById(R.id.RV_row_prodName);
        pdesTxt = (TextView) itemView.findViewById(R.id.RV_row_prodDesc);
        priceTxt = (TextView) itemView.findViewById(R.id.RV_row_prodPrice);

    }

    public void setItemClickListner(ItemClicklistner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
