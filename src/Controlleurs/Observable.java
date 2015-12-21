package Controlleurs;

/**
 * Interface permettant à une classe d'être observée par un observateur.
 * Utile à l'envoi de notifications.
 */
public interface Observable {

    /**
     * Ajoute un observateur à la classe
     * @param observateur l'observateur à ajouter
     */
    public void ajouterObservateur(Observateur observateur);

    /**
     * Retire un observateur de la classe
     * @param observateur l'observateur à enlever
     */
    public void removeObservateur(Observateur observateur);

    /**
     * Envoie un message à toutes les classes qui observent cette classe
     * @param event événement à gérer
     */
    public void notifierModification(ModificationEvent event);
}
