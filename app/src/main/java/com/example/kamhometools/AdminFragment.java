package com.example.kamhometools;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminFragment extends Fragment {

    private Button submit;
    private ProgressBar progressupload;
    private ImageView image1, image2, image3;
    private ProgressDialog progressDialog;
    private Uri image1Uri, image2Uri, image3Uri;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextInputEditText productname, product_description, priceCatalog;
    private DatabaseReference   root = FirebaseDatabase.getInstance().getReference("PostProducts");
    public static final int RESULT_OK = -1;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        image1.setOnClickListener(v -> chooseimg(2));
        image2.setOnClickListener(v -> chooseimg(3));
        image3.setOnClickListener(v -> chooseimg(4));
        productname = view.findViewById(R.id.productname);
        product_description = view.findViewById(R.id.product_description);
        priceCatalog = view.findViewById(R.id.priceCatalog);
        submit = view.findViewById(R.id.submit);
        progressDialog = new ProgressDialog(getActivity());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadpost();
            }
        });
        return view;
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

    private void uploadpost () {
        String pName = productname.getText().toString().trim();
        String pDescription = product_description.getText().toString().trim();
        String pPriceCatalog = priceCatalog.getText().toString().trim();
        if (image1.getDrawable() != null && image2.getDrawable() != null && image3.getDrawable() != null) {

            if (pName.isEmpty()) {
                productname.setError("Product Name is required");
                productname.requestFocus();
                return;
            }
            if (pDescription.isEmpty()) {
                product_description.setError("Description is required");
                product_description.requestFocus();
                return;
            }
            if (pPriceCatalog.isEmpty()) {
                priceCatalog.setError("Price Catalog is required");
                priceCatalog.requestFocus();
                return;
            }
            UploadToFirebase(image1Uri, image2Uri, image3Uri);
        } else {
            Toast.makeText(getActivity(), "Please Select Image", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void UploadToFirebase(Uri image1Uri, Uri image2Uri, Uri image3Uri) {

        if(image1Uri == null || image2Uri ==null || image3Uri ==null){
            Toast.makeText(getActivity(), "Please select the image", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("UPLOADING .. .. ..");
            progressDialog.setMessage("Please wait....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final StorageReference fileRef1 = reference.child(System.currentTimeMillis() + "_image1." + getFileExtension(image1Uri));
            final StorageReference fileRef2 = reference.child(System.currentTimeMillis() + "_image2." + getFileExtension(image2Uri));
            final StorageReference fileRef3 = reference.child(System.currentTimeMillis() + "_image3." + getFileExtension(image3Uri));

            fileRef1.putFile(image1Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            fileRef2.putFile(image2Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fileRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri2) {
                                            fileRef3.putFile(image3Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    fileRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri3) {
                                                            String pName = productname.getText().toString().trim();
                                                            String pDescription = product_description.getText().toString().trim();
                                                            String pPriceCatalog = priceCatalog.getText().toString().trim();
                                                            String productId = root.push().getKey();
                                                            PostProducts model = new PostProducts(productId, pName, pDescription, pPriceCatalog, uri1.toString(), uri2.toString(), uri3.toString());
                                                            root.child(productId).setValue(model);
                                                            progressDialog.dismiss();
                                                            productname.getText().clear();
                                                            product_description.getText().clear();
                                                            priceCatalog.getText().clear();
                                                            image1.setImageResource(R.drawable.upload);
                                                            image2.setImageResource(R.drawable.upload);
                                                            image3.setImageResource(R.drawable.upload);

                                                            Toast.makeText(getActivity(), "Upload Successful...", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private String getFileExtension (Uri mUri){

        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}