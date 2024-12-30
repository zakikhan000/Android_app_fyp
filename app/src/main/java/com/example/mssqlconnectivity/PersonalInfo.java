package com.example.mssqlconnectivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class PersonalInfo extends AppCompatActivity {
    private EditText firstNameEditText, middleNameEditText, lastNameEditText, ageEditText,
            countryEditText, cityEditText, anonymousNameEditText, postalCodeEditText, emailEditText;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        firstNameEditText = findViewById(R.id.first_name);
        middleNameEditText = findViewById(R.id.middle_name);
        lastNameEditText = findViewById(R.id.last_name);
        ageEditText = findViewById(R.id.age);
        countryEditText = findViewById(R.id.country);
        cityEditText = findViewById(R.id.city);
        anonymousNameEditText = findViewById(R.id.anonymous_name);
        postalCodeEditText = findViewById(R.id.postal_code);
        emailEditText = findViewById(R.id.email);
        Button updateUserButton = findViewById(R.id.update_user);
        updateUserButton.setOnClickListener(v -> updateUser());
    }
    private void updateUser() {
        String email = "khanzaki006006@gmail.com";
        UserDetail updatedUser = new UserDetail(); // Change User to UserDetail
        updatedUser.setFirst_Name(firstNameEditText.getText().toString());
        updatedUser.setMiddle_Name(middleNameEditText.getText().toString());
        updatedUser.setLast_Name(lastNameEditText.getText().toString());
        updatedUser.setAge(Integer.parseInt(ageEditText.getText().toString()));
        updatedUser.setCountry(countryEditText.getText().toString());
        updatedUser.setCity(cityEditText.getText().toString());
        updatedUser.setAnonymous_name(anonymousNameEditText.getText().toString());
        updatedUser.setPostal_Code(postalCodeEditText.getText().toString());

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<ResponseMessage> call = apiService.updateUserByEmail(email, updatedUser);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   // Refresh user list after updating
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}