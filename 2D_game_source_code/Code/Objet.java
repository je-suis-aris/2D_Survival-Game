package com.example.tema2;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public abstract class Objet implements Comparable<Objet>, Serializable {
    protected String nom;
    protected int valeurMonetaire;
    protected double poids;

    public Objet(String nom, int valeurMonetaire, double poids) {
        this.nom = nom;
        this.valeurMonetaire = valeurMonetaire;
        this.poids = poids;
    }

    public String getNom() { return nom; }
    public int getValeurMonetaire() { return valeurMonetaire; }
    public double getPoids() { return poids; }

    @Override
    public int compareTo(Objet o) {
        return Integer.compare(this.valeurMonetaire, o.valeurMonetaire);
    }

    @Override
    public String toString() {
        return "Objet: " + nom + ", Valeur Monétaire: " + valeurMonetaire + ", Poids: " + poids;
    }
}
