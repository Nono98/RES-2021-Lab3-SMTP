/*
 -----------------------------------------------------------------------------------
 Laboratoire : RES - Laboratoire SMTP
 Fichier     : MailRobot.java
 Auteur(s)   : Robin Gaudin, Noémie Plancherel
 Date        : 02.05.2021
 But         : Classe principale, où tout est lancé
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/
package heig.res;

import heig.res.config.ConfigurationManager;
import heig.res.config.IConfigurationManager;
import heig.res.model.prank.Prank;
import heig.res.model.prank.PrankGenerator;
import heig.res.smtp.ISmtpClient;
import heig.res.smtp.SmtpClient;

import java.util.List;

public class MailRobot {
    public static void main(String[] args)
    {
        // Récupération de l'adresse et du port pour la connexion
        IConfigurationManager configurationManager = new ConfigurationManager();
        String address = configurationManager.getSmtpServerAddress();
        int port = configurationManager.getSmtpServerPort();
        ISmtpClient smtpClient = new SmtpClient(address, port);

        try
        {
            // Génération des pranks
            PrankGenerator pg = new PrankGenerator(configurationManager);
            List<Prank> pranks = pg.generatePranks();
            // Envoi du mail pour chaque prank
            for(Prank p : pranks) {
                smtpClient.sendMessage(p.generateMailMessage());
            }

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
