package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<PostProducts> list;
    productAdapter adapter;
    ImageView poster1, poster2, poster3;
    final private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PostProducts");

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView= view.findViewById(R.id.recview);
        poster1 = view.findViewById(R.id.poster1);
        poster2 = view.findViewById(R.id.poster2);
        poster3 = view.findViewById(R.id.poster3);

        Picasso.get()
                .load(R.drawable.poster3)
                .resize(158,0)
                .centerCrop()
                .into(poster1);
        Picasso.get()
                .load(R.drawable.poster2)
                .resize(158,0)
                .centerCrop()
                .into(poster2);
        Picasso.get()
                .load(R.drawable.poster1)
                .resize(158,0)
                .centerCrop()
                .into(poster3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list= new ArrayList<>();
        adapter = new productAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve data from dataSnapshot and add it to your RecyclerView adapter
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostProducts object = snapshot.getValue(PostProducts.class);
                    list.add(object);
                }

                productAdapter adapter = new productAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });


        return  view;
    }

}