package com.example.madprojectdelta.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.R;
import com.example.madprojectdelta.interfaces.AdClickListner;

public class CornerAdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView type;
    public TextView price;
    public TextView description;
    public ImageView imageView;
    AdClickListner adClickListner;


    public CornerAdViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.adImage);
        type = (TextView) itemView.findViewById(R.id.ad_dogType);
        price = (TextView) itemView.findViewById(R.id.ad_dogPrice);
        description = (TextView) itemView.findViewById(R.id.ad_dogDescription);
    }

    public void setAdClickListner(AdClickListner Listner){
        this.adClickListner = adClickListner;
    }

    @Override
    public void onClick(View view) {
        adClickListner.onClick(view, getAdapterPosition(), false);
    }
}

