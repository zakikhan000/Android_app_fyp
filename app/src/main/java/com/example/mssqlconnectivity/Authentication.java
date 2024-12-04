package com.example.mssqlconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Authentication extends AppCompatActivity {

    String email, password, conpassword, username, phone, fn,mn,ln,age,country,city,an,postal;

     EditText et1, et2,et3,et4,et5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);
        Button btn = findViewById(R.id.updatebt);
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

        et2 = findViewById(R.id.et_username);
        et3 = findViewById(R.id.et_phone);

        et1 = findViewById(R.id.et_email);
        et1.setText(email);
        et2.setText(username);
        et3.setText(phone);


        EditText et1 = findViewById(R.id.et_old_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals("123"))
                {

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your Old Password is incorrect", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}