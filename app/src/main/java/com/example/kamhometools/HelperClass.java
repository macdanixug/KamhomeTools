package com.example.kamhometools;

public class HelperClass {

    private String name;
    private String email;
    private String contact;
    private String password;
    private String role;
    private String imageUrl;

    public HelperClass() {

    }

    public HelperClass(String name, String email, String contact, String password, String role, String imageUrl) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}