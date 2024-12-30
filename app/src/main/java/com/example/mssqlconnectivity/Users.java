package com.example.mssqlconnectivity;

public class Users {
    private String First_Name;
    private String Middle_Name;
    private String Last_Name;
    private int Age;
    private String Country;
    private String City;
    private String Anonymous_name;
    private String Postal_Code;
    private boolean Name_visibility;
    private boolean PersonalInfo_visibility;

    // Constructor
    public Users(String firstName, String middleName, String lastName, int age, String country, String city,
                String anonymousName, String postalCode, boolean nameVisibility, boolean personalInfoVisibility) {
        this.First_Name = firstName;
        this.Middle_Name = middleName;
        this.Last_Name = lastName;
        this.Age = age;
        this.Country = country;
        this.City = city;
        this.Anonymous_name = anonymousName;
        this.Postal_Code = postalCode;
        this.Name_visibility = nameVisibility;
        this.PersonalInfo_visibility = personalInfoVisibility;
    }
}
