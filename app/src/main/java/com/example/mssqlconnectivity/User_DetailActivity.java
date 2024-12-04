package com.example.mssqlconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_DetailActivity extends AppCompatActivity {

    private EditText firstNameEditText, middleNameEditText, lastNameEditText, ageEditText,
            countryEditText, cityEditText, anonymousNameEditText, postalCodeEditText, emailEditText;
    private TextView userListTextView;
    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Intent i = getIntent();
        email = i.getStringExtra("email");
        password = i.getStringExtra("pass");
        conpassword = i.getStringExtra("cpass");
        username = i.getStringExtra("username");
        phone = i.getStringExtra("phone");

        firstNameEditText = findViewById(R.id.first_name);
        middleNameEditText = findViewById(R.id.middle_name);
        lastNameEditText = findViewById(R.id.last_name);
        ageEditText = findViewById(R.id.age);
        countryEditText = findViewById(R.id.country);
        cityEditText = findViewById(R.id.city);
        anonymousNameEditText = findViewById(R.id.anonymous_name);
        postalCodeEditText = findViewById(R.id.postal_code);
        emailEditText = findViewById(R.id.email);
        userListTextView = findViewById(R.id.user_list);

        Button addUserButton = findViewById(R.id.add_user);
        Button updateUserButton = findViewById(R.id.update_user);
        Button deleteUserButton = findViewById(R.id.delete_user);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                getUsers();
            }
        });
        updateUserButton.setOnClickListener(v -> updateUser());
        deleteUserButton.setOnClickListener(v -> deleteUser());

        // Fetch users on activity creation
    }

    private void getUsers() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<UserDetail>> call = apiService.getUser(); // Use UserDetail here
        call.enqueue(new Callback<List<UserDetail>>() {
            @Override
            public void onResponse(Call<List<UserDetail>> call, Response<List<UserDetail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserDetail> users = response.body(); // Use UserDetail here
                    StringBuilder userList = new StringBuilder("User List:\n");
                    for (UserDetail user : users) { // Use UserDetail here
                        userList.append(user.getFirst_Name()).append(" ").append(user.getLast_Name()).append("\n");
                    }
                    userListTextView.setText(userList.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDetail>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUser() {
        UserDetail newUser = new UserDetail(); // Change User to UserDetail
        newUser.setFirst_Name(firstNameEditText.getText().toString());
        newUser.setMiddle_Name(middleNameEditText.getText().toString());
        newUser.setLast_Name(lastNameEditText.getText().toString());
        newUser.setAge(Integer.parseInt(ageEditText.getText().toString()));
        newUser.setCountry(countryEditText.getText().toString());
        newUser.setCity(cityEditText.getText().toString());
        newUser.setAnonymous_name(anonymousNameEditText.getText().toString());
        newUser.setPostal_Code(postalCodeEditText.getText().toString());
        newUser.setEmail(email);

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<ResponseMessage> call = apiService.addUser(newUser);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                     fn = firstNameEditText.getText().toString();
                     mn = middleNameEditText.getText().toString();
                     ln = lastNameEditText.getText().toString();
                     age = ageEditText.getText().toString();
                     country = countryEditText.getText().toString();
                     city = cityEditText.getText().toString();
                     an = anonymousNameEditText.getText().toString();
                     postal =postalCodeEditText.getText().toString();
                    i.putExtra("email", email);
                    i.putExtra("pass", password);
                    i.putExtra("phone", phone);
                    i.putExtra("username", username);
                    i.putExtra("cpass", conpassword);
                    i.putExtra("fn", fn);
                    i.putExtra("mn", mn);
                    i.putExtra("ln", ln);
                    i.putExtra("age", age);
                    i.putExtra("country", country);
                    i.putExtra("city", city);
                    i.putExtra("an",an);
                    i.putExtra("postal", postal);
                     startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        String email = emailEditText.getText().toString();
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
                    getUsers(); // Refresh user list after updating
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

    private void deleteUser() {
        String email = emailEditText.getText().toString();
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<ResponseMessage> call = apiService.deleteUsersByEmail(email);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getUsers(); // Refresh user list after deleting
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
