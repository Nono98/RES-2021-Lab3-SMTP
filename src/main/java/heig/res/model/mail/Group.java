/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : Group.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe objet gérant les groupes des pranks
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.model.mail;

import java.util.*;

public class Group {
    private final List<Person> group = new ArrayList<>();

    // Getter
    public List<Person> getGroup() {
        return new ArrayList<>(group);
    }

    /**
     * Méthode permettant d'ajouter une personne dans le groupe
     * @param p - personn à ajouter
     */
    public void addMember(Person p){
        group.add(p);
    }
}
