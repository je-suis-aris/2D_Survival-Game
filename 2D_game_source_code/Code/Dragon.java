package com.example.tema2;
import java.io.Serializable;

public class Dragon extends Ennemi implements Serializable {
    private static final long serialVersionUID = 1L;

    public Dragon(String nom, int attaque, int defense, int sante, int niveau) {
        super(nom, attaque, defense, sante, niveau);
    }

    public Dragon() {
        super("Dragon", 50, 30, 500, 5);
    }

    @Override
    public void interact(Joueur joueur, CarteJeu carte) {
        CombatWindow combatWindow = new CombatWindow(joueur, this);
        combatWindow.show();
    }
}
