package Model;

import Controlleurs.Observateur;
import javax.swing.JPanel;

/**
 * Interface qui permet d'indiquer qu'il s'agit d'une vue
 * Inclut du Swing... catégorie Vue
 */
public interface VueInterne {
    
    /**
     * Permet d'obtenir un panel de la classe
     * @return
     */
    public JPanel getPanel();

    /**
     * Équivalent du SetVisible
     * @param visible
     */
    public void visible(boolean visible);

    /**
     * Permet d'ajouter un Observateur à une composante de la vue
     * @param i le numéro de la composante
     * @param ob L'Observateur à ajouter à la composante
     */
    public void ajouterListener(int i, Observateur ob);   
}
