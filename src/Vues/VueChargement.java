package Vues;

import Controlleurs.ModificationEvent;
import Model.GestionRessource;
import Controlleurs.Observateur;
import Model.Modele;
import Model.ModelVue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

/**
 * Vue qui indique la progression du chargement des images
 */
public class VueChargement extends ModelVue{

    public static final int LARGEUR = 300,HAUTEUR = 150;
    private JFrame frame;
    private JPanel princPan;
    private ImageJPanel imgPanel;
    private JLabel lblCharge;
    private JProgressBar jprProg;

    /**
     * Constructeur
     * @param modele Le model à Observer
     */
    public VueChargement(Modele modele) {
        super(modele);
        initialiserInterface();
        ajouterComposants();
        display();
    }

    /**
     * Initialisation des composantes
     */
    public final void initialiserInterface() {
        UIManager.put("ProgressBar.background", Color.black);
        UIManager.put("ProgressBar.foreground", Color.white);
        UIManager.put("ProgressBar.selectionBackground", Color.white);
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        frame = new JFrame();
        frame.setTitle("Chargement");
        frame.setUndecorated(true);
        frame.setSize(LARGEUR, HAUTEUR);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.black);
        frame.setIconImage(getModele().getGr().getAutre(8));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    /**
     * Ajout des composantes dans la fenêtre
     */
    public final void ajouterComposants() {
        princPan = new JPanel();
        princPan.setBackground(Color.black);
        frame.setContentPane(princPan);
        princPan.setLayout(new BoxLayout(princPan, BoxLayout.PAGE_AXIS));
        imgPanel = new ImageJPanel(5, getModele());
        imgPanel.setBackground(Color.black);
        princPan.add(imgPanel);

        lblCharge = new JLabel("Chargement...");
        lblCharge.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCharge.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblCharge.setForeground(Color.white);
        imgPanel.add(lblCharge);

        jprProg = new JProgressBar();
        jprProg.setMaximum(GestionRessource.NOMBRE_TOTAL_IMAGES - 1);
        jprProg.setValue(0);
        jprProg.setStringPainted(true);
        jprProg.setBorderPainted(false);
        princPan.add(jprProg);

        getModele().getGr().ajouterObservateur(new Observateur() {
            @Override
            public void miseAjour(ModificationEvent event) {
                if (event.getType() == ModificationEvent.Type.IMG) {
                    jprProg.setValue(event.getEntier());
                    jprProg.setString(event.getChaine());
                    jprProg.repaint();
                    if (event.getEntier() >= (GestionRessource.NOMBRE_TOTAL_IMAGES)) {
                        close();
                    }
                }
            }

        });
    }

    /**
     * Permet d'obtenir la barre de progression
     * @return JProgressBar La barre de progression
     */
    public JProgressBar getJprProg() {
        return jprProg;
    }

    /**
     * Permet de modifier la barre de progression
     * @param jprProg La nouvelle barre de progression
     */
    public void setJprProg(JProgressBar jprProg) {
        this.jprProg = jprProg;
    }

    /**
     * Permet d'enregistrer une valeur à la barre de progression
     * @param v La nouvelle valeur
     */
    public void setPValue(int v) {
        jprProg.setValue(v);
        jprProg.repaint();
    }

    /**
     * Permet de modifier le message affiché dans la barre de progression
     * @param s Le nouveau message
     */
    public void setPString(String s) {
        jprProg.setString(s);
        jprProg.repaint();
    }

    /**
     * ferme la vue
     */
    @Override
    public void close() {
        ModificationEvent mod = new ModificationEvent(ModificationEvent.Type.INFO);
        mod.setChaine("chargement");
        notifierModification(mod);
        frame.setVisible(false);
    }

    /**
     * affiche la vue
     */
    @Override
    public final void display() {
        frame.setVisible(true);
    }

    /**
     * équivalent du SetVisible()
     * @param x
     */
    @Override
    public void visible(boolean x) {
        frame.setVisible(x);
    }    
}