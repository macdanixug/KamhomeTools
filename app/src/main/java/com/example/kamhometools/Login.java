package com.example.kamhometools;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText,forgot;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail= findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgot = findViewById(R.id.forgot);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = loginEmail.getText().toString().trim();
                String Password = loginPassword.getText().toString().trim();
                if(Email.isEmpty()){
                    loginEmail.setError("Email is Required*");
                    loginEmail.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    loginEmail.setError("Valid Email Required*");
                    loginEmail.requestFocus();
                    return;
                }else if(Password.isEmpty()){
                    loginPassword.setError("Password Required*");
                    loginPassword.requestFocus();
                    return;
                }else if (Password.length()<8) {
                    loginPassword.setError("Password Should be at least 8 characters*");
                    loginPassword.requestFocus();
                    return;
                }else{
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String myUserId = currentUser.getUid();
//                                Toast.makeText(Login.this,myUserId, Toast.LENGTH_SHORT).show();

                                if (myUserId.equals("kls4XrRKonT5NcNjfZYTZmmr4yB2")) {
                                    //grant access to database
                                    Toast.makeText(Login.this, "Admin Logged in Successful", Toast.LENGTH_SHORT).show();
                                    Intent dash=new Intent(Login.this, AdminMainPage.class);
                                    startActivity(dash);
                                    finish();
                                }else{
                                    Toast.makeText(Login.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent customer = new Intent(Login.this, UserMainPage.class);
                                    startActivity(customer);
                                    finish();
//                                    Toast.makeText(Login.this, "ID: "+myUserId, Toast.LENGTH_SHORT).show();
                                }
//
                            }else{

                                Toast.makeText(Login.this, "Login Failed try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

//        Creating account
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });



    }
}