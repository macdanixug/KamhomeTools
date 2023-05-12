package com.example.kamhometools;

public class PostProducts {
    private String productName;
    private String productDescription;
    private String priceCatalog;
    private String imageUri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public PostProducts(String id, String productName, String productDescription, String priceCatalog, String imageUri) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceCatalog = priceCatalog;
        this.imageUri = imageUri;
        this.id = id;

    }

    public PostProducts() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPriceCatalog() {
        return priceCatalog;
    }

    public void setPriceCatalog(String priceCatalog) {
        this.priceCatalog = priceCatalog;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}
