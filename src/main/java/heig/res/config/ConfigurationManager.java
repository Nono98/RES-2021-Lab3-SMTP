/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : ConfigurationManager.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe permettant de récupérer les informations contenues dans les fichiers de configuration
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.config;

import heig.res.model.mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager {
    private String smtpServerAddress;
    private int smtpServerPort;
    private final List<Person> victims;
    private List<Person> cc;
    private final List<String> messages;
    private int numberOfGroups;

    /**
     * Constructeur
     */
    public ConfigurationManager() {
        loadProperties("../config/config.properties");
        messages = loadMessages("../config/messages.utf8");
        victims = loadVictims("../config/victims.utf8");

    }

    /**
     * Méthode permettant de charger les propriétés du serveur SMTP
     * @param file - fichier contenant les propriétés
     */
    public void loadProperties(String file){
        try (InputStream input = new FileInputStream(file)) {
            Properties prop = new Properties();
            prop.load(input);

            smtpServerAddress = prop.getProperty("smtpServerAddress");
            smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
            numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
            cc = new ArrayList<>();

            String witnesses = prop.getProperty("witnessesToCC");
            String[] witness = witnesses.split(",");

            for(String w : witness){
                cc.add(new Person(w));
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de charger les différents messages
     * @param file - fichier contenant les messages
     * @return - messages sous un format string
     */
    public List<String> loadMessages(String file) {
        List<String> result = new ArrayList<>();
        try (FileInputStream input = new FileInputStream(file)){
            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
            BufferedReader read = new BufferedReader(reader);
            String line;
            StringBuilder message = new StringBuilder();
            while((line = read.readLine()) != null) {
                while((line != null && !line.contains("=="))) {
                    message.append(line);
                    message.append("\n");
                    line = read.readLine();
                }
                result.add(message.toString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Méthode permettant de charger la liste des destinataires des mails
     * @param file - fichier cotenant les adresses mails
     * @return - liste des personnes
     */
    public List<Person> loadVictims(String file) {
        List<Person> result = new ArrayList<>();
        try (FileInputStream input = new FileInputStream(file)){
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader read = new BufferedReader(reader);
            String line;
            while((line = read.readLine()) != null) {
                result.add(new Person(line));
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    @Override
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    @Override
    public List<Person> getVictims() {
        return victims;
    }

    @Override
    public List<Person> getCc() {
        return cc;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }
}
