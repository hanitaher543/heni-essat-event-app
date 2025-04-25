package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText cinEditText, passwordEditText;
    private Button loginButton;
    private TextView registerRedirectText;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        // Initialisation des éléments de l'interface utilisateur
        cinEditText = findViewById(R.id.cinEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerRedirectText = findViewById(R.id.registerRedirectText);
        progressBar = findViewById(R.id.progressBar);

        // Initialisation de Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Logic pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String CIN = cinEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validation des champs
            if (CIN.isEmpty() || password.isEmpty()) {
                Toast.makeText(StudentLoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Affichage du ProgressBar pendant la connexion
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false); // Désactivation du bouton pendant la connexion

            // Vérification de l'utilisateur et connexion
            verifyUserInDatabase(CIN, password);
        });

        // Redirection vers la page d'inscription si l'étudiant n'a pas de compte
        registerRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(StudentLoginActivity.this, StudentRegisterActivity.class);
            startActivity(intent);
        });
    }

    // Vérification de l'utilisateur dans Firebase et connexion
    private void verifyUserInDatabase(String CIN, String password) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students");

        reference.child(CIN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String email = dataSnapshot.child("email").getValue(String.class);
                    if (email != null) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(StudentLoginActivity.this, task -> {
                                    progressBar.setVisibility(View.GONE);
                                    loginButton.setEnabled(true);

                                    if (task.isSuccessful()) {
                                        // Connexion réussie, passez le CIN à l'activité suivante
                                        Toast.makeText(StudentLoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(StudentLoginActivity.this, StudentDashboardActivity.class);
                                        intent.putExtra("CIN", CIN); // Transmettre le CIN
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Erreur inconnue";
                                        Toast.makeText(StudentLoginActivity.this, "Echec de la connexion : " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(StudentLoginActivity.this, "Utilisateur non trouvé dans la base de données", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StudentLoginActivity.this, "Utilisateur non trouvé dans la base de données", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentLoginActivity.this, "Erreur de vérification de l'utilisateur", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
