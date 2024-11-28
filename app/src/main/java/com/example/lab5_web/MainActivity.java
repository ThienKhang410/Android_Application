//package com.example.lab5_web;
//
//import com.google.firebase.database.*;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//    private DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Khởi tạo Firebase Realtime Database
//        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//
//        // Gọi phương thức kiểm tra thông tin đăng nhập
//        checkLogin("admin", "admin");
//    }
//
//    private void checkLogin(String username, String password) {
//        databaseReference.orderByChild("username").equalTo(username)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            boolean isValid = false;
//
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                String dbPassword = snapshot.child("password").getValue(String.class);
//                                Log.d("FirebaseDebug", "Username: " + username + ", Password từ Firebase: " + dbPassword);
//
//                                if (dbPassword != null && dbPassword.equals(password)) {
//                                    isValid = true;
//                                    break;
//                                }
//                            }
//
//                            if (isValid) {
//                                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(MainActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Log.d("FirebaseDebug", "Không tìm thấy dữ liệu với username: " + username);
//                            Toast.makeText(MainActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
//                    }
//                });
//
//    }
//
//    private void registerUser(String username, String password) {
//        String userId = databaseReference.push().getKey(); // Tạo ID duy nhất
//        User user = new User(username, password);
//        databaseReference.child(userId).setValue(user)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(MainActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}

package com.example.lab5_web;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Liên kết giao diện
        edtUsername = findViewById(R.id.login_username);
        edtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        // Xử lý khi nhấn nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    checkLogin(username, password);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }

    private void checkLogin(String username, String password) {
        databaseReference.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isValid = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String dbPassword = snapshot.child("password").getValue(String.class);
                            if (dbPassword != null && dbPassword.equals(password)) {
                                isValid = true;
                                break;
                            }
                        }

                        if (isValid) {
                            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            // Chuyển đến giao diện SampleApplicationActivity
                            Intent intent = new Intent(MainActivity.this, Sample_Application.class);
                            intent.putExtra("USERNAME", username);
                            startActivity(intent);
                            finish(); // Kết thúc MainActivity nếu không cần quay lại
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
