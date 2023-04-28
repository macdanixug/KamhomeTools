package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {
    TextView profileName, profileEmail, profilePassword,profileContact;
    TextView titleName, titleUsername;
    Button editProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileContact = view.findViewById(R.id.profileContact);
        profilePassword = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        editProfile = view.findViewById(R.id.editProfile);

//        passUserData();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                passUserData();
            }
        });

        return view;
    }

//    public void passUserData(){
//
////        Intent intent = new Intent(getActivity(), Login.class);
//        Intent intent = getActivity().getIntent();
//        String nameUser = intent.getStringExtra("name");
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(nameUser);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//
//                    String nameFromDB = snapshot.child("name").getValue(String.class);
//                    String contactFromDB = snapshot.child("contact").getValue(String.class);
//                    String emailFromDB = snapshot.child("email").getValue(String.class);
//                    String passwordFromDB = snapshot.child("password").getValue(String.class);
//
//                    titleName.setText(nameFromDB);
//                    titleUsername.setText(emailFromDB);
//                    profileName.setText(nameFromDB);
//                    profileContact.setText(contactFromDB);
//                    profileEmail.setText(emailFromDB);
//                    profilePassword.setText(passwordFromDB);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//


}