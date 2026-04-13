package com.example.tema2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HelloController {
    private Joueur joueur;
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    private CarteJeu carte;
    private Map<String, Integer> ressources;
    private int tailleCarte = 10;
    private Label battlePointsLabel;
    private Label resourcePointsLabel;
    private Label totalPointsLabel;
    private PointManager pointManager;
    @FXML
    private Label welcomeText;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label lblWood, lblStone, lblFood;
    @FXML
    private VBox menuPane;
    @FXML
    private VBox gamePane;
    @FXML
    private Button btnShowInventory, btnCraftItem;




    private MediaPlayer mediaPlayer;
    private String currentBackground = "Default";
    private boolean isMusicPlaying = false;


    @FXML
    protected void onNewGame() {
        List<Item> items;
        try {
            items = GameLoader.loadItems("D:\\POO\\Grand Devoir II\\Grand_devoir_2-FINAL\\src\\main\\java\\com\\example\\tema2\\items.txt");
            System.out.println("Items loaded successfully!");
        } catch (IOException e) {
            items = new ArrayList<>();
            System.out.println("Error loading items: " + e.getMessage());
        }

        this.setCraftableItems(items);

        carte = new CarteJeu(tailleCarte);

        this.joueur = new Joueur("Arisum", 10, 5, 100);


        joueur.getPosition().put("X", 0);
        joueur.getPosition().put("Y", 0);

        carte.placerObjetA(0, 0, joueur);

        carte.placerRessourcesAleatoires();
        placerEnnemisAleatoires();

        System.out.println("Player placed at (0, 0). Resources and enemies placed.");

        openMapWindow();
    }


    private void openMapWindow() {
        if (mapStage == null) {
            mapStage = new Stage();
            mapStage.setTitle("Carte du jeu");

            BorderPane layout = new BorderPane();

            mapGrid = new GridPane();
            mapGrid.setHgap(5);
            mapGrid.setVgap(5);
            mapGrid.setAlignment(Pos.CENTER);
            layout.setCenter(mapGrid);

            lblWood = new Label("Bois: " + joueur.getRessource("Bois"));
            lblStone = new Label("Pierre: " + joueur.getRessource("Pierre"));
            lblFood = new Label("Nourriture: " + joueur.getRessource("Nourriture"));
            lblWood.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            lblStone.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            lblFood.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            ProgressBar healthBar = new ProgressBar((double) joueur.getSante() / joueur.getSanteMax());
            healthBar.setPrefWidth(200);
            healthBar.setStyle("-fx-accent: green;");

            Label healthLabel = new Label(joueur.getSante() + " / " + joueur.getSanteMax());
            healthLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            VBox healthBox = new VBox(5);
            healthBox.setAlignment(Pos.CENTER_LEFT);
            healthBox.getChildren().addAll(new Label("Santé:"), healthBar, healthLabel);

            HBox resourceBox = new HBox(30);
            resourceBox.setPadding(new Insets(10));
            resourceBox.setAlignment(Pos.CENTER);
            resourceBox.getChildren().addAll(lblWood, lblStone, lblFood, healthBox);
            layout.setTop(resourceBox);

            VBox rightBox = new VBox(10);
            rightBox.setPadding(new Insets(10));
            rightBox.setAlignment(Pos.CENTER);

            Button btnCraft = new Button("Fabriquer");
            btnCraft.setDisable(true);
            btnCraft.setOnAction(e -> openCraftingMenu());

            Button btnBuild = new Button("Construire");
            btnBuild.setDisable(true);
            btnBuild.setOnAction(e -> openBuildingMenu());

            Button btnInventory = new Button("Inventaire");
            btnInventory.setOnAction(e -> showInventory());

            Button btnStats = new Button("Statistiques");
            btnStats.setOnAction(e -> openStatisticsWindow());

            Button btnSave = new Button("Sauvegarder");
            btnSave.setOnAction(e -> onSave());

            rightBox.getChildren().addAll(btnStats, btnCraft, btnBuild, btnInventory, btnSave);
            layout.setRight(rightBox);

            Scene mapScene = new Scene(layout, 1400, 600);

            mapScene.setOnKeyPressed(event -> {
                onKeyPressed(event);
                updateButtonStates(btnCraft, btnBuild);
            });

            mapStage.setScene(mapScene);

            joueur.addHealthListener((oldValue, newValue) -> {
                Platform.runLater(() -> {
                    healthBar.setProgress((double) newValue / joueur.getSanteMax());
                    healthLabel.setText(newValue + " / " + joueur.getSanteMax());
                    if (newValue > joueur.getSanteMax() / 2) {
                        healthBar.setStyle("-fx-accent: green;");
                    } else if (newValue > joueur.getSanteMax() / 4) {
                        healthBar.setStyle("-fx-accent: orange;");
                    } else {
                        healthBar.setStyle("-fx-accent: red;");
                    }
                });
            });
        }

        drawMap();
        mapStage.show();
    }


    @FXML
    protected void onResume() {
        GameState loadedState = SaveLoadManager.loadGame();
        if (loadedState != null) {
            this.joueur = loadedState.getJoueur();
            this.carte = loadedState.getCarte();

            joueur.addHealthListener((oldValue, newValue) -> {
                Platform.runLater(() -> {
                });
            });

            openMapWindow();
        } else {
            System.out.println("No save found or error loading the game.");
        }
    }

    private void onSave() {
        System.out.println("Saving the game to salvare.txt...");

        GameState gameState = new GameState(this.joueur, this.carte);

        SaveLoadManager.saveGame(gameState);

        System.out.println("Game saved successfully!");
    }


    private Stage statsStage;
    private Label lblNom, lblNiveau, lblExperience, lblSante, lblAttaque, lblDefense, lblPoidsTransportable, lblPoidsActuel;

    private void openStatisticsWindow() {
        if (statsStage == null) {
            statsStage = new Stage();
            statsStage.setTitle("Statistiques du Joueur");

            VBox statsLayout = new VBox(10);
            statsLayout.setPadding(new Insets(10));
            statsLayout.setAlignment(Pos.CENTER_LEFT);

            lblNom = new Label("Nom : " + joueur.getNom());
            lblNiveau = new Label("Niveau : " + joueur.getNiveau());
            lblExperience = new Label("Expérience : " + joueur.getExperience() + " / " + (joueur.getNiveau() * 100));
            lblSante = new Label("Santé : " + joueur.getSante() + " / " + joueur.getSanteMax());
            lblAttaque = new Label("Attaque : " + joueur.getAttaque());
            lblDefense = new Label("Défense : " + joueur.getDefense());
            lblPoidsTransportable = new Label("Poids transportable : " + joueur.getPoidsTransportable() + " kg");
            lblPoidsActuel = new Label("Poids actuel : " + joueur.getPoidsActuel() + " kg");

            statsLayout.getChildren().addAll(
                    lblNom, lblNiveau, lblExperience, lblSante, lblAttaque, lblDefense, lblPoidsTransportable, lblPoidsActuel
            );

            Scene statsScene = new Scene(statsLayout, 300, 300);
            statsStage.setScene(statsScene);
        }

        updateStatisticsLabels();

        statsStage.show();
    }

    private void updateStatisticsLabels() {
        Platform.runLater(() -> {
            lblNom.setText("Nom : " + joueur.getNom());
            lblNiveau.setText("Niveau : " + joueur.getNiveau());
            lblExperience.setText("Expérience : " + joueur.getExperience() + " / " + (joueur.getNiveau() * 100));
            lblSante.setText("Santé : " + joueur.getSante() + " / " + joueur.getSanteMax());
            lblAttaque.setText("Attaque : " + joueur.getAttaque());
            lblDefense.setText("Défense : " + joueur.getDefense());
            lblPoidsTransportable.setText("Poids transportable : " + joueur.getPoidsTransportable() + " kg");
            lblPoidsActuel.setText("Poids actuel : " + joueur.getPoidsActuel() + " kg");
        });
    }

    private List<Batiment> getBuildableBuildings() {
        Map<String, Integer> fontaineCost = Map.of("Bois", 10, "Pierre", 5);
        Map<String, Integer> forgeCost = Map.of("Bois", 20, "Pierre", 10);
        Map<String, Integer> tourDeGardeCost = Map.of("Bois", 15, "Pierre", 20);

        List<Batiment> buildings = new ArrayList<>();
        buildings.add(new FontaineDeVie(fontaineCost, 0, 500, 100.0));
        buildings.add(new Forge(forgeCost, 5, 500, 150.0));
        buildings.add(new TourDeGarde(tourDeGardeCost, 10, 300, 100.0));

        return buildings;
    }

    private Map<String, Integer> checkMissingResources(Batiment building) {
        Map<String, Integer> missingResources = new HashMap<>();

        for (Map.Entry<String, Integer> entry : building.getCoutConstruction().entrySet()) {
            String resource = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmount = joueur.getRessource(resource);

            if (availableAmount < requiredAmount) {
                missingResources.put(resource, requiredAmount - availableAmount);
            }
        }

        return missingResources;
    }

    private void deductResources(Batiment building) {
        for (Map.Entry<String, Integer> entry : building.getCoutConstruction().entrySet()) {
            String resource = entry.getKey();
            int amount = entry.getValue();
            joueur.updateRessource(resource, joueur.getRessource(resource) - amount);
        }
    }

    private void updateMapAfterBuilding(Batiment building, int x, int y) {
        String imagePath = determineImagePath(building);
        ImageView cellImage = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        playBuildingSound();
        double screenWidth = 800;
        double screenHeight = 600;
        double cellSize = (Math.min(screenWidth, screenHeight) / carte.getTaille())*0.8;

        cellImage.setFitWidth(cellSize);
        cellImage.setFitHeight(cellSize);
        cellImage.setPreserveRatio(true);
        cellImage.setSmooth(true);

        GridPane.setMargin(cellImage, Insets.EMPTY);
        GridPane.setHalignment(cellImage, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(cellImage, javafx.geometry.VPos.CENTER);

        mapGrid.add(cellImage, y, x);
        System.out.println("Building constructed: " + building.getNom());
    }

    private void constructBuilding(Batiment batiment) {
        try {
            int x = joueur.getPosition().get("X");
            int y = joueur.getPosition().get("Y");

            int[] newCoordinates = findAdjacentEmptyCell(x, y);

            if (newCoordinates != null) {
                int newX = newCoordinates[0];
                int newY = newCoordinates[1];

                joueur.construire(batiment, newX, newY, carte);
                updateMapAfterBuilding(batiment, newX, newY);
                playBuildingSound();
                updateResourcesDisplay();
            } else {
                Alert noSpaceAlert = new Alert(Alert.AlertType.WARNING,
                        "No adjacent empty space available to construct!", ButtonType.OK);
                noSpaceAlert.showAndWait();
            }
        } catch (RessourcesInsuffisantesException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            errorAlert.showAndWait();
        }
    }
    private void playBuildingSound() {
        System.out.println("playBuildingSound called");
        try {
            String soundPath = getClass().getResource("/sounds/building.mp3").toExternalForm();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(soundPath));
            mediaPlayer.play();
        } catch (Exception ex) {
            System.err.println("Error playing building sound: " + ex.getMessage());
        }
    }



    private int[] findAdjacentEmptyCell(int x, int y) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < carte.getTaille() && newY >= 0 && newY < carte.getTaille()) {
                if (carte.estVideA(newX, newY)) {
                    return new int[]{newX, newY};
                }
            }
        }
        return null;
    }


    private void openBuildingMenu() {
        List<Batiment> buildableBuildings = getBuildableBuildings();

        if (buildableBuildings.isEmpty()) {
            Alert noBuildings = new Alert(Alert.AlertType.WARNING, "No buildings available to construct.", ButtonType.OK);
            noBuildings.showAndWait();
            return;
        }

        StringBuilder content = new StringBuilder("Bâtiments disponibles:\n");
        for (Batiment building : buildableBuildings) {
            content.append("- ").append(building.getNom()).append(" (Coûts: ");
            building.getCoutConstruction().forEach((resource, quantity) ->
                    content.append(resource).append(": ").append(quantity).append(", ")
            );
            content.setLength(content.length() - 2);
            content.append(")\n");
        }

        ChoiceDialog<Batiment> dialog = new ChoiceDialog<>(buildableBuildings.get(0), buildableBuildings);
        dialog.setTitle("Construire");
        dialog.setHeaderText("Choisir un bâtiment à construire");
        dialog.setContentText(content.toString());

        dialog.showAndWait().ifPresent(selectedBuilding -> {
            try {
                Map<String, Integer> missingResources = checkMissingResources(selectedBuilding);

                if (missingResources.isEmpty()) {
                    int x = joueur.getPosition().get("X");
                    int y = joueur.getPosition().get("Y");

                    int[] newCoordinates = findAdjacentEmptyCell(x, y);

                    if (newCoordinates != null) {
                        int newX = newCoordinates[0];
                        int newY = newCoordinates[1];

                        joueur.construire(selectedBuilding, newX, newY, carte);

                        updateMapAfterBuilding(selectedBuilding, newX, newY);
                        updateResourcesDisplay();

                        Alert success = new Alert(Alert.AlertType.INFORMATION, "Bâtiment construit: " + selectedBuilding.getNom(), ButtonType.OK);
                        success.showAndWait();
                    } else {
                        Alert noSpaceAlert = new Alert(Alert.AlertType.WARNING,
                                "Aucun espace vide adjacent n'est disponible pour construire le bâtiment!", ButtonType.OK);
                        noSpaceAlert.showAndWait();
                    }
                } else {
                    StringBuilder missingMessage = new StringBuilder("Pas assez de ressources pour construire " + selectedBuilding.getNom() + ":\n");
                    for (Map.Entry<String, Integer> entry : missingResources.entrySet()) {
                        missingMessage.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" encore nécessaire\n");
                    }
                    Alert missingAlert = new Alert(Alert.AlertType.WARNING, missingMessage.toString(), ButtonType.OK);
                    missingAlert.showAndWait();
                }
            } catch (RessourcesInsuffisantesException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
                errorAlert.setHeaderText("An error occurred");
                errorAlert.showAndWait();
            }
        });
    }

    private void updateButtonStates(Button btnCraft, Button btnBuild) {
        int x = joueur.getPosition().get("X");
        int y = joueur.getPosition().get("Y");

        boolean isFree = carte.estEspaceLibreOuJoueur(x, y);

        btnCraft.setDisable(!isFree);
        btnBuild.setDisable(!isFree);

        System.out.println("Button states updated. Craft: " + !btnCraft.isDisable() + ", Build: " + !btnBuild.isDisable());
    }


    private List<Item> craftableItems;

    public void setCraftableItems(List<Item> items) {
        this.craftableItems = items;
    }



    @FXML
    private void showInventory() {
        String inventoryText = joueur.getInventaireString();
        Alert inventoryDialog = new Alert(Alert.AlertType.INFORMATION);
        inventoryDialog.setTitle("Inventaire");
        inventoryDialog.setHeaderText("Vos objets:");
        inventoryDialog.setContentText(inventoryText);

        inventoryDialog.showAndWait();
    }


    private void updateCraftingButtonState(Button btnCraft) {
        int x = joueur.getPosition().get("X");
        int y = joueur.getPosition().get("Y");

        if (carte.estEspaceLibreOuJoueur(x, y)) {
            btnCraft.setDisable(false);
            System.out.println("Crafting button enabled.");
        } else {
            btnCraft.setDisable(true);
            System.out.println("Crafting button disabled.");
        }
    }


    private void openCraftingMenu() {
        List<Item> craftableItems = getCraftableItems();

        if (craftableItems.isEmpty()) {
            Alert noItems = new Alert(Alert.AlertType.WARNING, "No items available for crafting.", ButtonType.OK);
            noItems.showAndWait();
            return;
        }

        StringBuilder content = new StringBuilder("Objets disponibles:\n");
        for (Item item : craftableItems) {
            content.append("- ").append(item.getNom()).append(" (Coûts: ");
            item.getCoutFabrication().forEach((resource, quantity) ->
                    content.append(resource).append(": ").append(quantity).append(", ")
            );
            content.setLength(content.length() - 2);
            content.append(")\n");
        }

        ChoiceDialog<Item> dialog = new ChoiceDialog<>(craftableItems.get(0), craftableItems);
        dialog.setTitle("Fabriquer ");
        dialog.setHeaderText("Choisir un objet à fabriquer:");
        dialog.setContentText(content.toString());

        dialog.showAndWait().ifPresent(selectedItem -> {
            try {
                Map<String, Integer> missingResources = checkResourceRequirements(selectedItem);

                if (missingResources.isEmpty()) {
                    joueur.fabriquerObjet(selectedItem);
                    playCraftingSound(); // Play crafting sound
                    updateResourcesDisplay();
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Fabriqué: " + selectedItem.getNom(), ButtonType.OK);
                    success.showAndWait();
                } else {
                    StringBuilder missingMessage = new StringBuilder("Pas assez de ressources pour fabriquer " + selectedItem.getNom() + ":\n");
                    for (Map.Entry<String, Integer> entry : missingResources.entrySet()) {
                        missingMessage.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" encore nécessaire\n");
                    }
                    Alert missingAlert = new Alert(Alert.AlertType.WARNING, missingMessage.toString(), ButtonType.OK);
                    missingAlert.showAndWait();
                }
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage(), ButtonType.OK);
                error.showAndWait();
            }
        });
    }
    private void playCraftingSound() {
        try {
            String soundPath = getClass().getResource("/sounds/crafting.mp3").toExternalForm();
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(soundPath));
            mediaPlayer.play();
        } catch (Exception ex) {
            System.err.println("Error playing crafting sound: " + ex.getMessage());
        }
    }


    private Map<String, Integer> checkResourceRequirements(Item item) {
        Map<String, Integer> missingResources = new HashMap<>();

        for (Map.Entry<String, Integer> entry : item.getCoutFabrication().entrySet()) {
            String resource = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmount = joueur.getRessource(resource);

            if (availableAmount < requiredAmount) {
                missingResources.put(resource, requiredAmount - availableAmount);
            }
        }

        return missingResources;
    }


    private List<Item> loadedItems = new ArrayList<>();

    public void setLoadedItems(List<Item> items) {
        this.loadedItems = items;
    }

    private List<Item> getCraftableItems() {
        return craftableItems != null ? craftableItems : new ArrayList<>();
    }

    @FXML
    private void movePlayer(String direction, Label lblWood, Label lblStone, Label lblFood) {
        try {
            int oldX = joueur.getPosition().get("X");
            int oldY = joueur.getPosition().get("Y");

            joueur.deplacer(direction.charAt(0), carte);
            int newX = joueur.getPosition().get("X");
            int newY = joueur.getPosition().get("Y");

            Object objAtNewPosition = carte.getObjetA(newX, newY);
            if (objAtNewPosition instanceof Batiment) {
                System.out.println("You cannot overwrite a building.");
                joueur.getPosition().put("X", oldX);
                joueur.getPosition().put("Y", oldY);
                return;
            }

            carte.supprimerObjetA(oldX, oldY);
            carte.placerObjetA(newX, newY, joueur);

            drawMap();

            lblWood.setText("Bois: " + joueur.getRessource("Bois"));
            lblStone.setText("Pierre: " + joueur.getRessource("Pierre"));
            lblFood.setText("Nourriture: " + joueur.getRessource("Nourriture"));

            System.out.println("Player moved to (" + newX + ", " + newY + ")");
        } catch (HorsDeLaCarteException e) {
            System.out.println("You can't move outside the map!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public void placerEnnemisAleatoires() {
        int numberOfEachType = 3;
        Random random = new Random();

        for (int i = 0; i < numberOfEachType; i++) {
            placeMonsterOnMap(new Dragon(), random);
        }

        for (int i = 0; i < numberOfEachType; i++) {
            placeMonsterOnMap(new Troll(), random);
        }

        for (int i = 0; i < numberOfEachType; i++) {
            placeMonsterOnMap(new Orc(), random);
        }

        for (int i = 0; i < numberOfEachType; i++) {
            placeMonsterOnMap(new Goblin(), random);
        }

    }

    private void placeMonsterOnMap(Ennemi monster, Random random) {
        int x, y;

        do {
            x = random.nextInt(tailleCarte);
            y = random.nextInt(tailleCarte);
        } while (!carte.estVideA(x, y));

        carte.placerObjetA(x, y, monster);
    }

    @FXML
    private GridPane mapGrid;
    private Stage mapStage;

    private void drawMap() {
        if (mapGrid == null) return;

        mapGrid.getChildren().clear();

        double screenWidth = 800;
        double screenHeight = 700;
        double cellSize = (Math.min(screenWidth, screenHeight) / carte.getTaille())*0.8;

        for (int x = 0; x < carte.getTaille(); x++) {
            for (int y = 0; y < carte.getTaille(); y++) {
                Object obj = carte.getObjetA(x, y);
                String imagePath = determineImagePath(obj);

                ImageView cellImage = new ImageView();
                if (imagePath != null) {
                    cellImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
                } else {
                    cellImage.setImage(new Image(getClass().getResource("/images/empty.jpg").toExternalForm()));
                }

                cellImage.setFitWidth(cellSize);
                cellImage.setFitHeight(cellSize);
                cellImage.setPreserveRatio(true);
                cellImage.setSmooth(true);

                GridPane.setMargin(cellImage, Insets.EMPTY);
                GridPane.setHalignment(cellImage, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(cellImage, javafx.geometry.VPos.CENTER);

                mapGrid.add(cellImage, y, x);
            }
        }

        System.out.println("Map redrawn with consistent cell sizes.");
    }

    private String determineImagePath(Object obj) {
        if (obj instanceof Joueur) {
            return "/images/player.jpg";
        } else if (obj instanceof Chene) {
            return "/images/tree.jpg";
        } else if (obj instanceof Rocher) {
            return "/images/rock.jpg";
        } else if (obj instanceof Cereal) {
            return "/images/grain.jpg";
        } else if (obj instanceof Dragon) {
            return "/images/dragon.jpg";
        } else if (obj instanceof Troll) {
            return "/images/troll.JPG";
        } else if (obj instanceof Orc) {
            return "/images/orc.JPG";
        } else if (obj instanceof Goblin) {
            return "/images/goblin.JPG";
        } else if (obj instanceof FontaineDeVie) {
            return "/images/fountain.JPG";
        } else if (obj instanceof Forge) {
            return "/images/forge.JPG";
        } else if (obj instanceof TourDeGarde) {
            return "/images/tower.JPG";
        } else {
            return "/images/empty.jpg";
        }
    }


    private void updateUIAfterMove() {
        int x = joueur.getPosition().get("X");
        int y = joueur.getPosition().get("Y");

        Object obj = carte.getObjetA(x, y);
        if (obj == null) {
            System.out.println("This cell is empty. You can build or explore!");
        } else if (obj instanceof ObjetRecuperable) {
            joueur.recupererObjet((ObjetRecuperable) obj, carte);
            System.out.println("You collected: " + ((ObjetRecuperable) obj).getNom());
        } else if (obj instanceof Ennemi) {
            joueur.combattre((Ennemi) obj, carte);
            System.out.println("You fought an enemy!");
        } else if (obj instanceof Batiment) {
            joueur.interagirBatiment((Batiment) obj);
            System.out.println("You interacted with a building.");
        }

        drawMap();
    }


    private void updateResourcesDisplay() {
        lblWood.setText("Bois: " + joueur.getRessource("Bois"));
        lblStone.setText("Pierre: " + joueur.getRessource("Pierre"));
        lblFood.setText("Nourriture: " + joueur.getRessource("Nourriture"));
    }


    @FXML
    private void craftItem() {
        try {
            int x = joueur.getPosition().get("X");
            int y = joueur.getPosition().get("Y");

            if (!carte.estVideA(x, y)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You can only craft on an empty space!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            List<Item> craftableItems = getCraftableItems();
            ChoiceDialog<Item> dialog = new ChoiceDialog<>(craftableItems.get(0), craftableItems);
            dialog.setTitle("Craft Item");
            dialog.setHeaderText("Choose an item to craft");
            dialog.setContentText("Select an item:");

            dialog.showAndWait().ifPresent(selectedItem -> {
                try {
                    joueur.fabriquerObjet(selectedItem);
                    updateResourcesDisplay();
                } catch (RessourcesInsuffisantesException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    error.showAndWait();
                }
            });
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W -> movePlayer("W", lblWood, lblStone, lblFood);
            case A -> movePlayer("A", lblWood, lblStone, lblFood);
            case S -> movePlayer("S", lblWood, lblStone, lblFood);
            case D -> movePlayer("D", lblWood, lblStone, lblFood);
            default -> welcomeText.setText("Invalid key!");
        }
    }


    @FXML
    protected void onOptions() {
        welcomeText.setText("Options de lancement...");

        Dialog<Void> optionsDialog = new Dialog<>();
        optionsDialog.setTitle("Options");
        optionsDialog.setHeaderText("Personnalisez votre expérience:");

        ComboBox<String> backgroundSelector = new ComboBox<>();
        backgroundSelector.getItems().addAll("Default", "Forest", "Village", "Army of Monsters");
        backgroundSelector.setValue(currentBackground);

        CheckBox musicToggle = new CheckBox("Jouer de la musique");
        musicToggle.setSelected(isMusicPlaying);

        Button selectMusicButton = new Button("Sélectionner la musique");
        selectMusicButton.setDisable(!isMusicPlaying);
        musicToggle.setOnAction(e -> selectMusicButton.setDisable(!musicToggle.isSelected()));

        selectMusicButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier musical");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
            File musicFile = fileChooser.showOpenDialog(selectMusicButton.getScene().getWindow());
            if (musicFile != null) {
                playMusic(musicFile.toURI().toString());
            }
        });

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Choisir l'arrière-plan:"),
                backgroundSelector,
                new Label("Réglages de la musique:"),
                musicToggle,
                selectMusicButton
        );

        optionsDialog.getDialogPane().setContent(content);
        optionsDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        optionsDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                currentBackground = backgroundSelector.getValue();
                isMusicPlaying = musicToggle.isSelected();

                changeBackground(currentBackground);

                if (!isMusicPlaying) {
                    stopMusic();
                }
            }
            return null;
        });

        optionsDialog.showAndWait();
    }

    private void changeBackground(String background) {
        String imageUrl;
        switch (background) {
            case "Forest":
                imageUrl = getClass().getResource("/images/forest.jpg").toExternalForm();
                break;
            case "Village":
                imageUrl = getClass().getResource("/images/village.jpg").toExternalForm();
                break;
            case "Army of Monsters":
                imageUrl = getClass().getResource("/images/army.jpg").toExternalForm();
                break;
            default:
                imageUrl = "";
        }

        if (mainPane != null && !imageUrl.isEmpty()) {
            mainPane.setStyle(
                    "-fx-background-image: url('" + imageUrl + "'); " +
                            "-fx-background-size: cover; " +
                            "-fx-background-position: center;");
        }
    }

    private void playMusic(String musicPath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        welcomeText.setText("La musique est en marche...");
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        welcomeText.setText("La musique s'est arrêtée.");
    }

    @FXML
    protected void onHelp() {
        welcomeText.setText("Afficher l'aide...");

        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("Aide - Instructions de jeu");
        helpDialog.setHeaderText("Comment jouer:");

        String helpContent = """
                                      ===== Contrôles =====
                                      - W / ↑ : Déplacer vers le haut
                                      - S / ↓ : Déplacer vers le bas
                                      - A / ← : Déplacement vers la gauche
                                      - D / → : Déplacer vers la droite
                                     \s
                                      ===== Histoire =====
                                      Vous êtes un survivant dans un monde post-apocalyptique. Votre but est de rassembler des ressources,\s
                                      construire des abris et combattre les ennemis pour assurer votre survie.
                
                                      ===== Objectifs ===
                                      - Récolter des ressources : bois, pierre, nourriture.
                                      - Construire des structures pour se protéger.
                                      - Combattre ou éviter les ennemis.
                                      - Explorer la carte à la recherche de trésors cachés.
                                     \s
                                      === Conseils ===
                                      - Utilisez vos ressources à bon escient.
                                      - Équipez et améliorez les objets pour obtenir de meilleures statistiques.
                                      - Explorez toutes les zones pour trouver des objets rares.
                                     \s
                                      Bonne chance !
                
                          
            """;

        helpDialog.setContentText(helpContent);

        helpDialog.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        helpDialog.getDialogPane().getStyleClass().add("dialog-pane");

        helpDialog.showAndWait();
    }

    @FXML
    private void onExit() {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Êtes-vous sûr de vouloir quitter le jeu?", ButtonType.YES, ButtonType.NO);
        exitConfirmation.setTitle("Quitter");
        exitConfirmation.setHeaderText(null);

        exitConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {

                Platform.exit();
            }
        });
    }
}