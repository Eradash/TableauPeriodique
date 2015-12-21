package Controlleurs;

import Model.Modele;
import Model.ModelVue;
import Model.VueInterne;
import Vues.VueAdmin;
import Vues.VueMotPasse;
import Vues.VueAtome;
import Vues.VueListe;
import Vues.VueTableau;

/**
 * Le controleur du programme
 * Il gère les événements et fait les modification des vues et du model. Il fait une partie 
 * de la communication entre la vue et le modèle
 */
public class Controleur implements Observateur {

    private final Modele modele;
    private ModelVue vueAtome;
    private ModelVue vueTableau;
    private ModelVue vueAdministration;
    private final ModelVue vueChargement;
    private ModelVue vueListe;
    private ModelVue vueMotPasse;

    /**
     * Constructeur
     * @param model le modèle
     * @param vue la vue de départ, qui est la vue chargement.
     */
    public Controleur(final Modele model, ModelVue vue) {
        modele = model;
        this.vueChargement = vue;
        vue.ajouterObservateur(this);

        initialisation();
    }

    /**
     * initialise le constructeur
     */
    private void initialisation() {
        vueTableau = new VueTableau(modele);
        vueTableau.ajouterObservateur(this);
        vueAdministration = new VueAdmin(modele);
        vueAdministration.ajouterObservateur(this);
        vueListe = new VueListe(modele);
        vueListe.ajouterObservateur(this);
        vueMotPasse = new VueMotPasse(modele);
        vueMotPasse.ajouterObservateur(this);

        AjouterListenerBoutons((VueInterne) vueTableau);
        AjouterListenerBoutons((VueInterne) vueListe);

    }

    /**
     * Ajoute des listener au boutons du tableau
     * @param vue la vue qui contient les boutons.
     */
    public void AjouterListenerBoutons(VueInterne vue) {
        for (int i = 1; i <= 118; i++) {
            vue.ajouterListener(i, this);
        }
    }

    /**
     * Gère tout les évenements en provenance des vues et du modèle
     * @param event un évenement
     */
    @Override
    public void miseAjour(ModificationEvent event) {

        if (event.getType() == ModificationEvent.Type.INFO) {
            switch (event.getChaine()) {
                // lorsque la vue chargement est finie
                case "chargement":
                    closeAll();
                    vueTableau.display();
                    break;
                //Lorsque la vue de la liste est activée
                case "vueOptionActive":
                    vueListe.display();
                    break;
                //Lorsque la vue de la liste est désactivée
                case "vueOptionInactive":
                    vueListe.close();
                    break;
                //Lorsqu'on quite le programme
                case "quitter":
                    if(modele.getModifier()){
                        modele.reecrire();
                    }
                    modele.getGr().terminer();
                    modele.getGe().terminer();
                    System.exit(0);
                    break;
                //lorsqu'on veut se connecter à l'administration
                case "connection":
                    vueMotPasse.display();
                    break;
                //lorsque le mot de passe est entré correctement
                case "motPasse":
                    closeAll();
                    vueAdministration.display();
                    break;
                //lorsqu'on veut revenir a la vue du tableau, lorsqu'on est dans la vue de l'atome
                case "retour":
                    closeAll();
                    vueTableau.display();
                    break;
                // Lorsqu'on se déconnecte de l'administration
                case "deconnection":
                    closeAll();
                    vueTableau.display();
            }

        }
        //lorsque qu'un bouton du tableau est cliqué
        if (event.getType() == ModificationEvent.Type.BOUTON) {
            int noAtomique = event.getEntier();
            closeAll();
            vueAtome = new VueAtome(modele, noAtomique);
            vueAtome.ajouterObservateur(this);
            vueAtome.display();
        }
        //lorsque l'administrateur fait une modification dans les informations
        if (event.getType() == ModificationEvent.Type.ADMIN) {
            System.out.print("ddd");
            String nomAtome = event.getNom();
            modele.getGe().getElement(nomAtome).setProp("Description", event.getDescription());
            modele.setModifier(true);
        }
    }

    /**
     * Ferme toute les vues qui peuvent être ouvertes
     */
    public void closeAll() {
        vueTableau.close();
        vueMotPasse.close();
        vueListe.close();
        if (vueAtome != null) {
            vueAtome.close();
        }
    }
}
