package com.example.mssqlconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private ApiService apiService;
    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        Intent i = getIntent();
        email = i.getStringExtra("email");
        password = i.getStringExtra("pass");
        conpassword = i.getStringExtra("cpass");
        username = i.getStringExtra("username");
        phone = i.getStringExtra("phone");
        fn = i.getStringExtra("fn");
        mn = i.getStringExtra("mn");
        ln = i.getStringExtra("ln");
        age = i.getStringExtra("age");
        country = i.getStringExtra("country");
        city = i.getStringExtra("city");
        an = i.getStringExtra("an");
        postal = i.getStringExtra("postal");
        Button loginButton = findViewById(R.id.loginButton);
        Button reg = findViewById(R.id.RegisterButton);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
            }
        });

        apiService = ApiClient.getApiClient().create(ApiService.class);

        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    String email = emailEditText.getText().toString();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
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
                    Log.e("Login", "Response Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "Login failed! Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
