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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
                Map<String, Object> updateMap = new HashMap<>();

                if (!updatedName.isEmpty()) {
                    updateMap.put("productName", updatedName);
                    root.child(ProductID).updateChildren(updateMap);
                }
                if (!updatedPrice.isEmpty()) {
                    updateMap.put("priceCatalog", updatedPrice);
                    root.child(ProductID).updateChildren(updateMap);
                }
                if (!updatedDescription.isEmpty()) {
                    updateMap.put("productDescription", updatedDescription);
                    root.child(ProductID).updateChildren(updateMap);
                }
                if (image1Uri == null || image2Uri ==null || image3Uri ==null) {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                    if (image1Uri != null) {
                        String fileName = System.currentTimeMillis() + "." + getFileExtension(image1Uri);
                        StorageReference fileRef = storageRef.child(fileName);
                        fileRef.putFile(image1Uri).addOnSuccessListener(taskSnapshot -> {
                            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                updateMap.put("image1Url", uri.toString());
                                root.child(ProductID).updateChildren(updateMap);
                            });
                        });
                    }else if(image1.getDrawable() != null){
                        Toast.makeText(UpdateProduct.this, "Product Updated Successful", Toast.LENGTH_SHORT).show();
                    }

                    if (image2Uri != null) {
                        String fileName = System.currentTimeMillis() + "." + getFileExtension(image2Uri);
                        StorageReference fileRef = storageRef.child(fileName);
                        fileRef.putFile(image2Uri).addOnSuccessListener(taskSnapshot -> {
                            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                updateMap.put("image2Url", uri.toString());
                                root.child(ProductID).updateChildren(updateMap);
                            });
                        });
                    }else if(image2.getDrawable() != null){
                        Toast.makeText(UpdateProduct.this, "Product Updated Successful", Toast.LENGTH_SHORT).show();
                    }

                    if (image3Uri != null) {
                        String fileName = System.currentTimeMillis() + "." + getFileExtension(image3Uri);
                        StorageReference fileRef = storageRef.child(fileName);
                        fileRef.putFile(image3Uri).addOnSuccessListener(taskSnapshot -> {
                            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                updateMap.put("image3Url", uri.toString());
                                root.child(ProductID).updateChildren(updateMap);
                            });
                        });
                    }else if(image3.getDrawable() != null){
                        Toast.makeText(UpdateProduct.this, "Product Updated Successful", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(UpdateProduct.this, "Product Update failed", Toast.LENGTH_SHORT).show();
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