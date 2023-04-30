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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminFragment extends Fragment {

    private Button submit;
    private ProgressBar progressupload;
    private ImageView image1, image2, image3;
    private ProgressDialog progressDialog;
    private Uri imageUri;
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
        image1.setOnClickListener(v -> chooseimg());
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

    private void chooseimg() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            image1.setImageURI(imageUri);
//            image2.setImageURI(imageUri);
//            image3.setImageURI(imageUri);
        }
    }


    private void uploadpost () {
        if (imageUri != null) {

            String pName = productname.getText().toString().trim();
            String pDescription = product_description.getText().toString().trim();
            String pPriceCatalog = priceCatalog.getText().toString().trim();
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
            UploadToFirebase(imageUri);
        } else {
            Toast.makeText(getActivity(), "Please Select Image", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void UploadToFirebase (Uri uri){
        // if (currentUser != null) {
//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
        progressDialog.setTitle("UPLOADING .. .. ..");
        progressDialog.setMessage("Please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = root.push().getKey();

                        PostProducts model = new PostProducts(productname.getText().toString(), product_description.getText().toString(), priceCatalog.getText().toString(), uri.toString());
                        root.child(modelId).setValue(model);
                        progressDialog.dismiss();
                        productname.getText().clear();
                        product_description.getText().clear();
                        priceCatalog.getText().clear();
                        image1.setImageResource(0);product_description.getText().toString();
                        Toast.makeText(getActivity(), "Upload Sucessful", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_LONG)
                        .show();

            }
        });

    }
    private String getFileExtension (Uri mUri){

        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}