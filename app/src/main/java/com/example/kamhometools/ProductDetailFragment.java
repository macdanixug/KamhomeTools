package com.example.kamhometools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {
    String productName, productDescription, priceCatalog, imageUri;
    Button product_name_textview, call_button, message_button, add_cart_button, place_order_button;
    ImageView image1,image2,image3;
    TextView product_price_textview, description;


    public ProductDetailFragment(){

    }
    public ProductDetailFragment(String productName, String priceCatalog, String productDescription, String imageUri){
        this.productName = productName;
        this.priceCatalog = priceCatalog;
        this.productDescription = productDescription;
        this.imageUri = imageUri;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        product_name_textview = view.findViewById(R.id.product_name_textview);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        description = view.findViewById(R.id.description);
        product_price_textview = view.findViewById(R.id.product_price_textview);

        call_button = view.findViewById(R.id.call_button);
        message_button = view.findViewById(R.id.message_button);
        add_cart_button = view.findViewById(R.id.add_cart_button);
        place_order_button = view.findViewById(R.id.place_order_button);

        product_name_textview.setText(productName);
        description.setText(productDescription);
        product_price_textview.setText("UGX " + priceCatalog);
        Picasso.get().load(imageUri).into(image1);
        Picasso.get().load(imageUri).into(image2);
        Picasso.get().load(imageUri).into(image3);

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Call Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Message Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        add_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "To be Implemented in the next update, Kindly be patient!", Toast.LENGTH_SHORT).show();
            }
        });
        place_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Place Order Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }
}

