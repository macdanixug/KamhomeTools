package com.example.kamhometools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.myViewHolder> {
    private Context context;
    private ArrayList<PostProducts> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(PostProducts item);
    }

    public productAdapter(Context context, List<PostProducts> list) {
        this.context = context;
        this.list = (ArrayList<PostProducts>) list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.design, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PostProducts model = list.get(position);
        holder.productName.setText(model.getProductName());
        holder.priceCatalog.setText("UGX " + model.getPriceCatalog());
        String image1Uri;
        image1Uri = model.getImage1Url();
        Picasso.get().load(image1Uri).into(holder.image1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductDetailFragment(model.getProductName(),model.getPriceCatalog(),model.getProductDescription(),model.getImage1Url(), model.getImage2Url(),model.getImage3Url())).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, priceCatalog;
        ImageView image1,image2,image3;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productname);
            priceCatalog = itemView.findViewById(R.id.priceCatalog);
            productDescription = itemView.findViewById(R.id.product_description);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
            image3 = itemView.findViewById(R.id.image3);

        }
    }
}