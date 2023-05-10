package com.example.kamhometools;

import android.content.Context;
import android.content.Intent;
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

public class adminProductAdapter extends RecyclerView.Adapter<adminProductAdapter.myViewHolder> {
    private Context context;
    private ArrayList<PostProducts> list;
    private adminProductAdapter.OnItemClickListener mListener;
    String key = "";

    public interface OnItemClickListener {
        void onItemClick(PostProducts item);
    }

    public adminProductAdapter(Context context, List<PostProducts> list) {
        this.context = context;
        this.list = (ArrayList<PostProducts>) list;
    }

    public void setOnItemClickListener(adminProductAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public adminProductAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_design, parent, false);
        return new adminProductAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adminProductAdapter.myViewHolder holder, int position) {
        PostProducts model = list.get(position);
        holder.productName.setText(model.getProductName());
        holder.priceCatalog.setText("UGX " + model.getPriceCatalog());
        String imageUri;
        imageUri = model.getImageUri();
        Picasso.get().load(imageUri).into(holder.image1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminViewProducts.class);
                // Put the title, image URL, and message of the clicked item as extras in the intent
                intent.putExtra("productName", model.getProductName());
                intent.putExtra("priceCatalog", model.getPriceCatalog());
                intent.putExtra("productDescription", model.getProductDescription());
                intent.putExtra("imageUri", model.getImageUri());
                intent.putExtra("key", key= model.getKey());
                // Start the Details activity with the intent
                context.startActivity(intent);}
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, priceCatalog;
        ImageView image1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_title);
            productDescription = itemView.findViewById(R.id.product_description);
            priceCatalog = itemView.findViewById(R.id.product_price);
            image1 = itemView.findViewById(R.id.product_image);

        }
    }
}
