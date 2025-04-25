package com.example.essatconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView studentFullNameTextView;
    private Button viewNewsButton, viewEventsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // Initialize views
        studentFullNameTextView = findViewById(R.id.studentFullName);
        viewNewsButton = findViewById(R.id.viewNewsButton);
        viewEventsButton = findViewById(R.id.viewEventsButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Get the CIN from the intent
        String CIN = getIntent().getStringExtra("CIN");

        if (CIN != null) {
            // Display the CIN directly
            studentFullNameTextView.setText("Bienvenue, CIN: " + CIN);
        } else {
            studentFullNameTextView.setText("Erreur : CIN non fourni");
        }

        // Button actions
        viewNewsButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewNewsActivity.class);
            startActivity(intent);
        });

        viewEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewEventsActivity.class);
            startActivity(intent);
        });


        // Logout button action
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(StudentDashboardActivity.this, StudentLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
