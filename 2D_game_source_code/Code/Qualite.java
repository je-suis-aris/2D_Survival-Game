package com.example.tema2;
public enum Qualite {
    COMMUNE(1.0),
    RARE(1.5),
    EPIQUE(2.0),
    LEGENDAIRE(3.0);

    private double multiplicateur;

    private Qualite(double multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public double getMultiplicateur() {
        return multiplicateur;
    }
}


