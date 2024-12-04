package com.example.mssqlconnectivity;

import static kotlinx.coroutines.DelayKt.delay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText emailEditText;
    private ImageView profileImageView;
    private Button uploadButton, updateButton, deleteButton, fetchButton;
    private ApiService apiService;
    private Uri selectedImageUri;
    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        profileImageView = findViewById(R.id.profileImageView);
        uploadButton = findViewById(R.id.uploadButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        fetchButton = findViewById(R.id.fetchButton);

        // Initialize ApiService
        apiService = ApiClient.getApiClient().create(ApiService.class);

        // Upload image
        uploadButton.setOnClickListener(v -> openImageChooser());

        // Update image
        updateButton.setOnClickListener(v -> updateUserProfileImage());

        // Delete image
        deleteButton.setOnClickListener(v -> deleteUserProfileImage());

        // Fetch image
        fetchButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               fetchUserProfileImage();
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
        );
    }

    // Open image chooser to select image
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);
        }
    }

    // Upload user profile image
    private void uploadUserProfileImage() {
        if (selectedImageUri == null) {
            Toast.makeText(getApplicationContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty() || selectedImageUri == null) {
            Toast.makeText(getApplicationContext(), "Please enter an email and Image Url", Toast.LENGTH_SHORT).show();

            return;
        }


        try {
            // Get the bitmap and compress it
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            byte[] compressedData = compressImage(bitmap); // Compress the image

            // Prepare the request body
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), compressedData);
            MultipartBody.Part body = MultipartBody.Part.createFormData("Real_Image", "profile.jpg", requestFile);

            // Call the API
            Call<Void> call = apiService.addUserProfileImage(email, body);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to upload image: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ProfileActivity", "Error uploading image", t);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
        }
    }


    // Fetch user profile image
    private void fetchUserProfileImage() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<UserImagesResponse> call = apiService.getUserProfileImages(email);
        call.enqueue(new Callback<UserImagesResponse>() {
            @Override
            public void onResponse(Call<UserImagesResponse> call, Response<UserImagesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String base64ImageData = response.body().getImageData();
                    if (base64ImageData != null && !base64ImageData.isEmpty()) {
                        // Decode base64 string to byte array
                        byte[] imageData = Base64.decode(base64ImageData, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        profileImageView.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getApplicationContext(), "Image data is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("ProfileActivity", "Failed to fetch image: " + response.message());
                    Toast.makeText(getApplicationContext(), "Failed to fetch image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserImagesResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProfileActivity", "Error fetching image", t);
            }
        });
    }


    // Update user profile image
    private void updateUserProfileImage() {
        // For simplicity, the update functionality can call the same method as upload
        uploadUserProfileImage();
    }

    // Delete user profile image
    private void deleteUserProfileImage() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        int upid = 1; // Change this based on UPID needed
        Call<Void> call = apiService.deleteUserProfileImage(email, upid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Image deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProfileActivity", "Error deleting image", t);
            }
        });
    }

    // Method to compress the image
    public byte[] compressImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream); // Compress to 50% quality
        return byteArrayOutputStream.toByteArray();
    }
}
