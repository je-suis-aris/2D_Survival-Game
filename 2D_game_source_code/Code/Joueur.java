package com.example.tema2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

public class Joueur extends Personnage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int niveau;
    private int experience;
    private double poidsTransportable;
    private double poidsActuel;
    private Map<String, Integer> ressources;
    private Map<String, Integer> position;
    protected List<Item> equipements;
    private int points = 0;

    public Joueur(String nom, int attaque, int defense, int sante) {
        super(nom, attaque, defense, sante);

        this.niveau = 1;
        this.experience = 0;
        this.poidsTransportable = 300.0;
        this.poidsActuel = 0.0;

        this.ressources = new HashMap<>();
        this.ressources.put("Bois", 0);
        this.ressources.put("Pierre", 0);
        this.ressources.put("Nourriture", 0);

        this.position = new HashMap<>();
        this.position.put("X", 0);
        this.position.put("Y", 0);

        this.equipements = new ArrayList<>();
    }

    public void ajouterEquipement(Item item) {
        this.equipements.add(item);

        switch (item.getNom().toLowerCase()) {
            case "casque":
                this.defense += item.getBonusDefense();
                this.santeMax += item.getBonusSante();
                this.sante = Math.min(this.sante + item.getBonusSante(), this.santeMax);
                System.out.println("Casque ajouté. Défense et Santé augmentées.");
                break;

            case "épée":
                this.attaque += item.getBonusAttaque();
                System.out.println("Épée ajoutée. Attaque augmentée.");
                break;

            case "armure":
                this.defense += item.getBonusDefense();
                this.santeMax += item.getBonusSante();
                this.sante = Math.min(this.sante + item.getBonusSante(), this.santeMax);
                System.out.println("Armure ajoutée. Toutes les statistiques augmentées.");
                break;

            default:
                System.out.println(item.getNom() + " ajouté. Aucun bonus spécifique appliqué.");
        }

        System.out.println("Bonus ajoutés :");
        if (item.getBonusAttaque() > 0) {
            System.out.println("- Attaque +" + item.getBonusAttaque());
        }
        if (item.getBonusDefense() > 0) {
            System.out.println("- Défense +" + item.getBonusDefense());
        }
        if (item.getBonusSante() > 0) {
            System.out.println("- Santé +" + item.getBonusSante());
        }

        afficherStatistiques();
    }


    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public void setDefense(int defense) {
        this.defense = defense;
        System.out.println("La défense du joueur est maintenant de " + this.defense + ".");
    }


    public void combattre(Ennemi ennemi, CarteJeu carte) {
        System.out.println("Début du combat avec : " + ennemi.getNom());
        int x = position.get("X");
        int y = position.get("Y");

        while (this.getStatut() == Statut.VIVANT && ennemi.getStatut() == Statut.VIVANT) {
            System.out.println("\n" + this.getNom() + " attaque !");
            this.attaquer(ennemi);
            System.out.println(ennemi.getNom() + " - Santé restante : " + ennemi.getSante());

            if (ennemi.getStatut() == Statut.MORT) {
                System.out.println(ennemi.getNom() + " a été vaincu !");
                System.out.println("Vous obtenez " + ennemi.getExperienceDonnee() + " points d'expérience.");
                this.gagnerExperience(ennemi.getExperienceDonnee());

                Item objet = ennemi.laisserTomberObjet();
                if (objet != null) {
                    System.out.println("L'ennemi a laissé tomber : " + objet.getNom());
                    this.ajouterEquipement(objet);
                }
                carte.supprimerObjetA(x, y);
                return;
            }

            System.out.println("\n" + ennemi.getNom() + " contre-attaque !");
            ennemi.attaquer(this);
            System.out.println(this.getNom() + " - Santé restante : " + this.getSante());

            if (this.getStatut() == Statut.MORT) {
                System.out.println("Vous avez été vaincu par " + ennemi.getNom() + "...");
                return;
            }
        }
    }

    public void collecterRessource(String type, int quantite, Qualite qualite) {
        int actuel = ressources.getOrDefault(type, 0);

        double facteurQualite = obtenirFacteurQualite(qualite);
        int quantitePortee = (int) Math.min(quantite, poidsTransportable * facteurQualite);

        if (poidsActuel + quantitePortee <= poidsTransportable) {
            ressources.put(type, actuel + quantitePortee);
            poidsActuel += quantitePortee;
        } else {
            System.out.println("Vous ne pouvez pas porter cet objet, il est trop lourd !");
        }
    }

    public void updateRessource(String resource, int newValue) {
        if (ressources.containsKey(resource)) {
            ressources.put(resource, newValue);
        } else {
            System.out.println("Resource not found: " + resource);
        }
    }
    public void addPoints(int amount) {
        points += amount;
        if (points < 0) {
            points = 0;
        }
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void subtractPoints(int points) {
        this.points = Math.max(0, this.points - points);
    }



    private double obtenirFacteurQualite(Qualite qualite) {
        switch (qualite) {
            case COMMUNE:
                return 1.0;
            case RARE:
                return 1.5;
            case EPIQUE:
                return 2.0;
            case LEGENDAIRE:
                return 2.5;
            default:
                return 1.0;
        }
    }
    public void afficherRessourcesCollectees() {
        System.out.println("Ressources collectées :");
        for (Map.Entry<String, Integer> entry : ressources.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


    public void recupererObjet(ObjetRecuperable obj, CarteJeu carte) {
        double poidsTotal = poidsActuel + obj.getPoids();
        if (poidsTotal > poidsTransportable) {
            System.out.println("Vous ne pouvez pas porter cet objet, il est trop lourd !");
            return;
        }

        obj.recuperer(this);
        poidsActuel = poidsTotal;


        int x = position.get("X");
        int y = position.get("Y");
        carte.supprimerObjetA(x, y);
    }

    private transient List<BiConsumer<Integer, Integer>> healthListeners = new ArrayList<>();

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        in.defaultReadObject();
        this.healthListeners = new ArrayList<>();
    }
    public void addHealthListener(BiConsumer<Integer, Integer> listener) {
        healthListeners.add(listener);
    }

    public void setSante(int newSante) {
        int oldSante = this.sante;
        this.sante = Math.max(0, Math.min(newSante, santeMax));

        if (oldSante != this.sante) {
            for (BiConsumer<Integer, Integer> listener : healthListeners) {
                listener.accept(oldSante, this.sante);
            }
        }
    }

    public int getSante() {
        return sante;
    }

    public int getSanteMax() {
        return santeMax;
    }


    public void fabriquerObjet(Item item) throws RessourcesInsuffisantesException {
        Map<String, Integer> cout = item.getCoutFabrication();
        List<String> ressourcesManquantes = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : cout.entrySet()) {
            String ressource = entry.getKey();
            int quantiteRequise = entry.getValue();
            int quantiteDisponible = this.ressources.getOrDefault(ressource, 0);

            if (quantiteDisponible < quantiteRequise) {
                ressourcesManquantes.add(
                        ressource + " (nécessaire : " + quantiteRequise
                                + ", disponible : " + quantiteDisponible + ")"
                );
            }
        }

        if (!ressourcesManquantes.isEmpty()) {
            System.out.println("Pas assez de ressources pour fabriquer " + item.getNom() + ":");
            for (String manque : ressourcesManquantes) {
                System.out.println("- " + manque);
            }
            throw new RessourcesInsuffisantesException("Impossible de fabriquer l'objet.");
        }

        for (Map.Entry<String, Integer> entry : cout.entrySet()) {
            String ressource = entry.getKey();
            int quantiteRequise = entry.getValue();
            this.ressources.put(
                    ressource,
                    this.ressources.get(ressource) - quantiteRequise
            );
            System.out.println("Ressource consommée : " + ressource + " (-" + quantiteRequise + ")");
        }

        this.ajouterEquipement(item);
        System.out.println("Vous avez fabriqué : " + item.getNom());
    }



    public void interagirBatiment(Batiment batiment) {
        batiment.appliquerEffet(this);
    }


    @Override
    public int attaquer(Personnage cible) {
        int attaqueTotale = this.attaque;

        for (Item item : equipements) {
            attaqueTotale += item.getBonusAttaque();
        }

        attaqueTotale *= 1.2;

        boolean coupCritique = Math.random() < 0.2;
        if (coupCritique) {
            attaqueTotale *= 1.5;
            System.out.println("Coup critique!");
        }

        System.out.println(this.nom + " inflige " + attaqueTotale + " des dégâts à " + cible.getNom() + ".");
        cible.subirDegats(attaqueTotale);
        return attaqueTotale;
    }


    public void construire(Batiment batiment, int x, int y, CarteJeu carte) throws RessourcesInsuffisantesException {
        if (!carte.estVideA(x, y)) {
            throw new RessourcesInsuffisantesException("Position (" + x + ", " + y + ") est déjà occupé.");
        }

        Map<String, Integer> cout = batiment.getCoutConstruction();
        for (Map.Entry<String, Integer> entry : cout.entrySet()) {
            String ressource = entry.getKey();
            int quantiteRequise = entry.getValue();
            int quantiteDisponible = ressources.getOrDefault(ressource, 0);

            if (quantiteDisponible < quantiteRequise) {
                throw new RessourcesInsuffisantesException("Pas assez " + ressource + ". Exigée: " + quantiteRequise + ", Disponible: " + quantiteDisponible);
            }
        }

        for (Map.Entry<String, Integer> entry : cout.entrySet()) {
            String ressource = entry.getKey();
            ressources.put(ressource, ressources.get(ressource) - entry.getValue());
        }

        carte.placerObjetA(x, y, batiment);

        System.out.println("Construit " + batiment.getNom() + " à (" + x + ", " + y + ")");
    }

    public double getPoidsTransportable() {
        return poidsTransportable;
    }

    public double getPoidsActuel() {
        return poidsActuel;
    }



    @Override
    public void subirDegats(int degats) {
        int defenseTotale = this.defense;
        for (Item item : equipements) {
            defenseTotale += item.getBonusDefense();
        }
        int degatsReels = Math.max(0, degats - defenseTotale);
        this.setSante(this.sante - degatsReels);
        System.out.println(this.nom + " subit " + degatsReels + " dégâts.");
        if (this.sante <= 0) {
            mourir();
        }
    }



    @Override
    public void mourir() {
        this.statut = Statut.MORT;
        System.out.println(this.nom + " est mort. Fin du jeu !");
    }

    public void gagnerExperience(int exp) {
        this.experience += exp;
        while (this.experience >= this.niveau * 100) {
            this.experience -= this.niveau * 100;
            this.niveau++;
            this.santeMax += 10;
            this.sante = this.santeMax;
            this.attaque += 5;
            this.defense += 2;
            System.out.println("Vous avez atteint le niveau " + this.niveau + " !");
        }
    }

    public void deplacer(char direction, CarteJeu carte) throws HorsDeLaCarteException {
        int x = position.get("X");
        int y = position.get("Y");
        int nouveauX = x;
        int nouveauY = y;

        switch (direction) {
            case 'W':
            case 'w': nouveauX -= 1; break;
            case 'S':
            case 's': nouveauX += 1; break;
            case 'A':
            case 'a': nouveauY -= 1; break;
            case 'D':
            case 'd': nouveauY += 1; break;
            default:
                System.out.println("Direction invalide !");
                return;
        }

        if (nouveauX < 0 || nouveauX >= carte.getTaille() || nouveauY < 0 || nouveauY >= carte.getTaille()) {
            throw new HorsDeLaCarteException("Vous ne pouvez pas sortir de la carte !");
        }

        position.put("X", nouveauX);
        position.put("Y", nouveauY);

        for (Batiment batiment : carte.getBuildingsNear(nouveauX, nouveauY)) {
            batiment.appliquerEffet(this);
        }

        Object obj = carte.getObjetA(nouveauX, nouveauY);
        if (obj instanceof Interactable) {
            ((Interactable) obj).interact(this, carte);
        } else {
            System.out.println("Il y a un objet non interactif ici.");
        }
    }


    public int getRessource(String type) {
        return ressources.getOrDefault(type, 0);
    }

    public void setRessource(String resource, int amount) {
        if (amount < 0) {
            this.ressources.put(resource, 0);
        } else {
            this.ressources.put(resource, amount);
        }
    }


    public int getNiveau() {
        return niveau;
    }

    public int getExperience() {
        return experience;
    }

    public Map<String, Integer> getPosition() {
        return position;
    }

    public void afficherInventaire() {
        System.out.println("Inventaire trié par valeur monétaire décroissante :");
        List<Objet> objetsTries = new ArrayList<>(equipements);
        Collections.sort(objetsTries, new ComparateurObjetValeur());
        for (Objet objet : objetsTries) {
            System.out.println(objet);
        }
    }

    public String getInventaireString() {
        StringBuilder sb = new StringBuilder("Inventaire trié par valeur monétaire décroissante :\n");
        List<Objet> objetsTries = new ArrayList<>(equipements);

        Collections.sort(objetsTries, new ComparateurObjetValeur());

        for (Objet objet : objetsTries) {
            sb.append(objet.toString()).append("\n");
        }
        return sb.toString();
    }


    public void afficherStatistiques() {
        System.out.println("=== Statistiques du joueur ===");
        System.out.println("Nom : " + this.nom);
        System.out.println("Niveau : " + this.niveau);
        System.out.println("Expérience : " + this.experience + " / " + (this.niveau * 100));
        System.out.println("Santé : " + this.sante + " / " + this.santeMax);
        System.out.println("Attaque : " + this.attaque);
        System.out.println("Défense : " + this.defense);
        System.out.println("Poids transportable : " + this.poidsTransportable + " kg");
        System.out.println("Poids actuel : " + this.poidsActuel + " kg");
        System.out.println("===============================");
    }
    public void ameliorerEquipement() {
        if (equipements.isEmpty()) {
            System.out.println("Vous n'avez aucun équipement à améliorer.");
            return;
        }

        System.out.println("Choisissez un équipement à améliorer :");
        List<Item> equipementsList = new ArrayList<>(equipements);
        for (int i = 0; i < equipementsList.size(); i++) {
            Item item = equipementsList.get(i);
            System.out.println((i + 1) + ". " + item.getNom() + " (Attaque: +" + item.getBonusAttaque() +
                    ", Défense: +" + item.getBonusDefense() + ", Santé: +" + item.getBonusSante() + ")");
        }

        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt() - 1;
        if (choix >= 0 && choix < equipementsList.size()) {
            Item item = equipementsList.get(choix);
            item.ameliorer();
            System.out.println("Votre " + item.getNom() + " a été amélioré !");
        } else {
            System.out.println("Choix invalide.");
        }
    }


}