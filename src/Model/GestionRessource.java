package Model;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Permet de charger les images nécessaires (BufferedImage) au programme à 
 * partir du disque dur. Elles sont ensuite récupérables par des accesseurs à partir
 * de HashMaps. Cette classe implément Observable pour notifier la progressBar de 
 * chargement.
 * @author Marc-Antoine
 */
public class GestionRessource implements Observable {

    private static GestionRessource instance = null;

    final private HashMap<String, Integer> couleur; //Contient toutes les images
    final private HashMap<Integer, BufferedImage> mapImage, mapConfiguration, mapIcone, mapFondsNormal, mapFondsPese, mapAutres;

    /**
     *
     */
    public static final int NOMBRE_ELEMENTS = 118;

    /**
     *
     */
    public static final int NOMBRE_FONDS = 11;

    /**
     *
     */
    public static final int NOMBRE_AUTRES = 14;

    /**
     *
     */
    public static int NOMBRE_TOTAL_IMAGES = 2 * NOMBRE_FONDS +3* NOMBRE_ELEMENTS + NOMBRE_AUTRES;

    /**
     *
     */
    public static int loadedImages = 0;
    private final ArrayList<Observateur> obs;
    private String lastPath, fichier;

    private GestionRessource() {
        mapImage = new HashMap<>(); //Images diverses
        mapConfiguration = new HashMap<>(); //Configuration électronique
        mapIcone = new HashMap<>(); //Icones de tuiles
        mapFondsNormal = new HashMap<>(); //Fonds de tuiles
        mapFondsPese = new HashMap<>(); //Fonds de tuiles clicked
        mapAutres = new HashMap<>();
        couleur = new HashMap();
        obs = new ArrayList<>();
        chargerAutre();

        Thread chargement = new Thread() {
            @Override
            public void run() {
                chargerFondIcone(); //On charge les fonds de chaque icone
                chargerIcone(); //On charge tous les icones(le texte)
                chargerConfiguration(); //On charge les configurations électroniques
                chargerImages(); //On charge toutes les images diverses
            }
        };

        chargement.start();
    }

    /**
     * Récupère l'instance unique du gestionnaire de ressources
     *
     * @return le gestionnaire de ressources
     */
    protected static GestionRessource getInstance() { //Singleton

        if (instance == null) {
            instance = new GestionRessource();
        }

        return instance;
    }

    /**
     * Charge une image selon son chemin et le met dans un HashMap
     *
     * @param nom le chemin de l'image
     * @param map Map qui ajoute l'image
     * @param no Numéro de l'élément
     * @return BufferedImage
     */
    public BufferedImage lireImage(String nom, HashMap map, int no) {
        BufferedImage bi = null;

        synchronized (map) {
            if (map.containsKey(nom) == false) {
                no = map.size() + 1;

                try {
                    bi = ImageIO.read(new File(nom));
                    map.put(no, bi);
                    //System.out.println(nom + " chargé. ");
                } catch (IOException e) {
                    System.err.println(nom + " ne peut être lue. Vérifiez le chemin ou le nom. ");
                }
            } else {
                System.out.println("Image déjà existante");
            }
        }
        
        return bi;
    }
    

    /**
     * Charger les images de fond de tuile
     */
    private void chargerFondIcone() {
        fichier = "Fonds/Normal/";

        for (int x = 1; x < (NOMBRE_FONDS + 1); x++) {
            lastPath = fichier + x + ".png";
            lireImage(lastPath, mapFondsNormal, x);
            loadedImages++;
            notifierModification();
        }

        fichier = "Fonds/Pese/";

        for (int x = 1; x < (NOMBRE_FONDS + 1); x++) {
            lastPath = fichier + x + ".png";
            lireImage(lastPath, mapFondsPese, x);
            loadedImages++;
            notifierModification();
        }
    }

    /**
     * Charger toutes les icones (textes des tuiles)
     */
    private void chargerIcone() {
        fichier = "Icones/i";

        for (int x = 1; x < (NOMBRE_ELEMENTS + 1); x++) {
            lastPath = fichier + x + ".png";
            lireImage(lastPath, mapIcone, x);
            loadedImages++;
            notifierModification();

        }
    }

    /**
     * Charger toutes les images des couches électroniques
     */
    private void chargerConfiguration() {
        fichier = "Configuration/";

        for (int x = 1; x < (NOMBRE_ELEMENTS + 1); x++) {
            lastPath = fichier + x + ".png";
            lireImage(lastPath, mapConfiguration, x);
            loadedImages++;
            notifierModification();
        }
    }

    /**
     * Charger toutes les images d'éléments
     */
    private void chargerImages() {
        fichier = "Images/"; //Chemin du fichier contenant les images

        for (int x = 1; x < (NOMBRE_ELEMENTS + 1); x++) {
            lastPath = fichier + x + ".jpg";
            lireImage(lastPath, mapImage, x); //On charge chaque image
            loadedImages++;
            notifierModification();
        }
    }
    
