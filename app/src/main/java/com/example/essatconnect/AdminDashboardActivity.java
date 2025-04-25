package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);  // Assurez-vous que le fichier XML est bien nommé

        // Initialisation des vues depuis le layout XML
        Button manageStudentsButton = findViewById(R.id.manageStudentsButton);  // Bouton pour gérer les étudiants
        Button manageNewsButton = findViewById(R.id.manageNewsButton);  // Bouton pour gérer les actualités
        Button manageEventsButton = findViewById(R.id.manageEventsButton);  // Bouton pour gérer les événements
        Button logoutButton = findViewById(R.id.logoutButton);  // Bouton de déconnexion

        // Bouton pour gérer les étudiants
        manageStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir une activité pour gérer les étudiants
                Intent intent = new Intent(AdminDashboardActivity.this, ManageStudentsActivity.class);
                startActivity(intent);
            }
        });

        // Bouton pour gérer les actualités
        manageNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité pour gérer les actualités
                Intent intent = new Intent(AdminDashboardActivity.this, ManageNewsActivity.class);
                startActivity(intent);
            }
        });

        // Logique pour gérer les événements
        manageEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité pour gérer les événements
                Intent intent = new Intent(AdminDashboardActivity.this, ManageEventsActivity.class);
                startActivity(intent);
            }
        });

        // Bouton de déconnexion
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers la page de connexion (par exemple, MainActivity)
                Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optionnel, pour finir cette activité
            }
        });
    }
}
