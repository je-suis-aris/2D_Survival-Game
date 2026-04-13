package com.example.tema2;
public class Cereal extends ObjetRecuperable {
    public Cereal(int quantite, Qualite qualite, double poids, int dureeDeVie, int valeurMonetaire) {
        super("Céréales", quantite, qualite, poids, dureeDeVie, valeurMonetaire);
    }

    @Override
    public void recuperer(Joueur joueur) {
        if (isCollecte()) return;

        System.out.println("Vous avez récupéré " + quantite + " unités de nourriture de qualité " + qualite + ".");

        joueur.collecterRessource("Nourriture", quantite, qualite);

        setCollecte(true);
    }

    @Override
    public void utiliser() {
        System.out.println("Vous consommez la nourriture des céréales.");
        quantite--;
        if (quantite <= 0) {
            detruire();
        }
    }

    @Override
    public void detruire() {
        System.out.println("Les céréales sont épuisées.");
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        recuperer(joueur);
    }
}
