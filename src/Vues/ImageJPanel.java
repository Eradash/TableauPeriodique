package Vues;

import Model.Modele;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel qui permet d'afficher une image. Elle contient l'image et l'affiche...
 */
class ImageJPanel extends JPanel {
    

    private BufferedImage image;
    public static enum Type { ICONE, IMAGE, CONFIG };
    private boolean icone = false;
    private Dimension dim;
    private int noImg;
    private Modele modele;
    
    public ImageJPanel(LayoutManager b, Modele modele) {
        super(b);
        this.modele = modele;
    }
    
    public ImageJPanel(boolean isbd, Modele modele) {
        super(isbd);
        this.modele = modele;
    }
    
    public ImageJPanel(LayoutManager mn, boolean bds, Modele modele) {
        super(mn, bds);
        this.modele = modele;
    }
    
    public ImageJPanel(int img, Modele modele) {
        super();
        this.modele = modele;
        image = modele.getGr().getAutre(img);
        this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }
    public ImageJPanel(int img, Type tt, Modele modele){
        super();
        this.modele = modele;
        switch(tt){
            case ICONE: image = modele.getGr().getIcone(img);
                        noImg = img;
                        icone = true;
                break;
            case IMAGE: image = modele.getGr().getImage(img);
                break;
            case CONFIG: image = modele.getGr().getConfiguration(img);                       
                break;
                
        }
        this.dim = new Dimension(image.getWidth(null), image.getHeight(null));
        this.setPreferredSize(this.dim);
    }
    
    public void setDimension(Dimension d) {
        this.dim = d;
        this.setPreferredSize(dim);
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D)g;
        gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);       
         
        if(icone == true){
            BufferedImage imageFond =  modele.getGr().getFond(modele.getGe().getItemOfElement(noImg,"Caracteristique"));           
            gg.drawImage(imageFond, 0, 0, this.getWidth(),this.getHeight(),null);
        }
        gg.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);        
    }
}