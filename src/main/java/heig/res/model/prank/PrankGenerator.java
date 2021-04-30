/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : PrankGenerator.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe permettant de générer des pranks différents pour différents groupes
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.model.prank;

import heig.res.config.IConfigurationManager;
import heig.res.model.mail.Group;
import heig.res.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PrankGenerator {

    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private final IConfigurationManager configurationManager;

    /**
     * Constructeur
     * @param configurationManager - contient les éléments nécessaires à la génération du mail
     */
    public PrankGenerator(IConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    /**
     * Méthode générant les pranks à envoyer
     * @return - liste avec les pranks générés
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;
        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        // Test si il y a assez de victimes par rapport au nombre de groupes
        if((numberOfVictims / numberOfGroups) < 3) {
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("There are not enough victims !");
        }

        // Création des différents groupes d'envoi
        List<Group> groups = generateGroups(configurationManager.getVictims(), numberOfGroups);
        // Parcours des groupes pour la création du prank
        for(Group g : groups) {
            Prank prank = new Prank();
            List<Person> persons = g.getGroup();

            // Mélange des personnes du groupe pour que cela ne soit pas toujours le même qui envoie
            Collections.shuffle(persons);

            // Récupération de l'envoyeur du mail
            Person sender = persons.remove(0);
            prank.setSender(sender);
            // Configuration des gens qui vont recevoir le mail
            prank.setTo(persons);
            prank.setCc(configurationManager.getCc());

            // Récupération du message à envoyer par groupe
            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }

        return pranks;
    }

    /**
     * Méthode de génération des différents groupes
     * @param victims - liste des personnes
     * @param numberOfGroups - nombre de groupes à générer
     * @return - groupes générés
     */
    public List<Group> generateGroups(List<Person> victims, int numberOfGroups) {
        List<Group> groups = new ArrayList<>();
        List<Person> person = new ArrayList(victims);

        // Mélange des différentes personnes reçus en paramètre
        Collections.shuffle(person);

        // Création des groupes nécessaires
        for(int i = 0; i < numberOfGroups; ++i){
            Group group = new Group();
            groups.add(group);
        }

        int turn = 0;
        Group targetGroup;

        // Ajout des personnes dans les groupes
        while(person.size() > 0) {
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person victim = person.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }

}
