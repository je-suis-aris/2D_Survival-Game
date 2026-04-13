package com.example.tema2;
import java.io.Serializable;
import java.util.Map;

public class TourDeGarde extends Batiment implements Serializable {
    private static final long serialVersionUID = 1L;
    private long dernierInteraction;

    public TourDeGarde(Map<String, Integer> coutConstruction, int valeurEffet, double poids, double dureeDeVie) {
        super("Tour de Garde", coutConstruction, TypeEffet.BOOST_DEFENSE, valeurEffet, 100, poids);
        this.dernierInteraction = 0;
    }


    @Override
    public void appliquerEffet(Joueur joueur) {
        System.out.println("La Tour de Garde améliore la défense du joueur.");
        joueur.setDefense(joueur.getDefense() + this.getValeurEffet());
    }


    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        long maintenant = System.currentTimeMillis();


        if (maintenant - dernierInteraction < 30_000) {
            System.out.println("Vous devez attendre avant de pouvoir interagir à nouveau avec la Tour de Garde.");
            return;
        }

        System.out.println("Vous interagissez avec la Tour de Garde. Votre défense est renforcée.");
        appliquerEffet(joueur);

        dernierInteraction = maintenant;
    }

}
