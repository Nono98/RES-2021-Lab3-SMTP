package heig.res.smtp;

import heig.res.model.mail.Message;
import java.io.IOException;

public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
