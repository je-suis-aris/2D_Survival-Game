package com.example.tema2;
import java.io.Serializable;

public class Troll extends Ennemi implements Serializable {
    private static final long serialVersionUID = 1L;

    public Troll(String nom, int attaque, int defense, int sante, int niveau) {
        super(nom, attaque, defense, sante, niveau);
    }

    public Troll() {
        super("Troll", 10, 5, 100, 1);
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        CombatWindow combatWindow = new CombatWindow(joueur, this);
        combatWindow.show();
    }

}
