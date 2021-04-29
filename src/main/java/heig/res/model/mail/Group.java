package heig.res.model.mail;

import java.util.*;

public class Group {
    private List<Person> group = new ArrayList<>();

    public List<Person> getGroup() {
        return new ArrayList<>(group);
    }

    public void setGroup(List<Person> personList){
        group.addAll(personList);
    }

    public void addMember(Person p){
        group.add(p);
    }
}
