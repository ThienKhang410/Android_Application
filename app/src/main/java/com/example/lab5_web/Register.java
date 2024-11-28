package com.example.lab5_web;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText regUsername, regPassword, regEmail;
    private Button btnRegister;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Liên kết giao diện
        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        regEmail = findViewById(R.id.reg_email);
        btnRegister = findViewById(R.id.btn_reg);

        // Khởi tạo Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Xử lý sự kiện nút Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regUsername.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String email = regEmail.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(username, password, email);
                }
            }
        });
    }

    private void registerUser(String username, String password, String email) {
        // Tạo một user mới
        String userId = databaseReference.push().getKey();
        if (userId != null) {
            User newUser = new User(username, password, email);
            databaseReference.child(userId).setValue(newUser)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            // Chuyển đến MainActivity hoặc màn hình khác
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Lớp model để lưu dữ liệu người dùng
    public static class User {
        public String username;
        public String password;
        public String email;

        public User() {
        }

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }
}
