package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import Model.Modele;
import Model.VueInterne;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *Le Frame principale qui contient les différentes vue à afficher
 */
public class FramePrincipal extends JFrame implements MouseMotionListener, Observable {

    private static FramePrincipal instance = null;

    private boolean titleBarVisible;

    private final JMenuBar titleBar;
    private final JMenu mnFile, mnAdministration;
    private final JMenuItem mniQuitter, mniConnection, mniDeconnection, vueListe;
    private ModificationEvent evenementMenu;
    private Modele modele;

    private JPanel vue, vueOption;
    private boolean vueOptionActive = false;

    private final ArrayList<Observateur> listeObservateurs;

    /**
    * constructeur
    * @param modele le modele
    */
    private FramePrincipal(Modele modele) {
        this.modele = modele;
        this.listeObservateurs = new ArrayList<>();
        setUndecorated(true);
        titleBarVisible = true;
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setTitle("Tableau périodique");
        setIconImage(modele.getGr().getAutre(8));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Barre de menu
        titleBar = new JMenuBar();
        titleBarVisible = true;
        mnFile = new JMenu("Fichier");
        mnAdministration = new JMenu("Administration");
        mniQuitter = new JMenuItem("Quitter");
        mniConnection = new JMenuItem("Connexion");
        mniDeconnection = new JMenuItem("Déconnexion");
        vueListe = new JMenuItem("Liste d'éléments");
        vueListe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evenementMenu = new ModificationEvent(ModificationEvent.Type.INFO);
                if (vueOptionActive) {
                    evenementMenu.setChaine("vueOptionInactive");
                } else {
                    evenementMenu.setChaine("vueOptionActive");
                }
                notifierModification(evenementMenu);
            }
        });
        mniQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evenementMenu = new ModificationEvent(ModificationEvent.Type.INFO);
                evenementMenu.setChaine("quitter");
                notifierModification(evenementMenu);
            }
        });
        mniConnection.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                evenementMenu = new ModificationEvent(ModificationEvent.Type.INFO);
                evenementMenu.setChaine("connection");
                notifierModification(evenementMenu);
            }
        });
        mniDeconnection.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ModificationEvent event = new ModificationEvent(ModificationEvent.Type.INFO);
                event.setChaine("deconnection");
                notifierModification(event);
            }
        });
        mnAdministration.add(mniConnection);
        mnAdministration.add(mniDeconnection);
        mnFile.add(vueListe);
        mnFile.add(mniQuitter);
        titleBar.add(mnFile);
        titleBar.add(mnAdministration);

        updatePanels();
        vue = null;

    }
    
    /**
    * réinitialisation des composantes. enleve ce qui n'est plus nécéssaire et ajoute le nouveau
    */

    private void updatePanels() {
        if (vueOptionActive) {
            add(vueOption);
        } else if (vueOption != null) {
            remove(vueOption);
        }
        if (vue != null) {
            add(vue);
            vue.setVisible(true);
            vue.repaint();
        }
        setJMenuBar(titleBar);

        repaint();
    }

    /**
     * vérifie l'affichage des vues
     * @param vue la vue
     */
    public void setVuePrincipale(VueInterne vue) {
        if (this.vue != null) {
            remove(this.vue);
        }
        this.vue = vue.getPanel();
        if (vue instanceof VueAdmin) {
            mniConnection.setEnabled(false);
            mniDeconnection.setEnabled(true);
        } else {
            mniConnection.setEnabled(true);
            mniDeconnection.setEnabled(false);
        }
        updatePanels();
        setVisible(true);
    }

    /**
     * Active la vue de la liste et qui avertie les observateurs
     * @param option
     */
    public void setVueOption(boolean option) {
        ModificationEvent event = new ModificationEvent(ModificationEvent.Type.INFO);
        if (vueOptionActive != option) {
            if (option) {
                event.setChaine("OptionActive");
            } else {
                event.setChaine("OptionInactive");
            }
            notifierModification(event);
        }
        vueOptionActive = option;
        updatePanels();
    }

    /**
     * Active la vue de la liste et qui avertie les observateurs
     * @param option
     */
    public void setVueOption(VueInterne option) {
        vueOption = option.getPanel();
    }

    /**
     *
     * @param modele
     * @return
     */
    public static FramePrincipal getInstance(Modele modele) {
        if (instance == null) {
            instance = new FramePrincipal(modele);
        }
        return instance;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void ajouterObservateur(Observateur observateur) {
        listeObservateurs.add(observateur);
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void removeObservateur(Observateur observateur) {
        listeObservateurs.remove(observateur);
    }

    /**
     *
     * @param event
     */
    @Override
    public void notifierModification(ModificationEvent event) {
        for (Observateur ob : listeObservateurs) {
            ob.miseAjour(event);
        }
    }
}
