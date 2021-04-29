package heig.res.model.prank;

import heig.res.model.mail.Group;
import heig.res.model.mail.Message;
import heig.res.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// Personne qui envoie
// Groupe
// message a envoyé
public class Prank {
    private String message;
    private Person sender;
    private List<Person> to = new ArrayList<>();
    private List<Person> cc = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public List<Person> getTo() {
        return to;
    }

    public void setTo(List<Person> to) {
        this.to = to;
    }

    public List<Person> getCc() {
        return cc;
    }

    public void setCc(List<Person> cc) {
        this.cc = cc;
    }

    public Message generateMailMessage() {
        Message message = new Message();

        String[] messageParts = this.message.split("\n", 2);

        message.setSubject(messageParts[0]);
        message.setBody(messageParts[1]);

        String[] victims = to.stream().map(p -> p.getAdresse()).collect(Collectors.toList()).toArray(new String[]{});
        String[] witnesses = cc.stream().map(p -> p.getAdresse()).collect(Collectors.toList()).toArray(new String[]{});

        message.setTo(victims);
        message.setCc(witnesses);
        message.setFrom(sender.getAdresse());

        return message;
    }


}
