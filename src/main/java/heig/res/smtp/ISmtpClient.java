/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : ISmtpClient.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Interface du client smtp
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res.smtp;

import heig.res.model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {
    /**
     * Méthode mettant en forme les mails pour l'envoyer
     * @param message - message ayant été créé à envoyer
     * @throws IOException
     */
    void sendMessage(Message message) throws IOException;
}
