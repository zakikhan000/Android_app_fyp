package com.example.mssqlconnectivity;
public class User {
    private String Created_on;
    private String Email;
    private String PhoneNo;
    private int UAID;
    private String Updated_on;
    private String Username;
    private String Password;

    // Getters and Setters
    public String getCreatedOn() {
        return Created_on;
    }

    public void setCreatedOn(String created_on) {
        Created_on = created_on;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public int getUAID() {
        return UAID;
    }

    public void setUAID(int UAID) {
        this.UAID = UAID;
    }

    public String getUpdatedOn() {
        return Updated_on;
    }

    public void setUpdatedOn(String updated_on) {
        Updated_on = updated_on;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword(){ return Password;}
}
