package Vues;

import Controlleurs.ModificationEvent;
import Controlleurs.Observable;
import Controlleurs.Observateur;
import Model.Elem;
import Model.ModelVue;
import Model.Modele;
import Model.VueInterne;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


/**
 * Liste des éléments en ordre alphabétique  
 * contient des boutons qui, lorsqu'on les clique affiche la vue de cet atome, 
 * comme la vue tableau, mais en ordre alphabétique
 */
public class VueListe extends ModelVue implements VueInterne {

    private ImageJPanel panel;

    private final Map<Integer, Position> positionsMap = new HashMap<>();
    private final Map<Integer, BoutonListe> boutonsMap = new HashMap<>();

    private boolean init = false;

    private JScrollBar sc;
    private int defilement = 0;
    private int hauteurTotale;

    /**
     * @param modele Modèle à observer
     */
    public VueListe(Modele modele) {
        super(modele);
        init();
    }

    /**
     * Initialisation des composantes
     */
    private void init() {
        panel = new ImageJPanel(12, getModele());
        panel.setLayout(null);

        Toolkit tk = Toolkit.getDefaultToolkit();
        final Dimension d = tk.getScreenSize();

        panel.setSize(d.width / 10, d.height);

        updatePositions();
        hauteurTotale = positionsMap.get(117).getY();
        initialiserBoutons();
        init = true;

        FramePrincipal.getInstance(getModele()).setVueOption(this);

        panel.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = 10 * e.getWheelRotation();
                if (defilement - rotation < 0 && defilement - rotation > -hauteurTotale) {
                    defilement -= 10 * (e.getWheelRotation());
                    updateBoutons();

                }
                double t = (double) (-defilement) / (double) hauteurTotale;
                sc.setValue((int) (t * 100));
            }
        });

        panel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getX() > panel.getWidth()) {
                    close();
                }
            }
        });

        sc = new JScrollBar(JScrollBar.VERTICAL);
        sc.setBounds(0, 0, 20, panel.getSize().height - 30);
        sc.setValue(1);
        sc.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                double t = -e.getValue();
                defilement = (int) ((t / 100) * hauteurTotale);
                updateBoutons();
            }
        });

        panel.add(sc);

    }

    /**
     * Update les positions de base (points d'ancrage)
     */
    public void updatePositions() {
        Dimension d = panel.getSize();
        int x = 30;
        for (int i = 0; i < 118; i++) {
            double y = i * (d.getHeight() / 20) + 10;
            positionsMap.put(i, new Position(x, (int) y));
        }
        if (init) {
            updateBoutons();
        }
    }

    /**
     * Initialise les boutons
     */
    private void initialiserBoutons() {
        ArrayList<Elem> elements = getModele().getGe().trierCroissant();
        for (int i = 0; i < 118; i++) {
            BoutonListe bouton;
            bouton = new BoutonListe(elements.get(i));
            boutonsMap.put(i, bouton);
            panel.add(bouton);
        }
        updateBoutons();
    }

    /**
     * Update la position des boutons en fonction du défilement présent
     * (Possibilité de changer tout ça pour ajouter des effets)
     */
    public void updateBoutons() {
        for (int i = 0; i < 118; i++) {
            BoutonListe b = boutonsMap.get(i);
            Position p = positionsMap.get(i);
            b.setBounds(p.getX(), p.getY() + defilement, b.getWidth(), b.getHeight());
        }
        panel.repaint();
    }

    @Override
    public void display() {
        FramePrincipal.getInstance(getModele()).setVueOption(true);
    }

    @Override
    public void close() {
        FramePrincipal.getInstance(getModele()).setVueOption(false);
    }

    @Override
    public void visible(boolean x) {
        panel.setVisible(x);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void ajouterListener(int i, Observateur ob) {
        boutonsMap.get(i - 1).ajouterObservateur(ob);
    }

    
    /**
     * Classe qui remplace un JButton, pour qu'il puisse avoir nos propres paramètres facilement
     */
    private class BoutonListe extends JButton implements Observable {

        private final ArrayList<Observateur> obs = new ArrayList<>();
        private final Elem elem;

        public BoutonListe(Elem element) {
            elem = element;
            String texte = elem.get("Nom");
            setText(texte);
            initialisation();
        }

        public final void initialisation() {

            setSize(100, 35);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ModificationEvent event = new ModificationEvent(ModificationEvent.Type.BOUTON);
                    event.setEntier(Integer.parseInt(elem.get("Numero_Atomique")));
                    notifierModification(event);
                }
            });
        }

        @Override
        public void ajouterObservateur(Observateur observateur) {
            obs.add(observateur);
        }

        @Override
        public void removeObservateur(Observateur observateur) {
            obs.remove(observateur);
        }

        @Override
        public void notifierModification(ModificationEvent event) {
            for (Observateur ob : obs) {
                ob.miseAjour(event);
            }
        }
    }
}