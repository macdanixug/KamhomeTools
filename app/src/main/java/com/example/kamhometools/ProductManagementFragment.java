package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProductManagementFragment extends Fragment {

    public ProductManagementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        // Find the cardviews by their IDs
        CardView updateCard = view.findViewById(R.id.update_product_card);
        CardView addCard = view.findViewById(R.id.add_product_card);
        CardView deleteCard = view.findViewById(R.id.delete_product_card);

        // Set onClickListeners for each cardview
        updateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the UpdateFragment
                UpdateProductFragment updateFragment = new UpdateProductFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack(); // Remove old fragment from the back stack
                transaction.replace(R.id.product_management_fragment_container, updateFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the AdminFragment
                Fragment fragment = new AdminFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.product_management_fragment_container, fragment).commit();
            }
        });

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the DeleteFragment
                DeleteProductFragment deleteFragment = new DeleteProductFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.product_management_fragment_container, deleteFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }


}