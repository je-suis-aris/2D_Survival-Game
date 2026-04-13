package com.example.tema2;

public class PointManager {
    private int battlePoints;
    private int resourcePoints;

    public void addBattlePoints(int points) {
        battlePoints += points;
    }

    public void addResourcePoints(int points) {
        resourcePoints += points;
    }

    public int getBattlePoints() {
        return battlePoints;
    }

    public int getResourcePoints() {
        return resourcePoints;
    }

    public int getTotalPoints() {
        return battlePoints + resourcePoints;
    }
}

