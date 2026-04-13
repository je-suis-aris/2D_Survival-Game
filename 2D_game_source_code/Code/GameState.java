package com.example.tema2;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Joueur joueur;
    private CarteJeu carte;

    public GameState(Joueur joueur, CarteJeu carte) {
        this.joueur = joueur;
        this.carte = carte;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public CarteJeu getCarte() {
        return carte;
    }
}
