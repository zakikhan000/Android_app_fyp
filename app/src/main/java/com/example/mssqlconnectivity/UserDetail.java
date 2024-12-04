package com.example.mssqlconnectivity;

public class UserDetail {

        private String First_Name;
        private String Middle_Name;
        private String Last_Name;
        private Integer Age;
        private String Country;
        private String City;
        private String Anonymous_name;
        private String Postal_Code;
        private String email;

        // Getters and Setters
        public String getFirst_Name() {
            return First_Name;
        }

        public void setFirst_Name(String first_Name) {
            First_Name = first_Name;
        }

        public String getMiddle_Name() {
            return Middle_Name;
        }

        public void setMiddle_Name(String middle_Name) {
            Middle_Name = middle_Name;
        }

        public String getLast_Name() {
            return Last_Name;
        }

        public void setLast_Name(String last_Name) {
            Last_Name = last_Name;
        }

        public Integer getAge() {
            return Age;
        }

        public void setAge(Integer age) {
            Age = age;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getAnonymous_name() {
            return Anonymous_name;
        }

        public void setAnonymous_name(String anonymous_name) {
            Anonymous_name = anonymous_name;
        }

        public String getPostal_Code() {
            return Postal_Code;
        }

        public void setPostal_Code(String postal_Code) {
            Postal_Code = postal_Code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
