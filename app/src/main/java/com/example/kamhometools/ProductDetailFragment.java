package com.example.kamhometools;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:+256776100100"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
//                    return;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256776100100"));
                    startActivity(callIntent);
                }


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
                showPlaceOrderDialog();
            }
        });


        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private void showPlaceOrderDialog() {
        // create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set dialog title
        builder.setTitle("Place Order");

        // set dialog message
        builder.setMessage("Choose how you want to place your order:");

        // add buttons to dialog
        builder.setPositiveButton("WhatsApp Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // redirect to WhatsApp number
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/256776100100"));
                startActivity(intent);
            }
        });


        builder.setNeutralButton("Order on MTN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // redirect to MTN number
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256776100100"));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Order on Chat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256200944200"));
//                startActivity(intent);
            }
        });

        builder.setNegativeButton("Order on Airtel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256200944200"));
                startActivity(intent);

            }
        });

        // create and show dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

