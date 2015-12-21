package Controlleurs;

/**
 * Les événements qui sont généré tout au long du programme. se sont des événements personnalisé.
 */
public class ModificationEvent {

    /**
     * le type d'événement
     */
    public enum Type {
        /**
         * image
         */
        IMG,
        /**
         * information
         */
        INFO,
        /**
         * administration
         */
        ADMIN,
        /**
         * boutons du tableau périodique
         */
        BOUTON};
    private final Type type;
    private int entier;
    private String nom;
    private String chaine;
    private String description;
    
    /**
     * constructeur
     * @param type type d'événement
     */
    public ModificationEvent(Type type) {
        this.type = type;
    }
    
    /**
     *
     * @return le type
     */
    public Type getType() {
        return type;
    }
    
    /**
     * permet de seter l'entier de l'événement (le numéro de l'élément qui correspond à l'événement
     * (utilisé dans le type boutons)
     * @param ent entier
     */
    public void setEntier(int ent) {
        entier = ent;
    }

    /**
     *
     * @return entier
     */
    public int getEntier() {
        return entier;
    }
    
    /**
     * nom de l'atome de l'événement (utilisé dans le typer administration)
     * @param nom le nom de l'atome
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return nom 
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * la description qui est ajouter d'un atome
     * (utilisé dans le type administration)
     * @param description description ajouté
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     *
     * @param ch une information
     */
    public void setChaine(String ch) {
        chaine = ch;
    }
    
    /**
     *
     * @return l'information
     */
    public String getChaine() {
        return chaine;
    }
}