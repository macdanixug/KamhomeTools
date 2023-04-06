package com.example.kamhometools;

public class BlogModel {
    private String blog_title, blog_message;
    private String imageUrl;

    public BlogModel(String blog_title, String blog_message, String imageUrl) {
        this.blog_title = blog_title;
        this.blog_message = blog_message;
        this.imageUrl = imageUrl;
    }

    public BlogModel() {
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_message() {
        return blog_message;
    }

    public void setBlog_message(String blog_message) {
        this.blog_message = blog_message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
