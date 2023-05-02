package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUpdateDialog extends DialogFragment {

    private EditText mNameEditText, mEmailEditText, mPhoneEditText;
    private Button mUpdateButton;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_dialog, container, false);

        mNameEditText = view.findViewById(R.id.name);
        mEmailEditText = view.findViewById(R.id.email);
        mPhoneEditText = view.findViewById(R.id.phone);
        mUpdateButton = view.findViewById(R.id.edit_profile);

        // Get the current user and database reference
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Retrieve the user data from arguments
        Bundle args = getArguments();
        String name = args.getString("name");
        String email = args.getString("email");
        String phone = args.getString("contact");

        // Set the EditTexts with the user data
        mNameEditText.setText(name);
        mEmailEditText.setText(email);
        mPhoneEditText.setText(phone);
        mEmailEditText.setEnabled(false);

        // Set onClickListener for the update button
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the user data from EditTexts
                String name = mNameEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String phone = mPhoneEditText.getText().toString();

                // Update the user data in the Firebase database
                mDatabase.child(currentUser.getUid()).child("name").setValue(name);
                mDatabase.child(currentUser.getUid()).child("email").setValue(email);
                mDatabase.child(currentUser.getUid()).child("contact").setValue(phone);

                // Notify the user that the profile has been updated
                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                // Close the dialog
                dismiss();
            }
        });

        return view;
    }

    public static ProfileUpdateDialog newInstance(String name, String email, String phone) {
        ProfileUpdateDialog fragment = new ProfileUpdateDialog();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("email", email);
        args.putString("phone", phone);
        fragment.setArguments(args);
        return fragment;
    }

    public interface ProfileUpdateListener {
        void onProfileUpdated(String name, String email, String phone);
    }

}
