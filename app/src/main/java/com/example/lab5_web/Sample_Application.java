package com.example.lab5_web;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sample_Application extends AppCompatActivity {

    private TextView sappContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sample_application);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_sample_application);
        sappContent = findViewById(R.id.Sapp_content);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        if(username != null && !username.isEmpty()){
            sappContent.setText("Hello " + username);

        }else{
            sappContent.setText("Hello user");
        }
    }
}