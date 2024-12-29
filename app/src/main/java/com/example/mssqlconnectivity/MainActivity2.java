package com.example.mssqlconnectivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.example.mssqlconnectivity.ApiClient;
import com.example.mssqlconnectivity.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    Intent intent;
    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;

    Button nav_settings, nav_profile, nav_notification;
    private RecyclerView recyclerView;
    private CombinedDataAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button createPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize UI components
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_settings = findViewById(R.id.nav_settings);
        nav_profile = findViewById(R.id.nav_profile);
        nav_notification = findViewById(R.id.nav_notifications);
        createPostButton = findViewById(R.id.nav_create_post);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data initially
        loadData();

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> loadData());

        // Create Post button functionality
        createPostButton.setOnClickListener(v -> showCreatePostDialog());

        // Handle Navigation Drawer item clicks
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.nav_privacy) {
                nextActivity(Privacy.class);
            } else if (itemId == R.id.nav_authentication) {
                nextActivity(Authentication.class);
            } else if (itemId == R.id.nav_personal_info) {
                nextActivity(PersonalInfo.class);
            } else if (itemId == R.id.nav_logout) {
                nextActivity(Login.class);
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        // Get passed intent data
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

        // Open settings drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Button nav_settings = findViewById(R.id.nav_settings); // Assuming it's a Button

        nav_settings.setOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // Navigate to profile
        nav_profile.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), MainprofileActivity.class);
            startActivity(intent);
        });

        // Navigate to notifications
        nav_notification.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);
        });
    }

    // Load data for RecyclerView
    private void loadData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<CombinedDataResponse> call = apiService.getCombinedData();

        call.enqueue(new Callback<CombinedDataResponse>() {
            @Override
            public void onResponse(Call<CombinedDataResponse> call, Response<CombinedDataResponse> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false); // Stop refresh animation

                if (response.isSuccessful()) {
                    CombinedDataResponse combinedDataResponse = response.body();
                    adapter = new CombinedDataAdapter(combinedDataResponse.getCombinedData());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity2.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CombinedDataResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false); // Stop refresh animation
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show the post creation dialog
    private void showCreatePostDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_post, null);

        EditText emailEditText = dialogView.findViewById(R.id.editTextEmail);
        EditText titleEditText = dialogView.findViewById(R.id.editTextTitle);
        EditText contentEditText = dialogView.findViewById(R.id.editTextContent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Create New Post")
                .setPositiveButton("Create Post", (dialog, which) -> {
                    String email = emailEditText.getText().toString();
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();

                    if (email.isEmpty() || title.isEmpty() || content.isEmpty()) {
                        Toast.makeText(MainActivity2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        createPost(email, title, content);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Handle post creation
    private void createPost(String email, String title, String content) {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        CreatePostRequest postRequest = new CreatePostRequest(email, title, content);

        Call<CreatePostResponse> call = apiService.createPost(postRequest);

        call.enqueue(new Callback<CreatePostResponse>() {
            @Override
            public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity2.this, "Post created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Failed to create post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextActivity(Class<?> targetActivity) {
        Intent i = new Intent(this, targetActivity);
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
        i.putExtra("an", an);
        i.putExtra("postal", postal);
        startActivity(i);
    }
}
