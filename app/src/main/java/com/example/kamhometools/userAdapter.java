package com.example.kamhometools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class userAdapter extends RecyclerView.Adapter<userAdapter.myViewHolder>{
    private Context context;
    private ArrayList<HelperClass> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference().child("Users");
    private userAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(HelperClass item);
    }

    public userAdapter(Context context, List<HelperClass> list) {
        this.context = context;
        this.list = (ArrayList<HelperClass>) list;
    }


    public void setOnItemClickListener(userAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public userAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_view_design, parent, false);
        return new userAdapter.myViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull userAdapter.myViewHolder holder, int position) {
        HelperClass model = list.get(position);
        holder.user_fullname.setText(model.getName());
        holder.user_contact.setText(model.getContact());
        holder.user_email.setText(model.getEmail());
        String imageUri;
        imageUri = model.getImageUrl();
        Picasso.get().load(imageUri).into(holder.user_image);



        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String blogTitle = model.getBlog_title();
//                String blogMessage = model.getBlog_message();
//                String blogImage = model.getImageUrl();
//                String blogId = model.getId();
//
//                Intent intent = new Intent(context, UpdateBlog.class);
//                intent.putExtra("id", blogId);
//                intent.putExtra("blog_title", blogTitle);
//                intent.putExtra("imageUrl", blogImage);
//                intent.putExtra("blog_message", blogMessage);
//                context.startActivity(intent);
                Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show();

            }
        });
        
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder delete = new AlertDialog.Builder(context);
                delete.setTitle("Are you sure you want to delete this User?");
                delete.setMessage("You will not be able to recover this user...");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userId = list.get(position).getId();
                        String imageUrl = list.get(position).getImageUrl();

                        userRef.child(userId).removeValue();
                        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // File deleted successfully
                                Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // An error occurred while deleting the file
                                Toast.makeText(context, "Failed to Delete User", Toast.LENGTH_SHORT).show();
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
        TextView user_fullname, user_contact, user_email;
        ImageView user_image, delete_button, edit_button;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            user_fullname = itemView.findViewById(R.id.user_fullname);
            user_contact = itemView.findViewById(R.id.user_contact);
            user_email = itemView.findViewById(R.id.user_email);
            user_image = itemView.findViewById(R.id.user_image);
            delete_button = itemView.findViewById(R.id.delete_button);
            edit_button = itemView.findViewById(R.id.edit_button);

        }
    }

}

