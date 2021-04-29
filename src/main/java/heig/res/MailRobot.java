package heig.res;

import heig.res.config.ConfigurationManager;
import heig.res.config.IConfigurationManager;
import heig.res.model.prank.Prank;
import heig.res.model.prank.PrankGenerator;
import heig.res.smtp.ISmtpClient;
import heig.res.smtp.SmtpClient;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MailRobot {
    public static void main(String[] args)
    {
        ConfigurationManager configurationManager = new ConfigurationManager();
        String address = configurationManager.getSmtpServerAddress();
        int port = configurationManager.getSmtpServerPort();
        SmtpClient smtpClient = new SmtpClient(address, port);

        try
        {
            PrankGenerator pg = new PrankGenerator(configurationManager);
            List<Prank> pranks = pg.generatePranks();
            for(Prank p : pranks) {
                System.out.println("OKFor");
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
