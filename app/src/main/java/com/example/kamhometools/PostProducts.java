package com.example.kamhometools;

public class PostProducts {
    private String id;
    private String productName;
    private String productDescription;
    private String priceCatalog;
    private String image1Url,image2Url,image3Url;

    public PostProducts() {
    }

    public PostProducts(String id, String productName, String productDescription, String priceCatalog, String image1Url, String image2Url, String image3Url) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceCatalog = priceCatalog;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.image3Url = image3Url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }
}