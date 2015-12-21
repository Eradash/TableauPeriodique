package tableauperiodique;

import Controlleurs.Controleur;
import Model.Modele;
import Model.ModelVue;
import Vues.VueChargement;
import java.awt.EventQueue;

/**
 * Tableau périodique programmé dans le cadre du cours de Conception d'interface
 * avec Aymen Sioud en automne 2013.
 * @author Marc-Antoine, Alexandre, Vincent et Ève.
 * @version un million et demi .0  --"
 */
public class TableauPeriodique {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Modele model = new Modele();
                ModelVue vue = new VueChargement(model);
                Controleur c = new Controleur(model, vue);
            }
        });
    }
}