package com.example.mssqlconnectivity;

public class UserRegistrationRequest {
    private String Email;
    private String Username;
    private String Password;
    private String ConfirmPassword;
    private String PhoneNo;

    public UserRegistrationRequest(String email, String username, String password, String confirmPassword, String phoneNo) {
        Email = email;
        Username = username;
        Password = password;
        ConfirmPassword = confirmPassword;
        PhoneNo = String.valueOf(phoneNo);
    }

    public UserRegistrationRequest(String username, String phoneNo, String password, String confirmPassword, Boolean aTrue) {
    }
}
