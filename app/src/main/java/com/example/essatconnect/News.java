package com.example.essatconnect;

public class News {
    private String id;
    private String title;
    private String description;

    // Constructeur par défaut requis pour Firebase
    public News() {}

    public News(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
