package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisterActivity extends AppCompatActivity {

    private DatabaseReference reference; // Définir comme variable d'instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        // Initialisation des éléments de l'interface utilisateur
        EditText cinEditText = findViewById(R.id.cinEditText);
        EditText fullnameEditText = findViewById(R.id.fullnameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText classEditText = findViewById(R.id.classEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);
        TextView loginRedirectText = findViewById(R.id.loginRedirectText);

        // Initialisation de Firebase Auth et DatabaseReference
        FirebaseAuth auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("students"); // Initialisé ici

        // Logic pour le bouton d'inscription
        registerButton.setOnClickListener(v -> {
            String CIN = cinEditText.getText().toString().trim();
            String fullname = fullnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String studentClass = classEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validation des champs
            if (CIN.isEmpty() || fullname.isEmpty() || email.isEmpty() || studentClass.isEmpty() || password.isEmpty()) {
                Toast.makeText(StudentRegisterActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validation du CIN (doit contenir exactement 8 chiffres)
            if (!CIN.matches("\\d{8}")) {
                Toast.makeText(StudentRegisterActivity.this, "Le CIN doit contenir exactement 8 chiffres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validation de l'email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(StudentRegisterActivity.this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Inscription avec Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(StudentRegisterActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Sauvegarde des informations dans la base de données sans le mot de passe
                            saveUserInDatabase(CIN, fullname, email, studentClass, password);
                        } else {
                            // Gestion des erreurs
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Erreur inconnue";

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                errorMessage = "Cet utilisateur existe déjà.";
                            }

                            Toast.makeText(StudentRegisterActivity.this, "Echec de l'inscription : " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Redirection vers la page de connexion
        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(StudentRegisterActivity.this, StudentLoginActivity.class);
            startActivity(intent);
        });
    }

    // Sauvegarde des informations dans Firebase Realtime Database sans le mot de passe
    private void saveUserInDatabase(String CIN, String fullname, String email, String studentClass, String password) {
        // Création de l'objet Student avec mot de passe
        Student student = new Student(CIN, fullname, email, studentClass, password);

        // Utilisation de l'objet `reference` initialisé dans `onCreate`
        reference.child(CIN).setValue(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentRegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentRegisterActivity.this, StudentLoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(StudentRegisterActivity.this, "Erreur lors de l'ajout dans la base de données", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
