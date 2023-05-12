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

import java.util.ArrayList;
import java.util.List;

public class adminBlogAdapter extends RecyclerView.Adapter<adminBlogAdapter.myViewHolder>{

    private Context context;
    private ArrayList<BlogModel> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference blogsRef = database.getReference().child("Blogs");
    private adminBlogAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(BlogModel item);
    }

    public adminBlogAdapter(Context context, List<BlogModel> list) {
        this.context = context;
        this.list = (ArrayList<BlogModel>) list;
    }


    public void setOnItemClickListener(adminBlogAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public adminBlogAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_update_design, parent, false);
        return new adminBlogAdapter.myViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull adminBlogAdapter.myViewHolder holder, int position) {
        BlogModel model = list.get(position);
        holder.blog_title.setText(model.getBlog_title());
        String imageUri;
        imageUri = model.getImageUrl();
        Picasso.get().load(imageUri).into(holder.blog_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the Details activity
                Intent intent = new Intent(context, AdminViewBlogs.class);
                // Put the title, image URL, and message of the clicked item as extras in the intent
                intent.putExtra("blog_title", model.getBlog_title());
                intent.putExtra("imageUrl", model.getImageUrl());
                intent.putExtra("blog_message", model.getBlog_message());

                // Start the Details activity with the intent
                context.startActivity(intent);
            }
        });


        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blogTitle = model.getBlog_title();
                String blogMessage = model.getBlog_message();
                String blogImage = model.getImageUrl();
                String blogId = model.getId();

                Intent intent = new Intent(context, UpdateBlog.class);
                intent.putExtra("id", blogId);
                intent.putExtra("blog_title", blogTitle);
                intent.putExtra("imageUrl", blogImage);
                intent.putExtra("blog_message", blogMessage);
                context.startActivity(intent);

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder delete = new AlertDialog.Builder(context);
                delete.setTitle("Are you sure you want to delete this Blog?");
                delete.setMessage("You will not be able to recover this Blog...");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String BlogsId = list.get(position).getId();
                        String imageUrl = list.get(position).getImageUrl();

                        blogsRef.child(BlogsId).removeValue();
                        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // File deleted successfully
                                Toast.makeText(context, "Blog Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // An error occurred while deleting the file
                                Toast.makeText(context, "Failed to Delete Blog", Toast.LENGTH_SHORT).show();
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
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView blog_title, blog_message;
        ImageView blog_image;
        Button deleteBtn, updateBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_message = itemView.findViewById(R.id.blog_message);
            blog_image = itemView.findViewById(R.id.blog_image);
            deleteBtn = itemView.findViewById(R.id.delete);
            updateBtn = itemView.findViewById(R.id.update);

        }
    }

}

