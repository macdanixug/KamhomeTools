package com.example.kamhometools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adminProductAdapter extends RecyclerView.Adapter<adminProductAdapter.MyViewHolder> {
    private List<PostProducts> items;
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference productsRef = database.getReference().child("PostProducts");

    public adminProductAdapter(Context context, List<PostProducts> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public adminProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Inflating the design of the pharmacy layout
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_design,null));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull adminProductAdapter.MyViewHolder holder, int position) {
        PostProducts model = items.get(position);
        holder.productName.setText(items.get(position).getProductName());
        holder.priceCatalog.setText("UGX " + items.get(position).getPriceCatalog());
        Picasso.get().load(items.get(position).getImage1Url()).into(holder.image1);

        String image1Uri = model.getImage1Url();
        String image2Uri = model.getImage2Url();
        String image3Uri = model.getImage3Url();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(context, AdminViewProducts.class);
                // Put the title, image URL, and message of the clicked item as extras in the intent
                intent.putExtra("productName", model.getProductName());
                intent.putExtra("priceCatalog", model.getPriceCatalog());
                intent.putExtra("productDescription", model.getProductDescription());
                intent.putExtra("image1Url", image1Uri);
                intent.putExtra("image2Url", image2Uri);
                intent.putExtra("image3Url", image3Uri);
                context.startActivity(intent);
            }
        });
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = model.getProductName();
                String priceCatalog = model.getPriceCatalog();
                String productDescription = model.getProductDescription();
                String ProductID = model.getId();

                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("id", ProductID);
                intent.putExtra("productName", productName);
                intent.putExtra("priceCatalog", priceCatalog);
                intent.putExtra("productDescription", productDescription);
                intent.putExtra("image1Url", image1Uri);
                intent.putExtra("image2Url", image2Uri);
                intent.putExtra("image3Url", image3Uri);
                context.startActivity(intent);

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder delete = new AlertDialog.Builder(context);
                delete.setTitle("Are you sure?");
                delete.setMessage("You will not be able to recover...");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String productId = items.get(position).getId();
                        String imageUrl1 = items.get(position).getImage1Url();
                        String imageUrl2 = items.get(position).getImage2Url();
                        String imageUrl3 = items.get(position).getImage3Url();

                        productsRef.child(productId).removeValue();
                        StorageReference imageRef1 = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl1);
                        StorageReference imageRef2 = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl2);
                        StorageReference imageRef3 = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl3);
                        imageRef1.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(context, "Failed to Delete Product", Toast.LENGTH_SHORT).show();
                            }
                        });

                        imageRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(context, "Failed to Delete Product", Toast.LENGTH_SHORT).show();
                            }
                        });

                        imageRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(context, "Failed to Delete Product", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Delete Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                delete.show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productDescription, priceCatalog;
        ImageView image1,image2,image3;
        private Button deleteBtn, updateBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_title);
            productDescription = itemView.findViewById(R.id.product_description);
            priceCatalog = itemView.findViewById(R.id.product_price);
            image1 = itemView.findViewById(R.id.product_image);
            deleteBtn = itemView.findViewById(R.id.delete);
            updateBtn = itemView.findViewById(R.id.update);

        }
    }
}