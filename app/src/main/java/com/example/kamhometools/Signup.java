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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {

    EditText signupName, signupEmail, signupPassword, signupContact;
    ImageView image;
    TextView loginRedirectText;
    Button signupButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    public static final int RESULT_OK = -1;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    private static final int IMAGE_PICK_CODE = 1000;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupContact = findViewById(R.id.signup_contact);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        image = findViewById(R.id.image);

        progressDialog = new ProgressDialog(this);

        image.setOnClickListener(v -> chooseimg());

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadPost();
                } else {
                    Toast.makeText(Signup.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
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
//        Opening Gallery
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
    private void uploadPost () {
        if (imageUri != null) {
            String uName = signupName.getText().toString().trim();
            String uEmail = signupEmail.getText().toString().trim();
            String uContact = signupContact.getText().toString().trim();
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
            if (uPassword.isEmpty()) {
                signupPassword.setError("Password is required");
                signupPassword.requestFocus();
                return;
            }
            else {
                mAuth.createUserWithEmailAndPassword(signupEmail.getText().toString(), signupPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UploadToFirebase(imageUri);
                        }
                    }
                });
            }
        }
    }
    private void UploadToFirebase (Uri uri) {
        progressDialog.setTitle("Signing up in progress.. .. ..");
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
                        String uName = signupName.getText().toString().trim();
                        String uEmail = signupEmail.getText().toString().trim();
                        String uContact = signupContact.getText().toString().trim();
                        String uPassword = signupPassword.getText().toString().trim();
                        String Role="User";

                        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
                        String userID = root.push().getKey();
                        // Create a new user object with the data
                        HelperClass user = new HelperClass(userID,uName, uEmail, uContact, uPassword, Role, uri.toString());
                        // Get a reference to the "users" node
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();

                                            signupName.getText().clear();
                                            signupContact.getText().clear();
                                            signupEmail.getText().clear();
                                            signupPassword.getText().clear();
                                            image.setImageResource(R.drawable.upload);
                                            signupName.requestFocus();

                                            Toast.makeText(Signup.this, "Signup Successfully", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(Signup.this, "Error Failed to register", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

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
                Toast.makeText(Signup.this, "Signup Failed Failed", Toast.LENGTH_LONG).show();
            }
        });
    }


    //Getting File Extension
    private String getFileExtension (Uri mUri){

        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}