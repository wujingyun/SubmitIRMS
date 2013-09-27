/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author WU JINGYUN
 */


@Stateless
@LocalBean
public class EmailSessionBean {

    String emailServerName = "smtp.gmail.com";

    public EmailSessionBean() {
    }

    public void emailInitialPassward(String toEmailAdress, String initialPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("resortcoral", "hehehehe");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("resortcoral@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAdress));
            message.setSubject("Initial Password");
            message.setText("Dear Customer,"
                    + "\nYour initial password is:" + initialPassword +
                    "\nPlease login to change your password.\n\n"
                    + "\nBest Regards,\nCoral Island.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
