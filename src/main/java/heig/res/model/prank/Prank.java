/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : Prank.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe objet pour les pranks
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.model.prank;

import heig.res.model.mail.Message;
import heig.res.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private String message;
    private Person sender;
    private List<Person> to = new ArrayList<>();
    private List<Person> cc = new ArrayList<>();

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public void setTo(List<Person> to) {
        this.to = to;
    }

    public void setCc(List<Person> cc) {
        this.cc = cc;
    }

    /**
     * Méthode générant un objet message pour l'envoi du mail
     * @return - le message généré
     */
    public Message generateMailMessage() {
        Message message = new Message();

        // Split permettant de récupérer la première ligne du message qui contient le sujet
        String[] messageParts = this.message.split("\n", 2);

        message.setSubject(messageParts[0]);
        message.setBody(messageParts[1]);

        // Récupération des destinataires
        String[] victims = to.stream().map(Person::getAdress).collect(Collectors.toList()).toArray(new String[]{});
        String[] witnesses = cc.stream().map(Person::getAdress).collect(Collectors.toList()).toArray(new String[]{});

        message.setTo(victims);
        message.setCc(witnesses);
        message.setFrom(sender.getAdress());

        return message;
    }


}
