package com.example.tema2;
import java.io.Serializable;
import java.util.Map;

public class FontaineDeVie extends Batiment implements Serializable {
    private static final long serialVersionUID = 1L;

    public FontaineDeVie(Map<String, Integer> coutConstruction, int valeurEffet, int valeurMonetaire, double poids) {
        super("Fontaine de Vie", coutConstruction, TypeEffet.SANTE, valeurEffet, valeurMonetaire, poids);
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {

        System.out.println("Vous interagissez avec la Fontaine de Vie. Votre santé est restaurée !");
        appliquerEffet(joueur);
    }
}