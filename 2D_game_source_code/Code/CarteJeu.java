package com.example.tema2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarteJeu implements Serializable {
    private static final long serialVersionUID = 1L;
    private int taille;
    private Object[][] carte;

    public CarteJeu(int taille) {
        this.taille = taille;
        carte = new Object[taille][taille];
    }

    public int getTaille() {
        return taille;
    }

    public void placerObjetA(int x, int y, Object obj) {
        carte[x][y] = obj;
    }

    public Object getObjetA(int x, int y) {
        return carte[x][y];
    }

    public void supprimerObjetA(int x, int y) {
        carte[x][y] = null;
    }

    public boolean estVideA(int x, int y) {
        return getObjetA(x, y) == null;
    }

    public boolean estEspaceLibreOuJoueur(int x, int y) {
        Object obj = this.getObjetA(x, y);
        System.out.println("Checking position (" + x + ", " + y + "): " + (obj == null ? "Empty" : obj.getClass().getSimpleName()));
        return obj == null || obj instanceof Joueur;
    }

    public void placerRessourcesAleatoires() {
        Random rand = new Random();
        int nombreRessources = (int) (taille * taille * 0.6);

        for (int i = 0; i < nombreRessources; i++) {
            int x = rand.nextInt(taille);
            int y = rand.nextInt(taille);

            if (!estVideA(x, y)) {
                continue;
            }

            ObjetRecuperable ressource;
            int ressourceType = rand.nextInt(3);

            switch (ressourceType) {
                case 0:
                    ressource = new Chene(5 + rand.nextInt(11),
                            Qualite.values()[rand.nextInt(Qualite.values().length)],
                            10.0, 5, 50);
                    break;
                case 1:
                    ressource = new Rocher(3 + rand.nextInt(8),
                            Qualite.values()[rand.nextInt(Qualite.values().length)],
                            15.0, 10, 75);
                    break;
                case 2:
                    ressource = new Cereal(5 + rand.nextInt(11),
                            Qualite.values()[rand.nextInt(Qualite.values().length)],
                            5.0, 3, 30);
                    break;
                default:
                    continue;
            }

            placerObjetA(x, y, ressource);
        }

        System.out.println("Resources placed on the map.");
    }

    public List<Batiment> getBuildingsNear(int x, int y) {
        List<Batiment> nearbyBuildings = new ArrayList<>();

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && nx < taille && ny >= 0 && ny < taille) {
                Object obj = getObjetA(nx, ny);
                if (obj instanceof Batiment) {
                    nearbyBuildings.add((Batiment) obj);
                }
            }
        }

        return nearbyBuildings;
    }

    public void logMapState() {
        System.out.println("Current map state:");
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print((carte[i][j] == null ? "." : "X") + " ");
            }
            System.out.println();
        }
    }
}