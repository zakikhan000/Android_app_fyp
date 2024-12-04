package com.example.mssqlconnectivity;

import java.util.List;

public class ApiResponse {
    private List<User> auth_users;

    public List<User> getAuthUsers() {
        return auth_users;
    }

    public void setAuthUsers(List<User> auth_users) {
        this.auth_users = auth_users;
    }

    public List<UserDetail> getUser() {

        return java.util.Collections.emptyList();
    }
}
