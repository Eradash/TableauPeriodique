package Model;

import java.util.HashMap;

/**
 * Classe de "Stockage" des informations d'un élément du tableau périodique. 
 */
public class Elem extends HashMap<String, String> implements Comparable {

    /**
     * constructeur
     */
    public Elem() {

        put("Nom", null);
        put("Numero_Atomique", null);
        put("Masse_Atomique", null);
        put("Etat_Oxydation", null);
        put("Point_Ebullition", null);
        put("Symbole", null);
        put("Densite", null);
        put("Configuration_Electronique", null);
        put("Electronegativite", null);
        put("Rayon_Atomique", null);
        put("Volume_Atomique", null);
        put("Capacite_Thermique", null);
        put("Potentiel_Ionisation", null);
        put("Conductivite_Thermique", null);        
        put("Radioactif", null);
        put("Etat", null);
        put("Point_Fusion", null);
        put("Enthalpie_Fusion", null);
        put("Chaleur_Vaporisation", null);
        put("Rayon_Covalent", null);
        put("Synthetique", null);
        put("Caracteristique", null);       
        put("Ligne", null);
        put("Colonne", null);
        put("Description", "ddddd");  
        put("Lien", null);
    }

    /**
     * Accesseur d'une propriété
     * @param propriete la propriété souhaitée
     * @return l'information de la propriété
     */
    
    public String getProp(String propriete) {
        return this.get((String)(propriete));
    }

    /**
     * Permet de modifier une propriété
     * @param propriete la propriété à modifier
     * @param valeur la nouvelle valeur de la propriétée à changer
     */
    public void setProp(String propriete, String valeur) {
        put(propriete, valeur);
    }
    
    /**
     * compare les deux nom des éléments.
     * @param o objet à comparer
     * @return int voir compareTo d'un String
     */
    @Override
    public int compareTo(Object o) {
        Elem e = (Elem)o;
        String e1 = this.getProp("Nom");
        String e2 = e.getProp("Nom");
        return e1.compareToIgnoreCase(e2);
    }
    
    @Override
    public String toString(){
        return getProp("Numero_Atomique");
    }
}
