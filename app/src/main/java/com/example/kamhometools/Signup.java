package com.example.kamhometools;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Signup extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword, signupContact;
    ImageView image;
    TextView loginRedirectText;
    Button signupButton;

    private ProgressDialog progressDialog;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    public static final int RESULT_OK = -1;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    private Uri imageUri;
    ProgressBar progressBar;

    private static final int IMAGE_PICK_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupContact = findViewById(R.id.signup_contact);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadpost();
            }
        });


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
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
            image.setImageURI(imageUri);
        }
    }
    private void uploadpost () {
        if (imageUri != null) {

            String uName = signupName.getText().toString().trim();
            String uEmail = signupEmail.getText().toString().trim();
            String uContact = signupContact.getText().toString().trim();
            String uUsername = signupUsername.getText().toString().trim();
            String uPassword = signupPassword.getText().toString().trim();

            if (uName.isEmpty()) {
                signupName.setError("Name is required");
                signupName.requestFocus();
                return;
            }
            if (uEmail.isEmpty()) {
                signupEmail.setError("Email is required");
                signupEmail.requestFocus();
                return;
            }
            if (uContact.isEmpty()) {
                signupContact.setError("Contact is required");
                signupContact.requestFocus();
                return;
            }
            if (uUsername.isEmpty()) {
                signupUsername.setError("Username is required");
                signupUsername.requestFocus();
                return;
            }
            if (uPassword.isEmpty()) {
                signupPassword.setError("Password is required");
                signupPassword.requestFocus();
                return;
            }
            UploadToFirebase(imageUri);
        } else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_LONG).show();
        }
    }

    private void UploadToFirebase (Uri uri){
        progressDialog.setTitle("Signing up .. .. ..");
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

                        HelperClass model = new HelperClass(signupName.getText().toString(), signupEmail.getText().toString(), signupContact.getText().toString(), signupUsername.getText().toString(), signupPassword.getText().toString(), uri.toString());
                        root.child(modelId).setValue(model);
                        progressDialog.dismiss();

                        signupName.getText().clear();
                        signupContact.getText().clear();
                        signupEmail.getText().clear();
                        signupUsername.getText().clear();
                        signupPassword.getText().clear();
                        image.setImageResource(0);

//                        blog_message.getText().toString();
                        Toast.makeText(Signup.this, "Signup Succesfull", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Signup.this, "Blog Upload Failed", Toast.LENGTH_LONG).show();

            }
        });

    }
    private String getFileExtension (Uri mUri){

        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}