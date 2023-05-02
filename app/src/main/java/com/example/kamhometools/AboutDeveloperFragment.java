package com.example.kamhometools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AboutDeveloperFragment extends Fragment {

   Button project1,project2;
   ImageView twitter, facebook, github, linkedin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_developer, container, false);
//
        project1 = view.findViewById(R.id.project1);
        project2 = view.findViewById(R.id.project2);
        twitter = view.findViewById(R.id.twitter);
        facebook = view.findViewById(R.id.facebook);
        github = view.findViewById(R.id.github);
        linkedin = view.findViewById(R.id.linkedin);

        project1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Kam Pharmacy Coming soon in December", Toast.LENGTH_LONG).show();
            }
        });

        project2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AmecuOluga"));
                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Facebook Handle", Toast.LENGTH_SHORT).show();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/macdanixug"));
                startActivity(intent);
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/daniel-oluga-amecu-16b0161a5/"));
                startActivity(intent);
            }
        });

        return view;
    }
}