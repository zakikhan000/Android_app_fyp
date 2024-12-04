package com.example.mssqlconnectivity;

public class LoginRequest {
    private String Email;
    private String Password;

    public LoginRequest(String email, String password) {
        this.Email = email;
        this.Password = password;
    }
}

