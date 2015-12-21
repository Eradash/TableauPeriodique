/*

 */
package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observateur;
import Model.ModelVue;
import Model.Modele;
import Model.VueInterne;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Vue de l'atome Affiche toute les information spécifique d'un atome
 * des boutons permettent de changer d'un atome à un autre
 */
public class VueAtome extends ModelVue implements VueInterne {

    /**
     *
     */
    public int element = 0;
    private JPanel panel;
    private final Model.GestionRessource gr;
    private final Model.GestionElement ge;
    private final Font borderFont;

    /**
     *
     * @param modele
     * @param _numero
     */
    public VueAtome(Modele modele, int _numero) {
        super(modele);
        element = _numero;
        gr = getModele().getGr();
        ge = getModele().getGe();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(FramePrincipal.getInstance(getModele()).getWidth(), FramePrincipal.getInstance(getModele()).getHeight()));
        borderFont = new Font("Verdana", Font.BOLD, 17);
        panelGeneral();

    }
    
        
    /**
     *
     */
    public void panelGeneral() {
        
        ImageJPanel image = new ImageJPanel(element, ImageJPanel.Type.IMAGE, getModele());
    
        ImageJPanel background = new ImageJPanel(6, getModele());
        background.setPreferredSize(panel.getPreferredSize());
        background.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        background.add(panel1(), gbc);
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        background.add(panel3(), gbc);
        gbc.gridy = 6;
        gbc.gridheight = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        background.add(panel4(), gbc);
        gbc.gridy = 8;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        background.add(bouton(), gbc);
        panel.add(background);
    }
    
    private JPanel panel1(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(getModele().getGe().getItemOfElement(element, "Nom"));
        label.setFont(new Font("Helvetica", Font.PLAIN, 50));
        label.setForeground(Color.WHITE); 
        panel.setOpaque(false);
        panel.add(new ImageJPanel(element, ImageJPanel.Type.ICONE, getModele()));
        panel.add(label);
        ImageJPanel image = new ImageJPanel(element, ImageJPanel.Type.IMAGE, getModele());
        image.setDimension(new Dimension(panel.getX()/2, panel.getY()/2));
        panel.add(image);
        return panel;
    }
    
    private JPanel panel2(){
        ImageJPanel panel = new ImageJPanel(element, ImageJPanel.Type.IMAGE, getModele());
        panel.setDimension(new Dimension(panel.getX()/2, panel.getY()/2));
        return panel;
    }
    
    private JPanel panel3(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(panelInfo());
        panel.add(panelPhys());
        panel.add(panelAtom());
        panel.add(new ImageJPanel(element, ImageJPanel.Type.CONFIG, getModele()));
        return panel;
    }
    
    private JPanel panel4(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(panelDescription());
        return panel;
    }
    
    
    private JPanel bouton() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        if(element > 1){
            JButton bouton3 = new JButton(new Icone(gr.getAutre(10)));
            bouton3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ModificationEvent event = new ModificationEvent(ModificationEvent.Type.BOUTON);
                    event.setEntier(element-1);
                    notifierModification(event);
                }
            });
            panel.add(bouton3);
        }
        JButton bouton = new JButton(new Icone(gr.getAutre(9)));
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ModificationEvent mod = new ModificationEvent(ModificationEvent.Type.INFO);
                mod.setChaine("retour");
                notifierModification(mod);
            }
        });
        panel.add(bouton);
        if(element < 118){
            JButton bouton2 = new JButton(new Icone(gr.getAutre(11)));
            bouton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ModificationEvent event = new ModificationEvent(ModificationEvent.Type.BOUTON);
                    event.setEntier(element+1);
                    notifierModification(event);
                }
            });
            panel.add(bouton2);
        }
        return panel;
    }

    private JPanel panelInfo() {
        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 1), "Info générales", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, borderFont, Color.WHITE));
        panelInfo.setOpaque(false);
        panelInfo.setLayout(new GridLayout(8, 1));
        panelInfo.add(labelPanel("Nom: ", ge.getItemOfElement(element, "Nom")));
        panelInfo.add(labelPanel("Symbole: ", ge.getItemOfElement(element, "Symbole")));
        panelInfo.add(labelPanel("Numéro atomique: ", ge.getItemOfElement(element, "Numero_Atomique")));
        panelInfo.add(labelPanel("Masse atomique: ", ge.getItemOfElement(element, "Masse_Atomique")));
        panelInfo.add(labelPanel("Synthétique: ", nomAffichable(ge.getItemOfElement(element, "Synthetique"))));
        panelInfo.add(labelPanel("État: ", ge.getItemOfElement(element, "Etat")));
        panelInfo.add(labelPanel("Radioactif: ", nomAffichable(ge.getItemOfElement(element, "Radioactif"))));
        panelInfo.add(labelPanel("Famille: ", nomAffichable(ge.getItemOfElement(element, "Caracteristique"))));

        return panelInfo;
    }

    private JPanel panelPhys() {
        JPanel panelPhys = new JPanel();
        panelPhys.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 1), "Informations physiques", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, borderFont, Color.WHITE));
        panelPhys.setOpaque(false);
        panelPhys.setLayout(new GridLayout(8, 1));

        panelPhys.add(labelPanel("État d'oxydation: ", nomAffichable(ge.getItemOfElement(element, "Etat_Oxydation"))));
        panelPhys.add(labelPanel("Point d'ébullition: ", nomAffichable(ge.getItemOfElement(element, "Point_Ebullition"), "K")));
        panelPhys.add(labelPanel("Densité: ", nomAffichable(ge.getItemOfElement(element, "Densite"), "<html>g/cm<sup>3</sup></html>")));
        panelPhys.add(labelPanel("Capacité thermique: ", nomAffichable(ge.getItemOfElement(element, "Capacite_Thermique"), "J/gK")));
        panelPhys.add(labelPanel("Conductivité thermique: ", nomAffichable(ge.getItemOfElement(element, "Conductivite_Thermique"), "W/mK")));
        panelPhys.add(labelPanel("Point de fusion: ", nomAffichable(ge.getItemOfElement(element, "Point_Fusion"), "K")));
        panelPhys.add(labelPanel("Enthalpie de fusion: ", nomAffichable(ge.getItemOfElement(element, "Enthalpie_Fusion"))));
        panelPhys.add(labelPanel("Chaleur de vaporisation: ", nomAffichable(nomAffichable(ge.getItemOfElement(element, "Chaleur_Vaporisation"), "K"))));

        return panelPhys;
    }

    private JPanel panelAtom() {
        JPanel panelAtom = new JPanel();
        panelAtom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 1), "Informations atomiques", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, borderFont, Color.WHITE));
        panelAtom.setOpaque(false);
        panelAtom.setLayout(new GridLayout(6, 1));

        panelAtom.add(labelPanel("Configuration électronique: ", nomAffichable(ge.getItemOfElement(element, "Configuration_Electronique"))));
        panelAtom.add(labelPanel("Électronégativité: ", nomAffichable(ge.getItemOfElement(element, "Electronegativite"))));
        panelAtom.add(labelPanel("Rayon atomique: ", nomAffichable(ge.getItemOfElement(element, "Rayon_Atomique"), "Angströms")));
        panelAtom.add(labelPanel("Volume atomique: ", nomAffichable(ge.getItemOfElement(element, "Volume_Atomique"), "cm<html><sup>3</sup></html>/mole")));
        panelAtom.add(labelPanel("Potentiel d'ionisation: ", nomAffichable(ge.getItemOfElement(element, "Potentiel_Ionisation"))));
        panelAtom.add(labelPanel("Rayon covalent: ", nomAffichable(ge.getItemOfElement(element, "Rayon_Covalent"))));

        return panelAtom;
    }

    private JPanel panelDescription() {

        JPanel panelDescription = new JPanel();
        panelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 1), "Description", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, borderFont, Color.WHITE));
        panelDescription.setOpaque(false);
        panelDescription.setLayout(new GridLayout(2, 1));

        panelDescription.add(labelPanel(" ", nomAffichable(ge.getItemOfElement(element, "Description"))));
        panelDescription.add(labelPanel("Lien externe", "<html><a href=\"" + ge.getItemOfElement(element, "Lien") + "\" style=\"color: white;\">" + ge.getItemOfElement(element, "Lien") + "</a></html>"));

        return panelDescription;
    }

    private JPanel labelPanel(String string1, String string2) {
        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel(string1);
        Font font = new Font("Verdana", Font.BOLD, 15);
        label.setForeground(Color.WHITE);
        label.setFont(font);

        labelPanel.add(label);

        if (string2 != null && string2.startsWith("<html>")) {
            JEditorPane jedp = new JEditorPane();
            jedp.setEditable(false);
            jedp.setOpaque(false);
            jedp.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
            jedp.setText(string2);
            font = new Font("Verdana", Font.PLAIN, 15);
            jedp.setFont(font);
            jedp.setForeground(Color.WHITE);
            jedp.addHyperlinkListener(new HyperlinkListener() {

                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().browse(e.getURL().toURI());
                            } catch (IOException ex) {
                                Logger.getLogger(VueAtome.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (URISyntaxException ex) {
                                Logger.getLogger(VueAtome.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

            });

            labelPanel.add(jedp);
        } else {
            JLabel label2 = new JLabel("<html>" + string2 + "</html>");
            font = new Font("Verdana", Font.PLAIN, 15);
            label2.setFont(font);
            label2.setForeground(Color.WHITE);
            labelPanel.add(label2);
        }

        return labelPanel;
    }
    
    private String nomAffichable(String dat, String unit) {
        if (dat != null && unit != null) {
            if (dat.equals("null")) {
                dat = "Non disponible";
            }
            else {
                if (dat.equals("true")) {
                    dat = "oui";
                }
                else {
                    if (dat.equals("false")) {
                        dat = "non";
                    }
                    else {
                        dat = dat + " " + unit;
                    }
                }
            }

            return dat;
        }
        else {
            return "Non disponible";
        }
    }
    
    private String nomAffichable(String dat) {
        return nomAffichable(dat, "");
    }

    /**
     *
     * @param x
     */
    @Override
    public void visible(boolean x) {
        panel.setVisible(x);
    }

    /**
     *
     * @param i
     * @param ob
     */
    @Override
    public void ajouterListener(int i, Observateur ob) {

    }

    /**
     *
     * @return
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     *
     */
    @Override
    public void close() {
        FramePrincipal.getInstance(getModele()).remove(panel);
    }

    /**
     *
     */
    @Override
    public void display() {
        FramePrincipal.getInstance(getModele()).setVuePrincipale(this);
    }
}
