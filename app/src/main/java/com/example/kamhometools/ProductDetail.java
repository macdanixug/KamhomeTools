package com.example.kamhometools;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {
    String productName, productDescription, priceCatalog, image1Url,image2Url,image3Url;
    Button product_name_textview, call_button, message_button, add_cart_button, place_order_button;
    ImageView image1,image2,image3;
    TextView product_price_textview, description;

    public ProductDetail() {
        // Required empty public constructor
    }

    public ProductDetail(String productName, String productDescription, String priceCatalog, String image1Url, String image2Url, String image3Url){
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceCatalog = priceCatalog;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.image3Url = image3Url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String productName = getIntent().getStringExtra("productName");
        String priceCatalog = getIntent().getStringExtra("priceCatalog");
        String productDescription = getIntent().getStringExtra("productDescription");
        String imageUrl1 = getIntent().getStringExtra("image1Url");
        String imageUrl2 = getIntent().getStringExtra("image2Url");
        String imageUrl3 = getIntent().getStringExtra("image3Url");

        product_name_textview = findViewById(R.id.product_name_textview);
        product_price_textview = findViewById(R.id.product_price_textview);
        description = findViewById(R.id.description);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        call_button = findViewById(R.id.call_button);
        message_button = findViewById(R.id.message_button);
        add_cart_button = findViewById(R.id.add_cart_button);
        place_order_button = findViewById(R.id.place_order_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        product_name_textview.setText(productName);
        product_price_textview.setText(priceCatalog);
        description.setText(productDescription);
        Picasso.get().load(imageUrl1).into(image1);
        Picasso.get().load(imageUrl2).into(image2);
        Picasso.get().load(imageUrl3).into(image3);

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ProductDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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
                    Toast.makeText(ProductDetail.this, "Chatbox Clicked", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetail.this);
                    builder.setTitle("Login");
                    builder.setMessage("Please log in to access this feature.\nDo you wish to login to chat with our Admin?");
                    builder .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ProductDetail.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(ProductDetail.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ProductDetail.this, "Add to Cart Clicked", Toast.LENGTH_SHORT).show();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetail.this);
                    builder.setTitle("Login");
                    builder.setMessage("Please log in to access this feature.\nDo you wish to login to add product on the cart?");
                    builder .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ProductDetail.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(ProductDetail.this, "Cancelled", Toast.LENGTH_SHORT).show();
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

    }

    private void showPlaceOrderDialog() {
        // create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetail.this);
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

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}