    /**
     * Fonds, légendes, etc.
     */
    public void chargerAutre() {
        lireImage("legende.png", mapAutres, 0);
        loadedImages++;
        notifierModification();
        lireImage("legende2.png", mapAutres, 1);
        loadedImages++;
        notifierModification();
        lireImage("legende3.png", mapAutres, 2);
        loadedImages++;
        notifierModification();
        lireImage("fondTableau.png", mapAutres, 3);
        loadedImages++;
        notifierModification();
        lireImage("fondChargement.jpg", mapAutres, 4);
        loadedImages++;
        notifierModification();
        lireImage("fondAtome.jpg", mapAutres, 5);
        loadedImages++;
        notifierModification();
        lireImage("fondAdmin.jpg", mapAutres, 6);
        loadedImages++;
        notifierModification();
        lireImage("icone.jpg", mapAutres, 7);
        loadedImages++;
        notifierModification();
        lireImage("retour.png", mapAutres, 8);
        loadedImages++;
        notifierModification();
        lireImage("precedent.png", mapAutres, 9);
        loadedImages++;
        notifierModification();
        lireImage("suivant.png", mapAutres, 10);
        loadedImages++;
        notifierModification();
        lireImage("fondListe.jpg", mapAutres, 11);
        loadedImages++;
        notifierModification();
        lireImage("deconnection.png", mapAutres, 12);
        loadedImages++;
        notifierModification();
        lireImage("confirmer.png", mapAutres, 13);
        loadedImages++;
        notifierModification();
    }

    /**
     * Récupérer une image déjà chargée à partir de son numéro (no élément)
     *
     * @param no le numéro de l'élément
     * @return BufferedImage
     */
    public BufferedImage getImage(Integer no) {  //On prend l'image selon le numero de l'élément

        return mapImage.get(no);
    }

    /**
     * Récupérer la configuration électronique déjà chargée à partir de son numéro (no élément)
     *
     * @param no le numéro de l'élément
     * @return BufferedImage
     */
    public BufferedImage getConfiguration(Integer no) { //On prend l'image de l'atome selon le numero de l'élément
        return mapConfiguration.get(no);
    }

    /**
     * Récupérer l'icone déjà chargé à partir de son numéro (no élément)
     *
     * @param no le numéro de l'élément
     * @return BufferedImage
     */
    public BufferedImage getIcone(Integer no) {
        return mapIcone.get(no);
    }

    /**
     * Récupérer le fond de couleur déjà chargé à partir de sa famille
     * @param famille 
     * @return BufferedImage
     */
    public BufferedImage getFond(String famille) {
        this.couleur.put("Transition", 1);
        this.couleur.put("Alcalino-terreux", 2);
        this.couleur.put("Métalloïde", 3);
        this.couleur.put("Inconnu", 4);
        this.couleur.put("Alcalin", 5);
        this.couleur.put("Pauvre", 6);
        this.couleur.put("Gaz rares", 7);
        this.couleur.put("Halogène", 8);
        this.couleur.put("Non-métal", 9);
        this.couleur.put("Actinide", 10);
        this.couleur.put("Lanthanoïde", 11);
        int noImg = this.couleur.get(famille);

        return mapFondsNormal.get(noImg);
    }

    /**
     * Récupérer le fond (cliqué) de couleur déjà chargé à partir de sa famille
     * @param famille 
     * @return BufferedImage
     */
    public BufferedImage getFondPese(String famille) {
        this.couleur.put("Transition", 1);
        this.couleur.put("Alcalino-terreux", 2);
        this.couleur.put("Métalloïde", 3);
        this.couleur.put("Inconnu", 4);
        this.couleur.put("Alcalin", 5);
        this.couleur.put("Pauvre", 6);
        this.couleur.put("Gaz rares", 7);
        this.couleur.put("Halogène", 8);
        this.couleur.put("Non-métal", 9);
        this.couleur.put("Actinide", 10);
        this.couleur.put("Lanthanoïde", 11);
        int noImg = this.couleur.get(famille);

        return mapFondsPese.get(noImg);
    }

    /**
     * Récupérer une image déjà chargée selon un numéro
     * @param noImage
     * @return BufferedImage
     */
    public BufferedImage getAutre(int noImage){
        return mapAutres.get(noImage);
    }
    
    /**
     * Vide tous les HashMap
     */
    public void terminer() {
        // Suppression des listes        
        mapConfiguration.clear();
        mapImage.clear();
        mapIcone.clear();
        mapFondsNormal.clear();
        mapFondsPese.clear();
        mapAutres.clear();
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void ajouterObservateur(Observateur observateur) {
        obs.add(observateur);
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void removeObservateur(Observateur observateur) {
        obs.remove(observateur);
    }

    /**
     *
     * @param event
     */
    @Override
    public void notifierModification(ModificationEvent event) {
        for (Observateur ob : obs) {
            ob.miseAjour(event);
        }
    }
    
    /**
     *
     */
    public void notifierModification(){
        ModificationEvent mod = new ModificationEvent(ModificationEvent.Type.IMG);
            mod.setEntier(loadedImages);
            mod.setChaine(lastPath);
            notifierModification(mod);
    }
}