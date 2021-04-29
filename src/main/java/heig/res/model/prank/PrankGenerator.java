package heig.res.model.prank;

import heig.res.config.IConfigurationManager;
import heig.res.model.mail.Group;
import heig.res.model.mail.Person;
import heig.res.smtp.SmtpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PrankGenerator {

    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;
        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        if((numberOfVictims / numberOfGroups) < 3) {
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("There are not enough victims !");
        }

        List<Group> groups = generateGroups(configurationManager.getVictims(), numberOfGroups);
        for(Group g : groups) {
            Prank prank = new Prank();
            List<Person> victims = g.getGroup();

            Collections.shuffle(victims);

            Person sender = victims.remove(0);
            prank.setSender(sender);
            prank.setTo(victims);
            prank.setCc(configurationManager.getCc());

            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }

        return pranks;
    }

    public List<Group> generateGroups(List<Person> victims, int numberOfGroups) {
        List<Group> groups = new ArrayList<>();
        List<Person> person = new ArrayList(victims);

        Collections.shuffle(person);
        for(int i = 0; i < numberOfGroups; ++i){
            Group group = new Group();
            groups.add(group);
        }

        int turn = 0;
        Group targetGroup;
        while(person.size() > 0) {
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person victim = person.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }

}
