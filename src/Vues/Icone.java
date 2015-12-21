/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vues;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

/**
 * Icon pour mettre sur certain boutons, qui contient une image
 */
public class Icone implements Icon {

    BufferedImage image;

    /**
     * constructeur
     * @param image image de l'icone
     */
    public Icone(BufferedImage image) {
        this.image = image;
    }

    /**
     *
     * @param cmpnt
     * @param grphcs
     * @param i
     * @param i1
     */
    @Override
    public void paintIcon(Component cmpnt, Graphics grphcs, int i, int i1) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.drawImage(image, i, i1, cmpnt);
    }

    /**
     *
     * @return la taille de l'icone
     */
    @Override
    public int getIconWidth() {
        return image.getWidth();
    }

    /**
     *
     * @return la taille de l'icone
     */
    @Override
    public int getIconHeight() {
        return image.getHeight();
    }
}
