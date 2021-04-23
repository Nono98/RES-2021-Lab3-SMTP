package heig.res.model.mail;

public class Person {
    private String prenom;
    private String nom;
    private String adresse;

    public Person(String adresse) {
        this.adresse = adresse;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

}
