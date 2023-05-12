package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserViewFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HelperClass> list;
    userAdapter adapter;
    TextView user_count_textview;
    final private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

    public UserViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_view, container, false);

        recyclerView= view.findViewById(R.id.recview);
        user_count_textview= view.findViewById(R.id.user_count_textview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list= new ArrayList<>();
        adapter = new userAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numUsers = (int) dataSnapshot.getChildrenCount();
                user_count_textview.setText(String.valueOf(numUsers));
                // Retrieve data from dataSnapshot and add it to your RecyclerView adapter
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HelperClass object = snapshot.getValue(HelperClass.class);
                    list.add(object);
                }

                userAdapter adapter = new userAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });


        return view;
    }
}