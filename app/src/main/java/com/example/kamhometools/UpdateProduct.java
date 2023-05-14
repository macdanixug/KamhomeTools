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
import androidx.annotation.Nullable;
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
    private Uri image1Uri, image2Uri, image3Uri;
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

        image1.setOnClickListener(v -> chooseimg(2));
        image2.setOnClickListener(v -> chooseimg(3));
        image3.setOnClickListener(v -> chooseimg(4));

        Intent retrieve = getIntent();
        String ProductID = retrieve.getStringExtra("id");
        String ProductName = retrieve.getStringExtra("productName");
        String ProductDesc = retrieve.getStringExtra("priceCatalog");
        String ProductPrice = retrieve.getStringExtra("productDescription");
        String ProductImageUrl1 = retrieve.getStringExtra("image1Url");
        String ProductImageUrl2 = retrieve.getStringExtra("image2Url");
        String ProductImageUrl3 = retrieve.getStringExtra("image3Url");

        productname.setText(ProductName);
        product_description.setText(ProductDesc);
        priceCatalog.setText(ProductPrice);
        Picasso.get().load(ProductImageUrl1).into(image1);
        Picasso.get().load(ProductImageUrl2).into(image2);
        Picasso.get().load(ProductImageUrl3).into(image3);

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
                if (image1Uri != null || image2Uri != null || image3Uri != null) {
                    if (image1Uri != null) {
                        StorageReference storageRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(image1Uri));
                        storageRef.putFile(image1Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the new image URL from Firebase Storage
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String updatedImageURL = uri.toString();
                                        map.put("image1Uri", updatedImageURL);
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
                    }

                    if (image2Uri != null) {
                        StorageReference storageRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(image2Uri));
                        storageRef.putFile(image2Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the new image URL from Firebase Storage
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String updatedImageURL = uri.toString();
                                        map.put("image2Uri", updatedImageURL);

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
                    }
                }
                else {
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

    private String getFileExtension (Uri mUri){

        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
    private void chooseimg(int requestCode) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            switch (requestCode){
                case 2:
                    image1.setImageURI(imageUri);
                    image1Uri = imageUri;
                    break;
                case 3:
                    image2.setImageURI(imageUri);
                    image2Uri = imageUri;
                    break;
                case 4:
                    image3.setImageURI(imageUri);
                    image3Uri = imageUri;
                    break;
            }
        }
    }


}