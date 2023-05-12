package com.example.kamhometools;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateProduct extends AppCompatActivity {
    private Button submit;
    ImageView image1, image2, image3;
    private Uri imageUri;
    TextInputEditText productname, product_description, priceCatalog;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("PostProducts");
    public static final int RESULT_OK = -1;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    public UpdateProduct() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        productname = findViewById(R.id.productname);
        product_description = findViewById(R.id.product_description);
        priceCatalog = findViewById(R.id.priceCatalog);
        submit = findViewById(R.id.submit);

        Intent retrieve = getIntent();
        String ProductID = retrieve.getStringExtra("id");
        String ProductName = retrieve.getStringExtra("productName");
        String ProductDesc = retrieve.getStringExtra("priceCatalog");
        String ProductPrice = retrieve.getStringExtra("productDescription");
        String ProductImageUrl = retrieve.getStringExtra("imageUri");

        productname.setText(ProductName);
        product_description.setText(ProductDesc);
        priceCatalog.setText(ProductPrice);
        Picasso.get().load(ProductImageUrl).into(image1);
        Picasso.get().load(ProductImageUrl).into(image2);
        Picasso.get().load(ProductImageUrl).into(image3);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedName = productname.getText().toString().trim();
                String updatedDescription = product_description.getText().toString().trim();
                String updatedPrice = priceCatalog.getText().toString().trim();

                // Create a map to hold the updated fields
                Map<String, Object> map = new HashMap<>();

                if (!updatedName.isEmpty()) {
                    map.put("productName", updatedName);
                }
                if (!updatedPrice.isEmpty()) {
                    map.put("priceCatalog", updatedPrice);
                }
                if (!updatedDescription.isEmpty()) {
                    map.put("productDescription", updatedDescription);
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
                                    map.put("imageUri", updatedImageURL);

                                    root.child(ProductID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UpdateProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                                finish(); // Finish the activity
                                            } else {
                                                Toast.makeText(UpdateProduct.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProduct.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Update the product with the new values
                    root.child(ProductID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Finish the activity
                            } else {
                                Toast.makeText(UpdateProduct.this, "Failed to update product", Toast.LENGTH_SHORT).show();
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
            image1.setImageURI(imageUri);
        }
    }

    private String getFileExtension (Uri mUri){

        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
    
}