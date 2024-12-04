package com.example.mssqlconnectivity;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("auth")
    Call<ApiResponse> getUsers();
    @GET("combined")
    Call<CombinedDataResponse> getCombinedUserData();
 //   @GET("user-images/{email}")
   // Call<UserImagesResponse> getUserProfileImages(@Path("email") String email);

    @GET("/users") // For fetching users
    Call<List<UserDetail>> getUser();

        @POST("/users") // For adding a new user
        Call<ResponseMessage> addUser(@Body UserDetail user);

        @PUT("/users/{email}") // For updating user by email
        Call<ResponseMessage> updateUserByEmail(@Path("email") String email, @Body UserDetail user);

        @DELETE("/users/{email}") // For deleting user by email
        Call<ResponseMessage> deleteUsersByEmail(@Path("email") String email);



    @POST("auth")
    Call<Void> registerUser(@Body UserRegistrationRequest userRequest);
    @POST("verify_otp")
    Call<Void> verifyOtp(@Body OtpVerificationRequest otpRequest);
   // @Multipart
   // @POST("user-images/{email}")
   // Call<Void> addUserProfileImage(@Path("email") String email, @Part MultipartBody.Part image);
    @DELETE("auth/{email}")  // Assuming 'email' is the identifier
    Call<Void> deleteUserByEmail(@Path("email") String email);

    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    //Image
    @GET("/user-images/{email}")
    Call<UserImagesResponse> getUserProfileImages(@Path("email") String email);


    @Multipart
    @POST("user-images/{email}")
    Call<Void> addUserProfileImage(@Path("email") String email, @Part MultipartBody.Part image);


    @Multipart
    @PUT("user-images/{email}/{upid}")
    Call<Void> updateUserProfileImage(
            @Path("email") String email,
            @Path("upid") int upid,
            @Part MultipartBody.Part image
    );

    @DELETE("user-images/{email}/{upid}")
    Call<Void> deleteUserProfileImage(
            @Path("email") String email,
            @Path("upid") int upid
    );
}