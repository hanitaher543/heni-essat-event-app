package com.example.essatconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewEventsActivity extends AppCompatActivity {

    private LinearLayout eventsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        // Initialisation du conteneur pour afficher les événements
        eventsContainer = findViewById(R.id.eventsContainer);

        // Charger les événements depuis Firebase
        loadEventsFromFirebase();
    }

    private void loadEventsFromFirebase() {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");

        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventsContainer.removeAllViews(); // Effacer les événements existants avant d'en ajouter de nouveaux
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        displayEvent(event);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewEventsActivity.this, "Erreur lors du chargement des événements", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayEvent(Event event) {
        View eventView = getLayoutInflater().inflate(R.layout.item_event, null);

        TextView titleTextView = eventView.findViewById(R.id.eventTitleTextView);
        TextView descriptionTextView = eventView.findViewById(R.id.eventDescriptionTextView);
        TextView dateTextView = eventView.findViewById(R.id.eventDateTextView);
        Button registerButton = eventView.findViewById(R.id.registerButton);

        titleTextView.setText(event.getTitle());
        descriptionTextView.setText(event.getDescription());
        dateTextView.setText("Date : " + event.getDate());

        registerButton.setOnClickListener(v -> {
            // Logique d'inscription (peut inclure un ajout à Firebase ou une notification)
            Toast.makeText(ViewEventsActivity.this, "Inscrit à l'événement : " + event.getTitle(), Toast.LENGTH_SHORT).show();
        });

        eventsContainer.addView(eventView);
    }
}
