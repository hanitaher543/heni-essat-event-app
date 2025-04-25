package com.example.essatconnect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewNewsActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        // Initialisation des vues
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);

        // Charger les actualités depuis Firebase
        loadNewsFromFirebase();
    }

    private void loadNewsFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("news");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    News news = dataSnapshot.getValue(News.class);
                    if (news != null) {
                        newsList.add(news);
                    }
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewNewsActivity.this, "Erreur : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
