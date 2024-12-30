package com.example.mssqlconnectivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication extends AppCompatActivity {

    private EditText etEmail, etUsername, etPhone, etPassword, etConfirmPassword;
    private Button updateButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // Initialize UI components
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_username);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        updateButton = findViewById(R.id.updatebt);

        // Initialize API client
        apiService = ApiClient.getApiClient().create(ApiService.class);

        // Set button click listener
        updateButton.setOnClickListener(v -> updateAuthentication());
    }

    private void updateAuthentication() {
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String phoneNo = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the request object
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                username,
                phoneNo,
                password,
                confirmPassword,
                Boolean.TRUE,  // Example value for email visibility
                Boolean.TRUE   // Example value for username visibility
        );

        // Make the API call
        Call<ResponseBody> call = apiService.updateUserAuthentication(email, request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Authentication.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Authentication.this, "Update Failed! Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Authentication.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
