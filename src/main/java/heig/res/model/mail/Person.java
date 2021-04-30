/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : Person.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe objet pour les différentes personnes concernées par les pranks
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.model.mail;

public class Person {
    private final String adress;

    /**
     * Constructeur
     * @param adress - valeur de l'adresse mail
     */
    public Person(String adress) {
        this.adress = adress;
    }

    // Getter
    public String getAdress() {
        return adress;
    }

}
