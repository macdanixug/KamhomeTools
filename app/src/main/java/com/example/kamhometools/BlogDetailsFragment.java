package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class BlogDetailsFragment extends Fragment {
    String blog_title, blog_message, imageUrl;
    ImageView image;
    TextView title, message;

    public BlogDetailsFragment() {
    }
    public BlogDetailsFragment(String blog_title, String imageUrl, String blog_message) {
        this.blog_title = blog_title;
        this.imageUrl = imageUrl;
        this.blog_message = blog_message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_details, container, false);

        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
        image = view.findViewById(R.id.image);

        title.setText(blog_title);
        message.setText(blog_message);
        Picasso.get().load(imageUrl).into(image);

        return view;
    }
    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new HomeFragment()).commit();

    }
}