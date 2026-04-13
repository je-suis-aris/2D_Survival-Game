package com.example.tema2;
import java.io.Serializable;

public class Orc extends Ennemi implements Serializable {
    private static final long serialVersionUID = 1L;

    public Orc(String nom, int attaque, int defense, int sante, int niveau) {
        super(nom, attaque, defense, sante, niveau);
    }

    public Orc() {
        super("Orc", 15, 10, 120, 1);
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        CombatWindow combatWindow = new CombatWindow(joueur, this);
        combatWindow.show();
    }
}
