package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observateur;
import Model.Modele;
import Model.ModelVue;
import Model.VueInterne;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VueTableau extends ModelVue implements VueInterne {

    private boolean init = false;
    private final JFrame frame;
    private final ImageJPanel panel;
    private static final int VTAB_LARGEUR = 800, VTAB_HAUTEUR = 600;
    Map<Integer, Position> positionsMap = new HashMap<>();
    Map<Integer, ElemButton> boutonsMap = new HashMap<>();
    private int largeur_Case, hauteur_Case, espacement_X, espacement_Y, marge_Gauche, marge_Droite, marge_Haut, marge_Bas;
    private ImageJPanel legende, legende2, legende3;

    private PositionSourie sourie;

    /**
     * @param modele Modèle à observer
     */
    public VueTableau(Modele modele) {
        super(modele);
        frame = FramePrincipal.getInstance(modele);
        panel = new ImageJPanel(4, modele);
        init();
    }

    /**
     * Initialisation des composantes
     */
    private void init() {
        panel.setLayout(null);

        sourie = new PositionSourie();
        sourie.start();
        
        legende = new ImageJPanel(1, getModele());
        legende.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.2f));
        panel.add(legende);
        
        legende2 = new ImageJPanel(2, getModele());
        legende2.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.2f));
        panel.add(legende2);
        
        legende3 = new ImageJPanel(3, getModele());
        legende3.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.2f));
        panel.add(legende3);

        panel.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                updatePositions();
            }
        });

        FramePrincipal.getInstance(getModele()).ajouterObservateur(new Observateur() {

            @Override
            public void miseAjour(ModificationEvent event) {
                if (event.getType() == ModificationEvent.Type.INFO) {
                    switch (event.getChaine()) {
                        case "OptionActive":
                            sourie.stopper();
                            break;
                        case "OptionInactive":
                            sourie = new PositionSourie();
                            sourie.start();
                            break;
                    }
                }
            }
        });

        setDimensions(ElemButton.BUTTON_WIDTH, ElemButton.BUTTON_HEIGHT, 2, 2, 10, 10, 10, 10);

        updatePositions();
        updatePositions();
        initialiserBoutons();
        init = true;

        
        
    }


    /**
     * Permet de changes les préférences du tableau selon la largeur désirée, l'espacement, etc...
     * 
     * @param lc Largeur case
     * @param hc hauteur case
     * @param eX espacement en X
     * @param eY espacement en Y
     * @param mG marge de gauche
     * @param mD marge de droite
     * @param mH marge en haut
     * @param mB marge en bas
     */
    public void setDimensions(int lc, int hc, int eX, int eY, int mG, int mD, int mH, int mB) {
        largeur_Case = lc;
        hauteur_Case = hc;
        espacement_X = eX;
        espacement_Y = eY;
        marge_Gauche = mG;
        marge_Droite = mD;
        marge_Haut = mH;
        marge_Bas = mB;
    }

    /** 
     * Permet de calculer la taille optimale du panel
     * 
     * @param lc Largeur case
     * @param hc hauteur case
     * @param eX espacement en X
     * @param eY espacement en Y
     * @param mG marge de gauche
     * @param mD marge de droite
     * @param mH marge en haut
     * @param mB marge en bas
     */
    private Dimension setPreferedSize(int lc, int hc, int eX, int eY, int mG, int mD, int mH, int mB) {
        Dimension dim = new Dimension();

        int x = mG + mD + 18 * (lc + eX);
        int y = mH + mB + 9 * (hc + eY);

        dim.setSize(x, y);
        return dim;
    }

    /**
     * Changement de taille du panel, repositionnement de la position de base des boutons
     */
    private void updatePositions() {
        Dimension d = frame.getSize();
        Insets in = frame.getInsets();
        setPreferedSize(largeur_Case, hauteur_Case, espacement_X, espacement_Y, marge_Gauche, marge_Droite, marge_Haut, marge_Bas);
        int k = 1;
        for (int i = 0; i < 7; i++) {
            int y = marge_Haut + i * ((((int) d.getHeight() - marge_Bas - in.bottom) - marge_Haut - in.top) / 10) + in.top;
            for (int j = 0; j < 18; j++) {
                int x = marge_Gauche + j * ((((int) d.getWidth() - marge_Droite - in.right) - marge_Gauche - in.left) / 18) + in.left;
                positionsMap.put(k, new Position(x, y));
                k++;
            }
        }
        for (int i = 7; i < 9; i++) {
            int y = marge_Haut + i * ((((int) d.getHeight() - marge_Bas - in.bottom) - marge_Haut - in.top) / 10) + in.top + 20;
            for (int j = 0; j < 18; j++) {
                int x = marge_Gauche + j * ((((int) d.getWidth() - marge_Droite - in.right) - marge_Gauche - in.left) / 18) + in.left;
                positionsMap.put(k, new Position(x, y));
                k++;
            }
        }
        if (init) {
            updateBoutons(null);
        }
    }

    /**
     * Initialisation des boutons
     */
    private void initialiserBoutons() {
        for (int i = 0; i < 118; i++) {
            ElemButton bouton;
            bouton = new ElemButton(getModele().getGe().getElement(i), getModele());
            boutonsMap.put(i, bouton);
            panel.add(bouton);
        }
        updateBoutons(null);
    }

    /**
     * Changement de la position des boutons en fonction de l'emplacement de la sourie sur la fenêtre

     * @param sourie Position de la sourie
     */
    public void updateBoutons(Position sourie) {
        int x, x2, x3;
        int y, y2, y3;
        if (sourie != null) {
            x = sourie.getX();
            x2 = x - VTAB_LARGEUR / 2;
            y = sourie.getY();
            y2 = y - VTAB_HAUTEUR / 2;
            x2 = sourie.getX();
        } else {
            x = -10000;            
        }
        for (int k = 0; k < 118; k++) {
            try {
                ElemButton b = boutonsMap.get(k);
                int ligne = Integer.parseInt(b.getLigne());
                int colonne = Integer.parseInt(b.getColone());

                Position p = positionsMap.get((18 * (ligne - 1)) + colonne);
                double test = x - p.getX();
                double h = 15 * Math.exp(-((test * test) / 30000));
                b.setBounds(p.getX(), p.getY() - (int) h, largeur_Case, hauteur_Case);
            } catch (NullPointerException e) {
            }
        }
        updateLegendes();
        panel.repaint();
    }
    
    /**
     * Update la position des légendes (pour de futurs effets, ou des transitions)
     */
    public void updateLegendes(){
        Position p = positionsMap.get(21);
        legende.setBounds(p.getX() + 10, p.getY() -10,(int)(legende.getPreferredSize().width*0.5), (int)(legende.getPreferredSize().height*0.45));
        
        p = positionsMap.get(28);
        legende2.setBounds(p.getX() - 10, p.getY() -10,(int)(legende2.getPreferredSize().width * 0.9), (int)(legende2.getPreferredSize().height * 1.2));

        p = positionsMap.get(18*7 +1);
        legende3.setBounds(p.getX() + 10, p.getY(),(int)(legende3.getPreferredSize().width), (int)(legende3.getPreferredSize().height));

    
    
    }

    /**
     * Ajoute un listener à un bouton du tableau
     * 
     * @param numeroAtomique Numéro du bouton à sélectionner
     * @param ob Observateur à ajouter au bouton
     */
    @Override
    public void ajouterListener(int numeroAtomique, Observateur ob) {
        boutonsMap.get(numeroAtomique - 1).ajouterObservateur(ob);
    }

    /**
     * Ferme la vue
     */
    @Override
    public void close() {
        sourie.stopper();
        frame.remove(panel);
    }

    /**
     * Affiche la vue
     */
    @Override
    public void display() {
        sourie = new PositionSourie();
        sourie.start();
        FramePrincipal.getInstance(getModele()).setVuePrincipale(this);
    }

    /**
     * équivalent de setVisible
     * @param x True/False
     */
    @Override
    public void visible(boolean x) {
        panel.setVisible(x);

    }

    /**
     * Ajouter un observateur au panel
     * @param ob Observateur à ajouter
     */
    @Override
    public void ajouterObservateur(Observateur ob) {
        super.ajouterObservateur(ob);
        FramePrincipal.getInstance(getModele()).ajouterObservateur(ob);
    }

    /**
     * Retirer un observateur du panel
     * @param ob Observateur à retirer
     */
    @Override
    public void removeObservateur(Observateur ob) {
        super.removeObservateur(ob);
        FramePrincipal.getInstance(getModele()).removeObservateur(ob);
    }

    /**
     * Permet de chercher le panel de la vue (Interface Vue)
     * @return Jpanel Panel de la vue
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    
    /**
     * Thread qui vérifie la position de la sourie en continu, et qui update la position des boutons
     * (effets de sourie des boutons)
     */
    class PositionSourie extends Thread {

        Point p = new Point(0, 0);
        boolean ok;

        public PositionSourie() {
        }

        @Override
        public void run() {
            ok = true;
            p = new Point(0, 0);
            while (ok) {
                Point pTempo = MouseInfo.getPointerInfo().getLocation();
            if (p.getX() != pTempo.getX() || p.getY() != pTempo.getY()) {
                p = pTempo;
                updateBoutons(new Position(p.x, p.y));
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(FramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }

        public void stopper() {
            ok = false;
        }

    }
}

/**
 * Classe qui contient un x et un y...
 */
class Position {

    private int x, y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public Position(Position p) {
        x = p.x;
        y = p.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(Position p) {
        x = p.x;
        y = p.y;
    }

    public void setX(int _x) {
        x = _x;
    }

    public void setPosition(int _x, int _y) {
        x = _x;
        y = _y;
    }

    @Override
    public String toString() {
        return "Position: X = " + x + "   Y = " + y;
    }
}
