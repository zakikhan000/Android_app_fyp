package com.example.mssqlconnectivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedDataResponse {

    @SerializedName("combined_data")
    private List<CombinedData> combinedData;

    public List<CombinedData> getCombinedData() {
        return combinedData;
    }

    public void setCombinedData(List<CombinedData> combinedData) {
        this.combinedData = combinedData;
    }

    public static class CombinedData {

        @SerializedName("UAID")
        private int uaid;

        @SerializedName("Username")
        private String username;

        @SerializedName("First_Name")
        private String firstName;

        @SerializedName("Middle_Name")
        private String middleName;

        @SerializedName("Anonymous_Name")
        private String anonymousName;

        @SerializedName("Age")
        private int age;

        @SerializedName("Country")
        private String country;

        @SerializedName("City")
        private String city;

        @SerializedName("Postal_Code")
        private String postalCode;

        @SerializedName("Real_Image")
        private String realImage;

        @SerializedName("Profile_visibility")
        private String profileVisibility;

        @SerializedName("Name_visibility")
        private String nameVisibility;

        @SerializedName("Username_visibility")
        private String usernameVisibility;

        @SerializedName("PID")
        private int pid;

        @SerializedName("Title")
        private String title;

        @SerializedName("Content")
        private String content;

        @SerializedName("Created_At")
        private String createdAt;

        // Getters and setters for all fields
        public int getUaId() {
            return uaid;
        }

        public void setUaId(int uaid) {
            this.uaid = uaid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getAnonymousName() {
            return anonymousName;
        }

        public void setAnonymousName(String anonymousName) {
            this.anonymousName = anonymousName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getRealImage() {
            return realImage;
        }

        public void setRealImage(String realImage) {
            this.realImage = realImage;
        }

        public String getProfileVisibility() {
            return profileVisibility;
        }

        public void setProfileVisibility(String profileVisibility) {
            this.profileVisibility = profileVisibility;
        }

        public String getNameVisibility() {
            return nameVisibility;
        }

        public void setNameVisibility(String nameVisibility) {
            this.nameVisibility = nameVisibility;
        }

        public String getUsernameVisibility() {
            return usernameVisibility;
        }

        public void setUsernameVisibility(String usernameVisibility) {
            this.usernameVisibility = usernameVisibility;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
