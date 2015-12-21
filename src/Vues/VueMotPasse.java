/*

 */
package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Model.ModelVue;
import Model.Modele;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Boite de dialoque qui demande un mot de passe pour accéder à l'administration
 */
public class VueMotPasse extends ModelVue implements Observable {

    private final int x = 300;
    private final int y = 150;
    private final JDialog dialog;
    private JPasswordField pass;
    private JButton okBouton, cancelBouton;

    /**
     *
     * @param modele
     */
    public VueMotPasse(Modele modele) {
        super(modele);
        dialog = new JDialog();
        dialog.setSize(x, y);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - x/2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - y/2);
        creer();
    }

    /**
     *
     */
    public void creer() {

        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Entrer le mot de passe: "));
        JPanel panel2 = new JPanel();
        panel2.add(pass = new JPasswordField(20));
        JPanel pa = new JPanel();
        pa.add(panel1);
        pa.add(panel2);
        pan.add(pa, BorderLayout.CENTER);

        bouton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okBouton);
        buttonPanel.add(cancelBouton);
        pan.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(pan);
    }

    private void bouton() {
        okBouton = new JButton("OK");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String mot = new String(pass.getPassword());
                if (mot.equals(getModele().getMotPasse())) {
                    ModificationEvent mod = new ModificationEvent(ModificationEvent.Type.INFO);
                    mod.setChaine("motPasse");
                    notifierModification(mod);
                    dialog.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Mauvais mot de passe", "Attention", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dialog.setVisible(false);
            }
        });
    }
 
    /**
     *
     */
    @Override
    public void display() {
        dialog.setVisible(true);
    }

    /**
     *
     */
    @Override
    public void close() {
        dialog.setVisible(false);
    }

    /**
     *
     * @param x
     */
    @Override
    public void visible(boolean x) {
        dialog.setVisible(x);
    }
}
