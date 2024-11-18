package com.example.lab5_web;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {

    private EditText regEmail, regUsername, regPassword;
    private Button btnRegister;
    private  SQLiteConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        regEmail = findViewById(R.id.reg_email);
        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        btnRegister = findViewById(R.id.btn_reg);

        db = new SQLiteConnector(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String username = regUsername.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(register.this, "All files are required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.checkUser(username)) {
                    Toast.makeText(register.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    return;
                }
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(username);
                newUser.setPassword(password);
                db.addUser(newUser);
                Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}