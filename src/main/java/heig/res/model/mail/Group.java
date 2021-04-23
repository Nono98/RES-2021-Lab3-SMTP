package heig.res.model.mail;

import java.util.*;

public class Group {
    private List<Person> group = new ArrayList<>();

    public Group(List<Person> personList){
        group.addAll(personList);
    }

    public List<Person> getGroup() {
        return group;
    }

    public void addMember(Person p){
        group.add(p);
    }
}
