package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<PostProducts> list;
    adminProductAdapter adapter;
    final private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PostProducts");

    public ProductManagementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Loading Products");
        builder.setMessage("Please wait......");
        AlertDialog dialog = builder.create();
        dialog.show();

        recyclerView= view.findViewById(R.id.recview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list= new ArrayList<>();
        adapter = new adminProductAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                // Retrieve data from dataSnapshot and add it to your RecyclerView adapter
                int productCount = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostProducts object = snapshot.getValue(PostProducts.class);
                    list.add(object);
                    productCount++;
                }

                Collections.sort(list, new Comparator<PostProducts>(){
                    @Override
                    public int compare(PostProducts p1, PostProducts p2){
                        return p1.getProductName().compareToIgnoreCase(p2.getProductName());
                    }
                });

                adminProductAdapter adapter = new adminProductAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();

                TextView user_count_textview = getView().findViewById(R.id.user_count_textview);
                user_count_textview.setText(String.valueOf(productCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        return view;
    }

}