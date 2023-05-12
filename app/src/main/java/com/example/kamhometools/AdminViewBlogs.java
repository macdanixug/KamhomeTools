package com.example.kamhometools;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

public class AdminViewBlogs extends AppCompatActivity {
    String blog_title, blog_message, imageUrl,id;
    ImageView image;
    TextView title, message;

    public AdminViewBlogs() {

    }

    public AdminViewBlogs(String id, String blog_title, String imageUrl, String blog_message) {
        this.blog_title = blog_title;
        this.imageUrl = imageUrl;
        this.blog_message = blog_message;
        this.id = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_blogs);

        String blog_title = getIntent().getStringExtra("blog_title");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String blog_message = getIntent().getStringExtra("blog_message");

        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        image = findViewById(R.id.image);

        title.setText(blog_title);
        message.setText(blog_message);
        Picasso.get().load(imageUrl).into(image);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}