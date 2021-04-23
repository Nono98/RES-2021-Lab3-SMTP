package heig.res.config;

import heig.res.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {

    String getSmtpServerAddress();

    int getSmtpServerPort();

    List<Person> getVictims();

    List<Person> getCc();

    List<String> getMessages();

    int getNumberOfGroups();
}
