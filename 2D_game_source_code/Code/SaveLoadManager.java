package com.example.tema2;

import java.io.*;


public class SaveLoadManager {

    private static final String SAVE_FILE = "D:\\POO\\Grand Devoir II\\Grand_devoir_2-FINAL\\src\\main\\java\\com\\example\\tema2\\salvare.txt";


    public static void saveGame(GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
            System.out.println("[SaveLoadManager] Sauvegarde réussie dans " + SAVE_FILE);
        } catch (IOException e) {
            System.err.println("[SaveLoadManager] Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }


    public static GameState loadGame() {
        File file = new File(SAVE_FILE);

        if (!file.exists()) {
            System.err.println("[SaveLoadManager] Le fichier de sauvegarde " + SAVE_FILE + " est introuvable.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameState loadedGameState = (GameState) ois.readObject();
            System.out.println("[SaveLoadManager] Chargement réussi depuis " + SAVE_FILE);
            return loadedGameState;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[SaveLoadManager] Erreur lors du chargement : " + e.getMessage());
            return null;
        }
    }
}
