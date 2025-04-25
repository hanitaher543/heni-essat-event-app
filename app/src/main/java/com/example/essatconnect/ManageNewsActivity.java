package com.example.essatconnect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageNewsActivity extends AppCompatActivity {

    private EditText newsTitleEditText, newsDescriptionEditText;
    private Button addNewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);

        // Initialisation des vues
        newsTitleEditText = findViewById(R.id.newsTitleEditText);
        newsDescriptionEditText = findViewById(R.id.newsDescriptionEditText);
        addNewsButton = findViewById(R.id.addNewsButton);

        // Référence de la base de données Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news");

        // Logique du bouton "Ajouter l'actualité"
        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newsTitleEditText.getText().toString().trim();
                String description = newsDescriptionEditText.getText().toString().trim();

                // Vérification des champs
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                    Toast.makeText(ManageNewsActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    // Générer un identifiant unique pour l'actualité
                    String newsId = databaseReference.push().getKey();

                    if (newsId != null) {
                        // Créer un objet News
                        News news = new News(newsId, title, description);

                        // Insérer l'actualité dans Firebase
                        databaseReference.child(newsId).setValue(news)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ManageNewsActivity.this, "Actualité ajoutée avec succès", Toast.LENGTH_SHORT).show();
                                    newsTitleEditText.setText("");
                                    newsDescriptionEditText.setText("");
                                })
                                .addOnFailureListener(e -> Toast.makeText(ManageNewsActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }
}
