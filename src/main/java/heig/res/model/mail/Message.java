package heig.res.model.mail;

public class Message {
    private String subject;
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String body;

    public Message(String subject, String from, String[] to, String[] cc, String[] bcc, String body) {
        this.subject = subject;
        this.from = from;
        this.to = to;
        this.cc = cc;
        //this.bcc = bcc;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String[] getCc() {
        return cc;
    }

   /* public String[] getBcc() {
        return bcc;
    }*/

    public String getBody() {
        return body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    /*public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }*/

    public void setBody(String body) {
        this.body = body;
    }
}
