package com.example.kamhometools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.myViewHolder> {
    Context context;
    ArrayList<PostProducts> list;

    public productAdapter(Context context, List<PostProducts> list) {
        this.context = context;
        this.list = (ArrayList<PostProducts>) list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.design, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PostProducts model= list.get(position);
        holder.productName.setText(model.getProductName());
//        holder.productDescription.setText(model.getProductDescription());
        holder.priceCatalog.setText("UGX "+model.getPriceCatalog());
        String imageUri;
        imageUri=model.getImageUri();
        Picasso.get().load(imageUri).into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productDescription, priceCatalog;
        ImageView image1;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productname);
//            productDescription=itemView.findViewById(R.id.product_description);
            priceCatalog=itemView.findViewById(R.id.priceCatalog);
            image1=itemView.findViewById(R.id.image1);

        }
    }
}
