package com.example.madprojectdelta;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.models.ProductItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductItem_Adapter extends RecyclerView.Adapter<ProductItem_Adapter.ViewProduct>{
    Context context;
    List<ProductItem> productItemList;

    public ProductItem_Adapter(Context context, List<ProductItem> productItemList) {
        this.context = context;
        this.productItemList = productItemList;
    }

    @NonNull
    @Override
    public ViewProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_rows_for_rv,parent,false);

        return new ViewProduct(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProduct holder, int position) {

        ProductItem productItem=productItemList.get(position);
        holder.product.setText("name:"+productItem.getDescription());
        holder.desc.setText("description: "+productItem.getProductName());

        String imageUri= null;
        imageUri=productItem.getImage();
        Picasso.get().load(imageUri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public class ViewProduct extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView product,desc;


        public ViewProduct(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.RV_row_img);
            product = itemView.findViewById(R.id.RV_row_prodName);
            desc = itemView.findViewById(R.id.RV_row_prodDesc);
        }
    }
}
