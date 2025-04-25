package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button studentButton, adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des boutons
        studentButton = findViewById(R.id.studentButton);
        adminButton = findViewById(R.id.adminButton);

        // Gestion du clic sur le bouton Espace Étudiant
        studentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
            startActivity(intent);
        });

        // Gestion du clic sur le bouton Espace Administrateur
        adminButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}
