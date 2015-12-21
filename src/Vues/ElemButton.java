package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import Model.Elem;
import Model.Modele;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 */
public class ElemButton extends JButton implements Observable {

    private Dimension dimensions;
    private boolean mousePressed;
    private boolean mouseInside;
    private final BufferedImage elemImage;
    private final BufferedImage normImg;
    private BufferedImage overImg;
    private final BufferedImage pressedImg;
    private Shape shape;
    private Modele modele;

    /**
     *
     */
    public static final int BUTTON_WIDTH = 76,

    /**
     *
     */
    BUTTON_HEIGHT = 76;

    private final String ligne, colone;

    private final int noAtomique;

    private final ArrayList<Observateur> obs = new ArrayList<>();

    /**
     *
     * @param el
     * @param imgNorm
     * @param modele
     */
    public ElemButton(Elem el, Modele modele) {
        super();
        this.modele = modele;
        noAtomique = Integer.parseInt(el.getProp("Numero_Atomique"));

        this.elemImage = modele.getGr().getIcone(noAtomique);

        // Faut arranger ça, avoir de quoi pour déterminer la couleur selon le numéro atomique ?
        this.ligne = el.get("Ligne");
        this.colone = el.get("Colonne");
        int lig, col;
        lig = Integer.parseInt(this.ligne);
        col = Integer.parseInt(this.colone);

        if (el.getProp("Caracteristique") != null) {
            this.normImg = modele.getGr().getFond(el.getProp("Caracteristique"));
            this.pressedImg = modele.getGr().getFondPese(el.getProp("Caracteristique"));
        } else {
            this.normImg = modele.getGr().getFond("vert");
            this.pressedImg = modele.getGr().getFondPese("vert");
        }

        enableInputMethods(true);

        dimensions = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        setSize(dimensions);
        setPreferredSize(dimensions);
        setMaximumSize(dimensions);
        setMinimumSize(dimensions);
        setMargin(new Insets(0, 0, 0, 0));
        setFocusable(true);
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);

        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModificationEvent event = new ModificationEvent(ModificationEvent.Type.BOUTON);
                event.setEntier(noAtomique);
                notifierModification(event);
            }
        });
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setDimension(int x, int y) {
        dimensions = new Dimension(x, y);
        setSize(dimensions);
        setPreferredSize(dimensions);
        setMaximumSize(dimensions);
        setMinimumSize(dimensions);
        repaint();
    }

    /**
     *
     * @return
     */
    public String getLigne() {
        return ligne;
    }

    /**
     *
     * @return
     */
    public String getColone() {
        return colone;
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        BufferedImage toShow = null;
        Graphics2D g2d = (Graphics2D) (g);
        float opacity = 0f;

        // On définit quelle image à afficher selon la souris
        if (getModel().isRollover()) {
            if (overImg != null) {
                toShow = overImg;
                opacity = 0.3f;

            } else {
                toShow = normImg;
                opacity = 0.3f;
            }
        } else {
            toShow = normImg;
            opacity = 0f;
        }

        if (getModel().isArmed()) {
            if (pressedImg != null) {
                toShow = pressedImg;
                opacity = 0f;
            }
        }

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.drawImage(toShow, 0, 0, (int) dimensions.getWidth(), (int) dimensions.getHeight(), null);

        // Image de l'élément
        g2d.drawImage(elemImage, 0, 0, (int) dimensions.getWidth(), (int) dimensions.getHeight(), null);

        Rectangle2D rec = new Rectangle2D.Double(0, 0, dimensions.getWidth(), dimensions.getHeight());
        g2d.setPaint(new Color(1, 1, 1, opacity));
        g2d.fill(rec);
    }

    @Override
    public void paintBorder(Graphics g) {
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Rectangle2D.Double(0, 0, dimensions.getWidth(), dimensions.getHeight());
        }

        return shape.contains(x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return dimensions;
    }

    @Override
    public Dimension getMinimumSize() {
        return dimensions;
    }

    @Override
    public Dimension getMaximumSize() {
        return dimensions;
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void ajouterObservateur(Observateur observateur) {
        obs.add(observateur);
    }

    /**
     *
     * @param observateur
     */
    @Override
    public void removeObservateur(Observateur observateur) {
        obs.remove(observateur);
    }

    /**
     *
     * @param event
     */
    @Override
    public void notifierModification(ModificationEvent event) {
        for (Observateur ob : obs) {
            ob.miseAjour(event);
        }
    }

}
