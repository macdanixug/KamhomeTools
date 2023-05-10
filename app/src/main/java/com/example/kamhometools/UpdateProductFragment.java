package com.example.kamhometools;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateProductFragment extends Fragment {

    private Button submit;
    private ProgressBar progressupload;
    private ImageView image1, image2, image3;
    private ProgressDialog progressDialog;
    private Uri imageUri;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextInputEditText productname, product_description, priceCatalog;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("PostProducts");
    public static final int RESULT_OK = -1;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    public UpdateProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        productname = view.findViewById(R.id.productname);
        product_description = view.findViewById(R.id.product_description);
        priceCatalog = view.findViewById(R.id.priceCatalog);
        submit = view.findViewById(R.id.submit);
        progressDialog = new ProgressDialog(getActivity());



        return view;
    }
}
