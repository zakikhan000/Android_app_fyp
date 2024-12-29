package com.example.mssqlconnectivity;

public class CreatePostRequest {

    private String email;
    private String title;
    private String content;

    public CreatePostRequest(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}