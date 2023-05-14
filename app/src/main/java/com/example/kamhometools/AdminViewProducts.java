package com.example.kamhometools;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

public class AdminViewProducts extends AppCompatActivity {

    String productName, productDescription, priceCatalog, image1Url,image2Url,image3Url;
    Button product_name_textview;
    ImageView image1,image2,image3;
    TextView product_price_textview, description;

    public AdminViewProducts() {
        // Required empty public constructor
    }

    public AdminViewProducts(String productName, String productDescription, String priceCatalog, String image1Url, String image2Url, String image3Url){
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
        setContentView(R.layout.activity_admin_view_products);

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

        product_name_textview.setText(productName);
        product_price_textview.setText(priceCatalog);
        description.setText(productDescription);
        Picasso.get().load(imageUrl1).into(image1);
        Picasso.get().load(imageUrl2).into(image2);
        Picasso.get().load(imageUrl3).into(image3);

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