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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {
    String productName, productDescription, priceCatalog, image1Url, image2Url, image3Url;
    Button product_name_textview, call_button, message_button, add_cart_button, place_order_button;
    ImageView image1,image2,image3;
    TextView product_price_textview, description;
    public ProductDetailFragment(){

    }
    public ProductDetailFragment(String productName, String productDescription, String priceCatalog, String image1Url, String image2Url, String image3Url){
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceCatalog = priceCatalog;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.image3Url = image3Url;
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        product_name_textview.setText(productName);
        description.setText(productDescription);
        product_price_textview.setText("UGX " + priceCatalog);
        Picasso.get().load(image1Url).into(image1);
        Picasso.get().load(image2Url).into(image2);
        Picasso.get().load(image3Url).into(image3);

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:+256776100100"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
//                    return;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+256776100100"));
                    startActivity(callIntent);
                }


            }
        });
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user !=null){
                    Toast.makeText(getActivity(), "Chatbox Clicked", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Login");
                    builder.setMessage("Please log in to access this feature.\nDo you wish to login to chat with our Admin?");
                    builder .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getActivity(), Login.class);
                                    startActivity(intent);
                                    closeFragment();
                                }
                            });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

            }
        });
        add_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user !=null){
                    Toast.makeText(getActivity(), "Add to Cart Clicked", Toast.LENGTH_SHORT).show();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Login");
                    builder.setMessage("Please log in to access this feature.\nDo you wish to login to add product on the cart?");
                    builder .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);
                            closeFragment();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


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
        builder.setTitle("Place Order");
        builder.setMessage("Choose how you want to place your order:");

        builder.setPositiveButton("WhatsApp Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
    public void closeFragment(){
        if (isAdded() && getActivity() !=null){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}

