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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostBlogsFragment extends Fragment {

    private Button blog_submit;
    private ImageView blog_image;
    private Uri imageUri;
    private ProgressBar progressupload;
    private ProgressDialog progressDialog;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private EditText blog_title, blog_message;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Blogs");
    public static final int RESULT_OK = -1;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    public PostBlogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_blogs, container, false);


        blog_image = view.findViewById(R.id.blog_image);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        blog_image.setOnClickListener(v -> chooseimg());
        blog_title = view.findViewById(R.id.blog_title);
        blog_message = view.findViewById(R.id.blog_message);
        blog_submit = view.findViewById(R.id.blog_submit);
        progressDialog = new ProgressDialog(getActivity());

        blog_submit.setOnClickListener(new View.OnClickListener() {
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
            blog_image.setImageURI(imageUri);
        }
    }
    private void uploadpost () {
        if (imageUri != null) {

            String pBlogTitle = blog_title.getText().toString().trim();
            String pBlogMessage = blog_message.getText().toString().trim();
            if (pBlogTitle.isEmpty()) {
                blog_title.setError("Blog Title is required");
                blog_title.requestFocus();
                return;
            }
            if (pBlogMessage.isEmpty()) {
                blog_message.setError("Blog Message is empty");
                blog_message.requestFocus();
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
        progressDialog.setTitle("Uploading Blog .. .. ..");
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
                        String blogId = root.push().getKey();
                        BlogModel model = new BlogModel(blogId,blog_title.getText().toString(), blog_message.getText().toString(), uri.toString());
                        root.child(blogId).setValue(model);
                        progressDialog.dismiss();
                        blog_title.getText().clear();
                        blog_message.getText().clear();
                        blog_image.setImageResource(R.drawable.upload);
//                        blog_message.getText().toString();
                        Toast.makeText(getActivity(), "Blog Upload Sucessful", Toast.LENGTH_LONG)
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
                Toast.makeText(getActivity(), "Blog Upload Failed", Toast.LENGTH_LONG)
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