package com.example.kamhometools;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    Context context;
    ArrayList<PostProducts> list;

    public myAdapter(Context context, ArrayList<PostProducts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        // Model model= list.get(position);
       // holder.image1.setImageResource(list.get(position).getImageUri());
        holder.productName.setText(list.get(position).getProductName());
        holder.productDesc.setText(list.get(position).getProductDescription());
        holder.productCatalog.setText(list.get(position).getPriceCatalog());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView image1;
        TextView productName, productDesc, productCatalog;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image1= itemView.findViewById(R.id.image1);
            productName=itemView.findViewById(R.id.productname);
            productDesc=itemView.findViewById(R.id.product_description);
            productCatalog=itemView.findViewById(R.id.priceCatalog);
        }
    }
}
