package com.example.tema2;
public class Rocher extends ObjetRecuperable {
    public Rocher(int quantite, Qualite qualite, double poids, int dureeDeVie, int valeurMonetaire) {
        super("Rocher", quantite, qualite, poids, dureeDeVie, valeurMonetaire);
    }

    @Override
    public void recuperer(Joueur joueur) {
        if (isCollecte()) return;

        System.out.println("Vous avez récupéré " + quantite + " unités de pierre de qualité " + qualite + ".");
        joueur.collecterRessource("Pierre", quantite, qualite);
        setCollecte(true);
    }

    @Override
    public void utiliser() {
        System.out.println("Vous utilisez la pierre du rocher.");
        quantite--;
        if (quantite <= 0) {
            detruire();
        }
    }

    @Override
    public void detruire() {
        System.out.println("Le rocher est épuisé.");
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        recuperer(joueur);
    }
}
