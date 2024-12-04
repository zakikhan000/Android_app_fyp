package com.example.mssqlconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPhone, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSubmit, nextButton, deleteButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        //nextButton = findViewById(R.id.nextSubmit);
       // deleteButton = findViewById(R.id.buttonDelete);
        progressBar = findViewById(R.id.progressBar);  // Ensure you have a ProgressBar in your layout

        // Set OnClickListener for Next button (to navigate to MainActivity)


        // Set OnClickListener for Submit button
        buttonSubmit.setOnClickListener(v -> {
            submitUserData();  // Submit user data when the button is clicked
        });

        // Set OnClickListener for Delete button
       // deleteButton.setOnClickListener(v -> deleteUserData());
    }

    // Method to submit user data
    private void submitUserData() {
        // Hide the Register button and show progress bar
        buttonSubmit.setVisibility(View.GONE);  // Hide Register button
        progressBar.setVisibility(View.VISIBLE); // Show ProgressBar (loading)

        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Validate inputs
        if (email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE); // Hide ProgressBar on failure
            buttonSubmit.setVisibility(View.VISIBLE); // Show the Register button again
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE); // Hide ProgressBar on failure
            buttonSubmit.setVisibility(View.VISIBLE); // Show the Register button again
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE); // Hide ProgressBar on failure
            buttonSubmit.setVisibility(View.VISIBLE); // Show the Register button again
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE); // Hide ProgressBar on failure
            buttonSubmit.setVisibility(View.VISIBLE); // Show the Register button again
            return;
        }

        // Create a user registration request
        UserRegistrationRequest userRequest = new UserRegistrationRequest(email, username, password, confirmPassword, phone);

        // Call the API for registration
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<Void> call = apiService.registerUser(userRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE); // Hide ProgressBar after response

                if (response.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "OTP sent to your email. Please verify.", Toast.LENGTH_SHORT).show();
                    showOtpDialog(email); // Show OTP dialog
                } else if (response.code() == 409) {
                    Toast.makeText(UserActivity.this, "Email or Username already registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                }

                // Show the Register button again after failure
                buttonSubmit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Hide ProgressBar on failure
                Toast.makeText(UserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                buttonSubmit.setVisibility(View.VISIBLE); // Show the Register button again
            }
        });
    }

    // Method to show OTP dialog
    private void showOtpDialog(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialogue_otp, null);
        builder.setView(dialogView);
        EditText editTextOtp = dialogView.findViewById(R.id.editTextOtp);
        Button buttonVerifyOtp = dialogView.findViewById(R.id.buttonVerifyOtp);
        builder.setTitle("Verify OTP")
                .setCancelable(true);
        final AlertDialog dialog = builder.create();
        buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpString = editTextOtp.getText().toString();

                if (otpString.isEmpty()) {
                    Toast.makeText(UserActivity.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                int otp = Integer.parseInt(otpString);
                OtpVerificationRequest otpRequest = new OtpVerificationRequest(email, otp);
                ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
                Call<Void> call = apiService.verifyOtp(otpRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(UserActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss(); // Close dialog on successful registration
                            Intent i = new Intent(getApplicationContext(), User_DetailActivity.class);
                            String email = editTextEmail.getText().toString();
                            String password = editTextPassword.getText().toString();
                            String conpassword = editTextPassword.getText().toString();
                            String phoneno = editTextPhone.getText().toString();
                            String username = editTextUsername.getText().toString();
                            i.putExtra("email", email);
                            i.putExtra("pass", password);
                            i.putExtra("phone", phoneno);
                            i.putExtra("username", username);
                            i.putExtra("cpass", conpassword);
                            startActivity(i);
                        } else {
                            Toast.makeText(UserActivity.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(UserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
    // Method to delete user data
    private void deleteUserData() {
        String email = editTextEmail.getText().toString();  // Get the email from EditText

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<Void> call = apiService.deleteUserByEmail(email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserActivity.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
