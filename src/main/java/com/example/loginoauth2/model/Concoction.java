package com.example.loginoauth2.model;

import java.util.List;

public class Concoction {

    private Long id;
    private String nom;
    private String description;
    private String difficulte;          // "Facile", "Moyen", "Difficile"
    private List<String> ingredients;

    public Concoction(Long id, String nom, String description,
                      String difficulte, List<String> ingredients) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.difficulte = difficulte;
        this.ingredients = ingredients;
    }

    // Getters : indispensables pour que Spring puisse lire les valeurs
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getDifficulte() { return difficulte; }
    public List<String> getIngredients() { return ingredients; }
}