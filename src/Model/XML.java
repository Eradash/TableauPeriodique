package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/** 
 * Classe permettant de lire le fichier du tableau périodique. 
 * <p>Récupère toutes 
 * les informations à insérer dans le tableau. Permet aussi la réécriture du fichier 
 * pour actualiser les informations. Les informations sont stockées dans un hashmap 
 * et les éléments dans une ArrayList.
 * </p>
 * @author Marc-Antoine
 */
public class XML {

    /**
     * Liste des éléments
     */
    public static ArrayList<Elem> listeElem;

    /**
     *
     */
    public static HashMap<Integer, String> map;

    /**
     * Lis le fichier du tableau périodique.
     * @throws ParserConfigurationException
     * @throws Exception
     */
    public XML() throws ParserConfigurationException, Exception {
        try {
            listeElem = new ArrayList<>();
            initMapCarac();
            lireFichier();
        } catch (Exception ex) {
            System.err.println("Erreur dans le lecteur XML!");
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void lireFichier() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.parse(new InputSource("Tableau_Periodique.xml"));

        Element tabperio = doc.getDocumentElement();
        NodeList elements = tabperio.getElementsByTagName("Element");

        for (int i = 0; i < elements.getLength(); i++) {
            instancierElement((Element) elements.item(i));
        }   
    }

    private void initMapCarac() {

        map = new HashMap();

        map.put(1, "Nom");
        map.put(2, "Numero_Atomique");
        map.put(3, "Masse_Atomique");
        map.put(4, "Etat_Oxydation");
        map.put(5, "Point_Ebullition");
        map.put(6, "Symbole");
        map.put(7, "Densite");
        map.put(8, "Configuration_Electronique");
        map.put(9, "Electronegativite");
        map.put(10, "Rayon_Atomique");
        map.put(11, "Volume_Atomique");
        map.put(12, "Capacite_Thermique");
        map.put(13, "Potentiel_Ionisation");
        map.put(14, "Conductivite_Thermique");       
        map.put(15, "Radioactif");        
        map.put(16, "Etat");
        map.put(17, "Point_Fusion");
        map.put(18, "Enthalpie_Fusion");
        map.put(19, "Chaleur_Vaporisation");
        map.put(20, "Rayon_Covalent");
        map.put(21, "Synthetique");
        map.put(22, "Caracteristique");
        map.put(23, "Ligne");
        map.put(24, "Colonne");
        map.put(25, "Description");
        map.put(26, "Lien");
    }

    /**
     * creer les éléments du tableau
     * @param n éléments du XML
     */
    private static void instancierElement(Element n) {
        Elem elem = new Elem();

        for (int i = 1; i < map.size()+1; i++) {
            elem.setProp(map.get(i), getInformation(map.get(i),n));
        }
        listeElem.add(elem);
        
    }

    /**
     * 
     * @return ArrayList d'éléments
     */
    public ArrayList<Elem> getListeElements() {
        return listeElem;
    }

    /**
     * Réécris un fichier complet du tableau périodique. 
     * 
     * @param nouvelleListe
     */
    public void reecrire(ArrayList<Elem> liste) {
        try {
            
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

            Document document = domBuilder.newDocument();
            Element rootElement = document.createElement("Tableau_Periodique");
            document.appendChild(rootElement);


            for (Elem element : liste) {
                Element elem = document.createElement("Element");
                setCarac(elem, element, document);
                rootElement.appendChild(elem);
            }

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer aTransformer = transFactory.newTransformer();

            Source src = new DOMSource(document);
            Result dest = new StreamResult(new File("Tableau_Periodique.xml"));

            aTransformer.transform(src, dest);
        } catch (ParserConfigurationException | TransformerException | DOMException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recharge la liste des éléments dans la même ArrayList.
     */
    public void actualiser(){
        listeElem.clear();
        map.clear();       
        try {
            listeElem = new ArrayList<>();
            initMapCarac();
            lireFichier();
        } catch (Exception ex) {
            System.err.println("Erreur dans le lecteur XML!");
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void setCarac(Element elem, Elem e, Document d) {

        for (int i = 1; i < (map.size()+1); i++) {
            elem.appendChild(addCarac(map.get(i), e.getProp(map.get(i)), d));
        }
    }

    private static Element addCarac(String nom, String information, Document d) {
        Element info = d.createElement(nom);
        info.appendChild(d.createTextNode(information+""));
        return info;
    }

    /**
     *
     * @param info
     * @param n
     * @return
     */
    private static String getInformation(String info, Element n) {
        try {
            NodeList information = n.getElementsByTagName(info);
            if (information.getLength() != 0) {
                return information.item(0).getFirstChild().getNodeValue();
            }
            return null;
        } catch (DOMException e) {
            e.getCause();
            return null;
        }
    }
}