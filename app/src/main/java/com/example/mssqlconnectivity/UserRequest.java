package com.example.mssqlconnectivity;


public class UserRequest {

    private String username;
    private String phoneNo;
    private String password;
    private String confirmPassword;
    private boolean emailVisibility;

    // Constructor
    public UserRequest(String username, String phoneNo, String password, String confirmPassword, boolean emailVisibility) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.emailVisibility = emailVisibility;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isEmailVisibility() {
        return emailVisibility;
    }

    public void setEmailVisibility(boolean emailVisibility) {
        this.emailVisibility = emailVisibility;
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", emailVisibility=" + emailVisibility +
                '}';
    }
}
