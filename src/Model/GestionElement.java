/*

 */
package Model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permet de gérer la liste des éléments et les éléments eux-mêmes.
 * Entre autre, trier une liste (ArrayList) selon une propriété, récupérer un élément ou la 
 * liste au complet, et aussi actualiser la liste.
 * @author Marc-Antoine
 */
public class GestionElement {

    private static GestionElement instance = null;
    private XML lecteurXML = null;
    private final ArrayList<Elem> listeElement;

    private GestionElement() {
        try {
            lecteurXML = new XML();           
        } catch (Exception ex) {
            System.err.println("Lecteur XML non opérationel");
            ex.getCause();
            Logger.getLogger(GestionElement.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeElement = lecteurXML.getListeElements();
        trierListe("Numero_Atomique");
    }

    /**
     *
     * @return instance
     */
    protected static GestionElement getInstance() { //Singleton

        if (instance == null) {
            instance = new GestionElement();
        }
        return instance;
    }

    /**
     * Récupérer la liste des éléments
     *
     * @return un ArrayList d'Elem
     */
    public ArrayList<Elem> getListeElement() {
        return listeElement;
    }

    /**
     * pour récupérer un élément de la liste, par son numéro
     * @param elementNumber numéro de l'élément
     * @return l'élément
     */
    public Elem getElement(Integer elementNumber) {
        for(int x = 0;x < 118;x++){
            if(listeElement.get(x).getProp("Numero_Atomique").equals(String.valueOf(elementNumber))){
                return listeElement.get(x);
            }
        }
        return listeElement.get(0);
    }
    
    /**
     * pour récupérer un élément de la liste, par son nom
     * @param elementNumber nom de l'élément
     * @return l'élément
     */
    public Elem getElement(String nomELement) {
        for(int x = 0;x < 118;x++){
            if(listeElement.get(x).getProp("Nom").equals(nomELement)){
                return listeElement.get(x);
            }
        }
        return listeElement.get(0);
    }

    /**
     *
     * @param elementNumber
     * numero atomique de l'élément
     * @param prop
     * propriété à récupérer
     * @return
     */
    public String getItemOfElement(Integer elementNumber, String prop) {
        return (String) listeElement.get(elementNumber-1).get(prop);
    }

    /**
     *
     * @param prop
     */
    public void trierListe(String prop) {

        Integer i, j, min;
        Elem temp;
        for (i = 0; i < listeElement.size(); i++) {
            min = i;
            for (j = i; j < listeElement.size(); j++) {
                if (Integer.valueOf(listeElement.get(min).getProp(prop)).compareTo(Integer.valueOf(listeElement.get(j).getProp(prop))) > 0) {
                    min = j;
                }
            }
            temp = listeElement.get(i);
            listeElement.set(i, listeElement.get(min));
            listeElement.set(min, temp);
        }       
    }
    
    public ArrayList<Elem> trierCroissant(){
        ArrayList<Elem> listeTempo = new ArrayList<Elem>(listeElement);
        Integer i, j, min;
        Elem temp;
        for (i = 0; i < listeTempo.size(); i++) {
            min = i;
            for (j = i; j < listeTempo.size(); j++) {
                if (listeElement.get(min).compareTo(listeTempo.get(j)) > 0) {
                    min = j;
                }
            }
            temp = listeTempo.get(i);
            listeTempo.set(i, listeTempo.get(min));
            listeTempo.set(min, temp);
        } 
        return listeTempo;
    }

    /**
     * Pour modifier une propriété d'un élément
     * @param prop propriété à modifier
     * @param valeur nouvelle valeur de la propriété
     * @param numero numéro de l'élément
     */
    
    public void modifierCarac(String prop,String valeur, int numero ){
        
        listeElement.get(numero).setProp(prop, valeur);
        reecrire(listeElement);
    }
    
    /**
     * Pour réécrire le XML
     * @param liste
     */
    public final void reecrire(ArrayList<Elem> liste) {
        lecteurXML.reecrire(liste);
    }
    
    /**
     * Pour actualiser les informations
     */
    public final void actualiser(){
        lecteurXML.actualiser();
    }

    /**
     * pour vider la liste.
     */
    public void terminer() {
        listeElement.clear();
    }
}
