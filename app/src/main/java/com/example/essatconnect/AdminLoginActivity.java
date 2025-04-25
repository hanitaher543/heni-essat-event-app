package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginActivity extends AppCompatActivity {

    // Déclaration des champs de texte et du bouton
    EditText emailEditText, passwordEditText;
    Button loginButton;
    FirebaseAuth mAuth; // Instance de FirebaseAuth
    FirebaseDatabase database; // Référence à la base de données Realtime
    DatabaseReference adminRef; // Référence à la table des administrateurs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login); // Lien avec le fichier XML pour l'interface

        // Initialisation des éléments de l'interface
        emailEditText = findViewById(R.id.emailEditText); // Champ de texte pour l'email
        passwordEditText = findViewById(R.id.passwordEditText); // Champ de texte pour le mot de passe
        loginButton = findViewById(R.id.loginButton); // Bouton de connexion

        mAuth = FirebaseAuth.getInstance(); // Initialiser FirebaseAuth
        database = FirebaseDatabase.getInstance(); // Initialiser la base de données
        adminRef = database.getReference("admin"); // Référence à la table des administrateurs

        // Action lorsque l'utilisateur clique sur le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim(); // Récupérer l'email
            String password = passwordEditText.getText().toString().trim(); // Récupérer le mot de passe

            // Vérifier si l'email et le mot de passe sont remplis
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Connexion via Firebase Auth
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Une fois l'authentification réussie, vérifier les informations dans la base de données
                                checkIfAdmin(email, password); // Vérification des informations de l'admin
                            }
                        } else {
                            // Afficher une erreur si l'authentification échoue
                            Toast.makeText(AdminLoginActivity.this, "Erreur de connexion : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    // Vérification si l'utilisateur est un administrateur dans la base de données
    private void checkIfAdmin(String email, String password) {
        adminRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    // Récupérer les données de l'admin
                    String storedEmail = task.getResult().child("email").getValue(String.class);
                    String storedPassword = task.getResult().child("password").getValue(String.class);

                    // Vérification des identifiants de l'administrateur
                    if (storedEmail != null && storedPassword != null &&
                            storedEmail.equals(email) && storedPassword.equals(password)) {
                        // L'utilisateur est un administrateur, redirection vers le tableau de bord admin
                        Toast.makeText(AdminLoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminLoginActivity.this, AdminDashboardActivity.class));
                        finish();
                    } else {
                        // Si l'utilisateur n'est pas un administrateur ou les informations sont incorrectes
                        Toast.makeText(AdminLoginActivity.this, "Accès refusé. Vous n'êtes pas un administrateur.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Données d'administrateur introuvables.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AdminLoginActivity.this, "Erreur de récupération des données", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
