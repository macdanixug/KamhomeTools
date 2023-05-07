package com.example.kamhometools;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class AdminMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductManagementFragment()).commit();
                break;
            case R.id.manage_blogs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlogsManagementFragment()).commit();
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent intent = new Intent(AdminMainPage.this, Login.class);
                startActivity(intent);
                finish();
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
