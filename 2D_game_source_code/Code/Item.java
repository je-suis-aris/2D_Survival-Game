package com.example.tema2;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Item extends Objet implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bonusSante;
    private int bonusAttaque;
    private int bonusDefense;
    private Map<String, Integer> coutFabrication;

    public Item(String nom, int bonusAttaque, int bonusDefense, int bonusSante, int valeurMonetaire, double poids) {
        super(nom, valeurMonetaire, poids);
        this.bonusAttaque = bonusAttaque;
        this.bonusDefense = bonusDefense;
        this.bonusSante = bonusSante;
        this.coutFabrication = new HashMap<>();
    }

    public void setCoutFabrication(Map<String, Integer> cout) {
        this.coutFabrication = cout;
    }

    public Map<String, Integer> getCoutFabrication() {
        return coutFabrication;
    }

    public int getBonusSante() { return bonusSante; }

    public int getBonusAttaque() { return bonusAttaque; }

    public int getBonusDefense() { return bonusDefense; }

    @Override
    public String toString() {
        return super.toString() + ", Bonus Attaque: " + bonusAttaque + ", Bonus Défense: " + bonusDefense +
                ", Bonus Santé: " + bonusSante;
    }
    public void ameliorer() {
        this.bonusAttaque += 2;
        this.bonusDefense += 1;
        this.bonusSante += 5;
        System.out.println("Les bonus de " + this.nom + " ont été améliorés !");
    }
}
