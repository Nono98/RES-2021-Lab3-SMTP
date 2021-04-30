/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : IConfigurationManager.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Interface de la configuration du mail
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.config;

import heig.res.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {

    // Getters
    String getSmtpServerAddress();

    int getSmtpServerPort();

    List<Person> getVictims();

    List<Person> getCc();

    List<String> getMessages();

    int getNumberOfGroups();
}
