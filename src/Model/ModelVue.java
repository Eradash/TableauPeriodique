package Model;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import java.util.ArrayList;

/**
 * Représentation abstraite d'une Vue
 * Permet de controller des classes qui n'ont pas de lien avec des interfaces graphiques
 */
public abstract class ModelVue implements Observable{

    private final ArrayList<Observateur> listeObservateur;
    private final Modele modele;

    /**
     * Constructeur
     * @param modele Modèle à observer
     */
    public ModelVue(Modele modele) {
        listeObservateur = new ArrayList<>();
        this.modele = modele;
    }

    /**
     * Fonction qui permet de récupérer le modèle
     * @return Modele Le model de la classe
     */
    public Modele getModele() {
        return modele;
    }

    /**
     * Permet d'ajouter un obserbateur à la liste d'observateurs
     * @param ob L'Observateur en question
     */
    @Override
    public void ajouterObservateur(Observateur ob) {
        listeObservateur.add(ob);
    }

    /**
     * Permet de retirer un observateur de la liste d'observateurs
     * @param ob L'Observateur en question
     */
    @Override
    public void removeObservateur(Observateur ob) {
        listeObservateur.remove(ob);
    }

    /**
     * Permet d'envoyer un message à tout les observateurs
     * @param event L'Evenement qui s'est produit (le message en question)
     */
    @Override
    public void notifierModification(ModificationEvent event) {
        for(Observateur ob: listeObservateur){
            ob.miseAjour(event);
        }
    }
    
    /**
     * Permet d'afficher la vue
     */
    public abstract void display();

    /**
     * Permet de fermer la vue
     */
    public abstract void close();

    /**
     * Équivalent du SetVisible()
     * @param x
     */
    public abstract void visible(boolean x);

}
