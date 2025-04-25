package com.example.essatconnect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ManageEventsActivity extends AppCompatActivity {

    private EditText eventTitleEditText, eventDescriptionEditText, eventDateEditText;
    private Button addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        // Initialisation des vues
        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        addEventButton = findViewById(R.id.addEventButton);

        // Bouton pour ajouter un événement
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToFirebase();
            }
        });
    }

    private void addEventToFirebase() {
        String title = eventTitleEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Génération d'un ID unique pour l'événement
        String eventId = UUID.randomUUID().toString();

        // Création d'un objet Event
        Event event = new Event(eventId, title, description, date);

        // Référence à la base de données Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");

        // Ajout de l'événement dans Firebase
        databaseReference.child(eventId).setValue(event).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ManageEventsActivity.this, "Événement ajouté avec succès", Toast.LENGTH_SHORT).show();
                // Réinitialiser les champs
                eventTitleEditText.setText("");
                eventDescriptionEditText.setText("");
                eventDateEditText.setText("");
            } else {
                Toast.makeText(ManageEventsActivity.this, "Erreur lors de l'ajout de l'événement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
