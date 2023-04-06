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


public class UserMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_menu,
                R.string.close_menu);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.view_pdt);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_pdt:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_chat:
                Toast.makeText(this, "Chat Fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.blogs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlogsFragment()).commit();
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "Profile Fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signup:
                Intent intent = new Intent(UserMainPage.this,Signup.class);
                startActivity(intent);
                finish();
                break;
            case R.id.about_developer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutDeveloperFragment()).commit();
                break;
            case R.id.nav_login:
            case R.id.nav_logout:
                Intent intent1 = new Intent(UserMainPage.this,MainActivity.class);
                startActivity(intent1);
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
