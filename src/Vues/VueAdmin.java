package Vues;


import Controlleurs.ModificationEvent;
import Controlleurs.Observateur;
import Model.ModelVue;
import Model.Modele;
import Model.VueInterne;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Vue de l'administration. Elle permet de sélectionner un éléments et d'ajouter des informations
 * sur lui. Fait en sorte de réécrire le XML a la fin du projet avec les nouvelles informations
 */
public class VueAdmin extends ModelVue implements VueInterne {


    private final JPanel panel;
    private JTextArea text;
    private JComboBox<String> choixElement;
    private JButton confirmer, deconnection;
    private Font font;

    /**
     *
     * @param modele
     */
    public VueAdmin(Modele modele) {
        
        super(modele);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(FramePrincipal.getInstance(getModele()).getWidth(), FramePrincipal.getInstance(getModele()).getHeight()));
        font = new Font("Verdana", Font.BOLD, 20);
        
        ImageJPanel background = new ImageJPanel(7, getModele());
        background.setPreferredSize(panel.getPreferredSize());
        background.setLayout(new GridBagLayout());
        panel.add(background, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        background.add(choixElement());
        gbc.gridy = 1;
        background.add(scrollText(), gbc);
        gbc.gridy = 2;
        background.add(bouton(), gbc);
    }
    
    private JPanel scrollText() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(null, "Informations à ajouter:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font, Color.WHITE));
        text = new JTextArea(5,50);
        JScrollPane scrollPane = new JScrollPane(text);
        panel.add(scrollPane);
        return panel;
    }
    
    private JPanel choixElement(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(2,1));
        choixElement = new JComboBox(TableauElement());
        JLabel label = new JLabel("Choisir un élément");
        Font font = new Font("Verdana", Font.BOLD, 20);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.setOpaque(false);
        panel.add(label);
        panel.add(choixElement);
        return panel;
    }
    
    /**
     *
     * @return
     */
    public JPanel bouton(){
        JPanel panel = new JPanel();
        confirmer = new JButton(new Icone(getModele().getGr().getAutre(14)));
        confirmer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!(choixElement.getSelectedItem().equals(""))){
                    
                        ModificationEvent event = new ModificationEvent(ModificationEvent.Type.ADMIN);
                        event.setNom((String)choixElement.getSelectedItem());
                        
                        if(text.getText() == null || text.getText().equals("")){
                            event.setDescription("null");
                        }
                        else {
                            event.setDescription(text.getText());
                        }
                        notifierModification(event);
                        JOptionPane.showMessageDialog(null, "Confirmation réussie :D", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Choisir un élément", "Confirmation", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deconnection = new JButton(new Icone(getModele().getGr().getAutre(13)));
        deconnection.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ModificationEvent event = new ModificationEvent(ModificationEvent.Type.INFO);
                event.setChaine("deconnection");
                notifierModification(event);
            }
        });
        panel.setOpaque(false);
        panel.add(confirmer);
        panel.add(deconnection);
        return panel;
    }
    
     /**
     * Un tableau contenant le nom des éléments
     *
     * @return un tableau d'Elem
     */
    public String[] TableauElement(){
        String[] tab = new String[119];   
        tab[0] = "";
        for(int x=1; x<119;x++){
            tab[x] = getModele().getGe().getListeElement().get(x-1).getProp("Nom");
        }
        return tab;
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

