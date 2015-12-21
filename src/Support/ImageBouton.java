
package Support;

import Model.GestionElement;
import Model.Elem;
import Model.Modele;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Génère l'image des tuiles du tableau.
 * n'est plus utilisée, n'a été utilisé une fois pour générer les images et ne sert plus par la suite...
 */
public class ImageBouton {

    private GestionElement ge;
    private ArrayList<Elem> elems;
    private Modele modele;
    private Font fontSymbole, fontNum, fontNom;

    /**
     *
     * @param modele
     */
    public ImageBouton(Modele modele) {
        this.modele = modele;
        ge = modele.getGe();
        elems = ge.getListeElement();
        fontSymbole = new Font("Impact", Font.PLAIN, 40);
        fontNum = new Font("Impact", Font.PLAIN, 20);
        fontNom = new Font("Impact", Font.PLAIN, 15);
    }
    
    /**
     * pour redimensionner une image
     */
    public void redimensionner(){
        BufferedImage image, imagesortie;
        Image image2;
        
        
        for(int x =1;x<119;x++){
            try {
                image = ImageIO.read(new File( "Images\\"+x+".jpg"));
                image2 = image.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
                imagesortie = new BufferedImage(image2.getHeight(null), image2.getWidth(null), BufferedImage.TYPE_INT_RGB);
                imagesortie.getGraphics().drawImage(image2, 0, 0 , null);
                ImageIO.write(imagesortie, "jpg", new File(x+".jpg"));
            } catch (IOException e) {
            }
            
            
        }
    }

    /**
     * pour créer les images des boutons
     */
    public void creerImages() {

        BufferedImage image;


        for (int x = 0; x < elems.size(); x++) {
            try {
                image = ImageIO.read(new File("fond.png"));
            } catch (IOException e) {
                image = null;
            }

            
            System.out.print(elems.get(x).getProp("Synthetique"));
            if (elems.get(x).getProp("Synthetique").equals("true")) {
                System.out.print("yes");
                
                creerImage(image, Color.WHITE, new Dimension(image.getWidth(), image.getHeight()), elems.get(x));
            } else if ((elems.get(x).getProp("Etat")).equals("solide")) {
                creerImage(image, Color.BLACK, new Dimension(image.getWidth(), image.getHeight()), elems.get(x));
            } else if (elems.get(x).getProp("Etat").equals("gazeux")) {
                creerImage(image, Color.GRAY, new Dimension(image.getWidth(), image.getHeight()), elems.get(x));
            } else {
                creerImage(image, Color.LIGHT_GRAY, new Dimension(image.getWidth(), image.getHeight()), elems.get(x));
            }
        }
    }

    /**
     * pour créer les images des boutons
     * @param image image a modifier
     * @param color couleur du texte a ajouter
     * @param d dimension de l'image
     * @param elem élément du tableau que l'image représente
     */
    public void creerImage(BufferedImage image, Color color, Dimension d, Elem elem) {
        Graphics2D g2 = image.createGraphics();
        g2.setColor(color);
        FontRenderContext context;
        String symbole = elem.getProp("Symbole");
        String num = elem.getProp("Numero_Atomique");
        String nom = elem.getProp("Nom");

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        //Symbole
        g2.setFont(fontSymbole);
        context = g2.getFontRenderContext();
        Rectangle2D rectSymbole = fontSymbole.getStringBounds(symbole, context);
        g2.drawString(symbole, (int) ((d.getWidth() / 2) - (rectSymbole.getWidth() / 2) + 3), (int) ((d.getHeight() / 2) + (rectSymbole.getHeight() / 2)) - 8);

        //Numero
        g2.setFont(fontNum);
        context = g2.getFontRenderContext();
        Rectangle2D rectNum = fontNum.getStringBounds(num, context);
        g2.drawString(num, 8, (int) (rectNum.getHeight()));

        //Nom
        g2.setFont(fontNom);
        context = g2.getFontRenderContext();
        Rectangle2D rectNom = fontNom.getStringBounds(nom, context);
        g2.drawString(nom, (int) ((d.getWidth() / 2) - (rectNom.getWidth() / 2) + 3), (int) ((d.getHeight()) - (rectNom.getHeight()) + 5));
        g2.dispose();
        g2.clearRect(0, 0, image.getWidth(), image.getHeight());

        try {
            ImageIO.write(image, "png", new File("i" + num + ".png"));
        } catch (IOException e) {
        }
    }
}