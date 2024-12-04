package com.example.mssqlconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity2 extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    Intent intent;
    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;


    Button nav_settings,nav_profile, nav_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_settings = findViewById(R.id.nav_settings);
        nav_profile = findViewById(R.id.nav_profile);
        nav_notification = findViewById(R.id.nav_notifications);
        Button createPostButton = findViewById(R.id.nav_create_post);

        createPostButton.setOnClickListener(v -> {
            // Inflate the custom layout
            View dialogView = getLayoutInflater().inflate(R.layout.dialogue_create_post, null);

            // Create the dialog
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create();

            // Set dialog window attributes
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            // Set up dialog interactions
            Button submitPostButton = dialogView.findViewById(R.id.submit_post);
            submitPostButton.setOnClickListener(view -> {
                // Handle submit action
                EditText postContent = dialogView.findViewById(R.id.post_content);
                String postText = postContent.getText().toString().trim();

                if (!postText.isEmpty()) {
                    Toast.makeText(this, "Post Submitted: " + postText, Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Close dialog
                } else {
                    Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show(); // Display the dialog
        });



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



        nav_settings.setOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainprofileActivity.class);
                startActivity(intent);
            }
        });

        nav_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });


      /*  BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

       bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
           int itemId = item.getItemId();

            if (itemId == R.id.nav_settings) {
                drawerLayout.openDrawer(GravityCompat.END);  // Open drawer from the right
                return true;
            } else {
                // Handle other navigation items
                return false;
            }
        });
*/
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
    }
    private void nextActivity(Class<?> targetActivity) {
        Intent i = new Intent(this, targetActivity);
        i.putExtra("email", email);
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
    }
}
