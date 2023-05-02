package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private TextView mNameTextView, mEmailTextView, mPhoneTextView, mAgeTextView, mAddressTextView;
    private Button mUpdateButton, editProfile;
    private ImageView myProfile;

    public ProfileFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        currentUser = mAuth.getCurrentUser();
        mDatabase.child(currentUser.getUid());

        // Initialize the TextViews and Button
        mNameTextView = view.findViewById(R.id.name);
        mEmailTextView = view.findViewById(R.id.email);
        mPhoneTextView = view.findViewById(R.id.phone);
        mUpdateButton = view.findViewById(R.id.update);
//        editProfile=view.findViewById(R.id.edit_profile);
        myProfile=view.findViewById(R.id.image);

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the user data from TextViews
                String name = mNameTextView.getText().toString();
                String email = mEmailTextView.getText().toString();
                String phone = mPhoneTextView.getText().toString();

                // Create a new instance of the update dialog fragment with the user data
                ProfileUpdateDialog dialog = ProfileUpdateDialog.newInstance(name, email, phone);
                dialog.show(getParentFragmentManager(), "Update Profile");
            }
        });

        return view;
    }

}