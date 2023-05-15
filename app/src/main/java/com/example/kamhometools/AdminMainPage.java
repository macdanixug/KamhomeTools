package com.example.kamhometools;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class AdminMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser currentUser;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ImageView profilePic = headerView.findViewById(R.id.admin_icon);
        TextView profileName = headerView.findViewById(R.id.name);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            mDatabase.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Get the user data from the snapshot
                    String name = snapshot.child("name").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);

                    profileName.setText(name);
                    Picasso.get().load(imageUrl).into(profilePic);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
        else{
            profileName.setText("Full name");
            profilePic.setImageResource(R.drawable.ic_launcher_background);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_menu,
                R.string.close_menu);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminFragment()).commit();
            navigationView.setCheckedItem(R.id.post_drugs);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_drugs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminFragment()).commit();
                break;
            case R.id.post_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostBlogsFragment()).commit();
                break;
            case R.id.admin_chat:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostBlogsFragment()).commit();
                Toast.makeText(this, "Chat Box selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.manage_pdts:
                EditText inputPassword = new EditText(this);
                inputPassword.setHint("Enter password");
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password,0,0,0);
                inputPassword.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.edit_text_drawable_padding));

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                        .setTitle("Enter Password")
                        .setView(inputPassword)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Check if the entered password matches the user's password
                                String password = inputPassword.getText().toString();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uid = currentUser.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userPassword = snapshot.child("password").getValue(String.class);
                                        if (password.equals(userPassword)) {
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductManagementFragment()).commit();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect password, try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e(TAG, "onCancelled: " + error.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder2.show();
                break;

            case R.id.manage_blogs:
                EditText inputPassword3 = new EditText(this);
                inputPassword3.setHint("Enter password");
                inputPassword3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPassword3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password,0,0,0);
                inputPassword3.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.edit_text_drawable_padding));

                AlertDialog.Builder builder3 = new AlertDialog.Builder(this)
                        .setTitle("Enter Password")
                        .setView(inputPassword3)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Check if the entered password matches the user's password
                                String password = inputPassword3.getText().toString();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uid = currentUser.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userPassword = snapshot.child("password").getValue(String.class);
                                        if (password.equals(userPassword)) {
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlogsManagementFragment()).commit();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect password, try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e(TAG, "onCancelled: " + error.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder3.show();
                break;

            case R.id.manage_users:
                // Prompt the user to enter password
                EditText inputPassword2 = new EditText(this);
                inputPassword2.setHint("Enter password");
                inputPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPassword2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password,0,0,0);
                inputPassword2.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.edit_text_drawable_padding));

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Enter Password")
                        .setView(inputPassword2)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Check if the entered password matches the user's password
                                String password = inputPassword2.getText().toString();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String uid = currentUser.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userPassword = snapshot.child("password").getValue(String.class);
                                        if (password.equals(userPassword)) {
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserViewFragment()).commit();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect password, try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e(TAG, "onCancelled: " + error.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.show();
                break;

            case R.id.nav_logout:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure you want to log out?")
                        .setTitle("Log Out")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                Intent intent = new Intent(AdminMainPage.this, UserMainPage.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
