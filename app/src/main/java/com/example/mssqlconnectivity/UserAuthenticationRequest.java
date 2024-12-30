package com.example.mssqlconnectivity;

public class UserAuthenticationRequest {
    private String Username;
    private String PhoneNo;
    private String Password;
    private String ConfirmPassword;
    private Boolean Email_visibility;
    private Boolean Username_visibility;

    public UserAuthenticationRequest(String username, String phoneNo, String password, String confirmPassword, Boolean emailVisibility, Boolean usernameVisibility) {
        this.Username = username;
        this.PhoneNo = phoneNo;
        this.Password = password;
        this.ConfirmPassword = confirmPassword;
        this.Email_visibility = emailVisibility;
        this.Username_visibility = usernameVisibility;
    }

    // Getters and setters (if needed)
}
