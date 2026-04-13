package com.example.tema2;
import java.io.Serializable;
import java.util.Random;

public abstract class Ennemi extends Personnage implements Interactable, Serializable {
    private static final long serialVersionUID = 1L;
    private int niveau;
    private int experienceDonnee;
    private int pointsValue;
    public Ennemi(String nom, int attaque, int defense, int sante, int niveau) {
        super(nom, attaque, defense, sante);
        this.niveau = niveau;
        this.experienceDonnee = niveau * 50;
        this.pointsValue = pointsValue;
    }

    public Ennemi() {
        super();
        this.niveau = 1;
        this.experienceDonnee = 50;}

    @Override
    public int attaquer(Personnage cible) {
        int attaqueTotale = this.attaque;
        for (Item item : equipements) {
            attaqueTotale += item.getBonusAttaque();
        }
        System.out.println(this.nom + " inflige " + attaqueTotale + " points de dégâts à " + cible.getNom() + ".");
        cible.subirDegats(attaqueTotale);
        return attaqueTotale;
    }

    @Override

    public void subirDegats(int degats) {
        int defenseTotale = this.defense;
        for (Item item : equipements) {
            defenseTotale += item.getBonusDefense();
        }

        int degatsReels = Math.max(0, degats - defenseTotale);
        this.sante -= degatsReels;

        System.out.println(this.nom + " subit " + degatsReels + " points de dégâts. Santé restante: " + Math.max(0, this.sante));
        if (this.sante <= 0) {
            mourir();
        }
    }


    @Override
    public void mourir() {
        this.statut = Statut.MORT;
        System.out.println(this.nom + " a été vaincu.");
    }


    public Item laisserTomberObjet() {
        Random rand = new Random();
        String[] nomsObjets = {"Épée", "Armure", "Casque"};
        String nomObjet = nomsObjets[rand.nextInt(nomsObjets.length)];
        int bonusAttaque = rand.nextInt(5) + 1;
        int bonusDefense = rand.nextInt(5) + 1;
        int bonusSante = rand.nextInt(10) + 5;
        Item objetLaisse = new Item(nomObjet, bonusAttaque, bonusDefense, bonusSante, 100, 5.0);
        System.out.println(this.nom + " a laissé tomber un objet : " + objetLaisse.getNom());
        return objetLaisse;
    }

    public int getNiveau() { return niveau; }

    public int getExperienceDonnee() { return experienceDonnee; }
    @Override
    public String toString() {
        return "Ennemi{" +
                "Nom='" + nom + '\'' +
                ", Attaque=" + attaque +
                ", Défense=" + defense +
                ", Santé=" + sante +
                ", Niveau=" + niveau +
                ", Expérience Donnée=" + experienceDonnee +
                '}';
    }

    public int getPointsValue() {
        return pointsValue;
    }

}
