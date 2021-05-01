/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : SmtpClient.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe permettant la mise en forme des mails avec les informations reçues et d'envoi des mails
 Remarque(s) : Implémente l'interface ISmtpClient
 -----------------------------------------------------------------------------------
*/
package heig.res.smtp;

import heig.res.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private final String smtpServerAddress;
    private final int smtpServerPort;

    /**
     * Constructeur
     * @param smtpServerAddress - addresse de connexion
     * @param smtpServerPort - port de connexion
     */
    public SmtpClient(String smtpServerAddress, int smtpServerPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        LOG.info(line);

        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        LOG.info(line);

        if(!line.startsWith("250")) {
            throw new IOException("SMTP Error: " + line);
        }

        while(line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        // Récupération de l'envoyeur pour l'en-tête
        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();

        line = reader.readLine();
        LOG.info(line);

        // Parcours de tous les destinataires principaux pour l'en-tête
        for(String to : message.getTo()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();

            line = reader.readLine();
            LOG.info(line);
        }

        // Parcours de tous les destinataires en copie pour l'en-tête
        for(String cc : message.getCc()) {
            writer.write("RCPT TO:");
            writer.write(cc);
            writer.write("\r\n");
            writer.flush();

            line = reader.readLine();
            LOG.info(line);
        }

        // Récupération du contenu du mail
        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("Content-Type: text/plain: charset=\"utf-8\"\r\n");

        // Inscris l'envoyeur dans le contenu
        writer.write("From: " + message.getFrom() + "\r\n");

        // Inscris les destinataires principaux dans le contenu
        writer.write("To: " + message.getTo()[0]);
        for(int i = 1; i < message.getTo().length; ++i){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write("\r\n");

        // Inscris les destinataires en copie dans le contenu
        writer.write("Cc: " + message.getCc()[0]);
        for(int i = 1; i < message.getCc().length; ++i){
            writer.write(", " + message.getCc()[i]);
        }
        writer.write("\r\n");

        // Inscris le sujet du mail dans le contenu
        // Encodage en base 64 afin de prendre en comptes les accents
        String subject_base64 = Base64.getEncoder().encodeToString(message.getSubject().getBytes());
        String subject_send = "=?utf-8?B?" + subject_base64 + "?=";

        writer.write("Subject: " + subject_send);
        writer.write("\r\n");
        writer.flush();

        // Inscris le corps du mail dans le contenu
        LOG.info(message.getBody());
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();

        line = reader.readLine();
        LOG.info(line);

        writer.write("QUIT\r\n");
        writer.flush();

        reader.close();
        writer.close();
        socket.close();
    }
}
