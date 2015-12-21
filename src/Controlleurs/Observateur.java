/*
 
 */

package Controlleurs;

import Controlleurs.ModificationEvent;

/**
 * Interface permettant à une classe d'observer un observable.
 * Utile à l'envoi de notifications.
 * @author Marc-Antoine
 */
public interface Observateur {

    /**
     *
     * @param event événement a traiter
     */
    public void miseAjour(ModificationEvent event);
}
