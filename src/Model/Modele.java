package Model;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Modele du programme. Permet de récupérer des données telles que le mot
 * de passe d'administration ou les instances des singletons.
 */
public class Modele implements Observable {

    private final ArrayList<Observateur> observateurs;
    private final GestionElement ge;
    private final GestionRessource gr;
    private String motPasse;
    private boolean modifier;

    /**
     * Construceur
     */
    public Modele() {
        observateurs = new ArrayList<>();
        ge = GestionElement.getInstance();
        gr = GestionRessource.getInstance();
        motPasse = "meva";
        modifier = false;
    }
    
    /**
     * Permet d'obtenir le mot de passe de la partie Administration
     * @return String Le mot de passe en question
     */
    public String getMotPasse() {
        return motPasse;
    }

    /**
     * Retourne l'instance du gestionnaire d'élément
     * @return GestionElement La classe Gestion Élément en question
     */
    public GestionElement getGe() {
        return ge;
    }

    /**
     *  Retourne l'instance du gestionnaire de ressources.
     * @return GestionRessource La classe Gestion Ressource en question
     */
    public GestionRessource getGr() {
        return gr;
    }

    /**
     * Permet de modifier le mot de passe
     * @param motPasse Le nouveau mot de passe
     */
    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    /**
     * Voir Observable
     * @param observateur
     */
    @Override
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    /**
     * Voir Observable
     * @param observateur
     */
    @Override
    public void removeObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    /**
     * Voir Observable
     * @param event
     */
    @Override
    public void notifierModification(ModificationEvent event) {
        for (Observateur obs : observateurs) {
            obs.miseAjour(new ModificationEvent(ModificationEvent.Type.INFO));
        }
    }

    /**
     * Permet de savoir s'il y a eu une modification des éléments
     * @return boolean true si une modification a eu lieu
     */
    public boolean getModifier() {
        return modifier;
    }

    /**
     * Permet d'indiquer qu'un élément a été modifié
     * @param modifier true si un élément a été modifié
     */
    public void setModifier(boolean modifier) {
        this.modifier = modifier;
    }
    
    /**
     * Permet de réécrire le fichier XML s'il y a eu des modifications
     */
    public void reecrire(){
        try {
            XML xml = new XML();
            xml.reecrire(ge.getListeElement());
        } catch (ParserConfigurationException ex) {Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
