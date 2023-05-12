package com.example.kamhometools;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateBlog extends AppCompatActivity {
    Button blog_submit;
    ImageView blog_image;
    EditText blog_title, blog_message;
    private Uri imageUri;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Blogs");
    public static final int RESULT_OK = -1;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_blog);

        blog_image = findViewById(R.id.blog_image);
        blog_title = findViewById(R.id.blog_title);
        blog_message = findViewById(R.id.blog_message);
        blog_submit = findViewById(R.id.blog_submit);

        Intent retrieve = getIntent();
        String blogId = retrieve.getStringExtra("id");
        String blogTitle = retrieve.getStringExtra("blog_title");
        String blogMessage = retrieve.getStringExtra("blog_message");
        String blogImage = retrieve.getStringExtra("imageUrl");

        blog_title.setText(blogTitle);
        blog_message.setText(blogMessage);
        Picasso.get().load(blogImage).into(blog_image);

        blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        blog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = blog_title.getText().toString().trim();
                String updatedMessage = blog_message.getText().toString().trim();

                // Create a map to hold the updated fields
                Map<String, Object> map = new HashMap<>();

                if (!updatedTitle.isEmpty()) {
                    map.put("blog_title", updatedTitle);
                }
                if (!updatedMessage.isEmpty()) {
                    map.put("blog_message", updatedMessage);
                }
                if (imageUri != null) {
                    StorageReference storageRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                    storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the new image URL from Firebase Storage
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String updatedImageURL = uri.toString();
                                    map.put("imageUrl", updatedImageURL);

                                    root.child(blogId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UpdateBlog.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                                finish(); // Finish the activity
                                            } else {
                                                Toast.makeText(UpdateBlog.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateBlog.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Update the product with the new values
                    root.child(blogId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateBlog.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish the activity
                            } else {
                                Toast.makeText(UpdateBlog.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            blog_image.setImageURI(imageUri);
        }
    }

    private String getFileExtension (Uri mUri){

        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}