package com.example.madprojectdelta.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.R;
import com.example.madprojectdelta.interfaces.AdminOrgListner;

public class org_view extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrgName, txtConNo, txtLocation;
    public AdminOrgListner listner;

    public org_view(@NonNull View itemView) {
        super(itemView);

        txtOrgName = (TextView) itemView.findViewById(R.id.org_name);
        txtConNo = (TextView) itemView.findViewById(R.id.admin_conNo);
        txtLocation = (TextView) itemView.findViewById(R.id.admin_location);
    }

    public void setItemClickListner(AdminOrgListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
