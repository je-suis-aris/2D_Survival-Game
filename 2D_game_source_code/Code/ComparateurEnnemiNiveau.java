package com.example.tema2;
import java.util.Comparator;

public class ComparateurEnnemiNiveau implements Comparator<Ennemi> {
    @Override
    public int compare(Ennemi e1, Ennemi e2) {
        return Integer.compare(e1.getNiveau(), e2.getNiveau());
    }
}
