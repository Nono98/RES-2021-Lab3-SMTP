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
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

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

        socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        lineRead();

        writer.printf("EHLO localhost\r\n");
        LOG.info("Command: EHLO localhost");

        String line = reader.readLine();

        if(!line.startsWith("250")) {
            throw new IOException("SMTP Error: " + line);
        }

        while(line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        LOG.info("Command to add sender: MAIL FROM " + message.getFrom());
        // Récupération de l'envoyeur pour l'en-tête
        writer.write("MAIL FROM:");
        lineWrite(message.getFrom());

        lineRead();

        // Parcours de tous les destinataires principaux pour l'en-tête
        for(String to : message.getTo()) {
            LOG.info("Command to add victims: RCPT TO: " + to);
            writer.write("RCPT TO:");
            lineWrite(to);

            lineRead();
        }

        // Parcours de tous les destinataires en copie pour l'en-tête
        for(String cc : message.getCc()) {
            LOG.info("Command to add CC victims: RCPT TO: " + cc);
            writer.write("RCPT TO:");
            lineWrite(cc);

            lineRead();
        }

        LOG.info("Command to start writing message: DATA");
        // Récupération du contenu du mail
        lineWrite("DATA");
        lineRead();

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

        lineWrite("Subject: " + subject_send);

        // Inscris le corps du mail dans le contenu
        LOG.info("Message to send: " + message.getBody());
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();

        lineRead();

        LOG.info("Command to quit: QUIT");
        lineWrite("QUIT");

        reader.close();
        writer.close();
        socket.close();
    }

    private void lineWrite(String s) {
        writer.write(s);
        writer.write("\r\n");
        writer.flush();
    }

    private void lineRead() throws IOException {
        String line = reader.readLine();
        LOG.info(line);
    }
}
