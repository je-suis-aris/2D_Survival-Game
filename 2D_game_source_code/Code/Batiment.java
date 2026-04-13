package com.example.tema2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Batiment extends Objet implements Interactable, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> coutConstruction;
    private TypeEffet typeEffet;
    private int valeurEffet;

    public Batiment(String nom, Map<String, Integer> coutConstruction, TypeEffet typeEffet, int valeurEffet, int valeurMonetaire, double poids) {
        super(nom, valeurMonetaire, poids);
        this.coutConstruction = coutConstruction;
        this.typeEffet = typeEffet;
        this.valeurEffet = valeurEffet;
    }

    public void appliquerEffet(Joueur joueur) {
        switch (typeEffet) {
            case SANTE:
                joueur.setSante(joueur.getSanteMax());
                System.out.println("Votre santé a été restaurée au maximum !");
                break;
            case BOOST_ATTAQUE:
                int nouvelleAttaque = joueur.getAttaque() + (valeurEffet * joueur.getAttaque() / 100);
                joueur.setAttaque(nouvelleAttaque);
                System.out.println("Votre attaque a augmenté de " + valeurEffet + "% !");
                break;
            case BOOST_DEFENSE:
                int nouvelleDefense = joueur.getDefense() + (valeurEffet * joueur.getDefense() / 100);
                joueur.setDefense(nouvelleDefense);
                System.out.println("Votre défense a augmenté de " + valeurEffet + "% !");
                break;
            case CUSTOM:
                System.out.println("Effet personnalisé appliqué !");
                break;
            default:
                System.out.println("Effet inconnu.");
        }
    }

    public List<int[]> getEffectArea(int x, int y) {
        List<int[]> positions = new ArrayList<>();
        positions.add(new int[]{x, y - 1});
        positions.add(new int[]{x, y + 1});
        positions.add(new int[]{x - 1, y});
        positions.add(new int[]{x + 1, y});
        return positions;
    }

    public Map<String, Integer> getCoutConstruction() {
        return coutConstruction;
    }

    public String getTypeEffet() {
        return typeEffet.name();
    }

    public int getValeurEffet() {
        return valeurEffet;
    }

    @Override
    public String toString() {
        return super.toString() + ", Effet: " + typeEffet + " (" + valeurEffet + ")";
    }
}