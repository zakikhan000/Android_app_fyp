package com.example.mssqlconnectivity;
public class OtpVerificationRequest {
    private String Email;
    private int Otp;

    public OtpVerificationRequest(String email, int otp) {
        Email = email;
        Otp = otp;
    }
}
