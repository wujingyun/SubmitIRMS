/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
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
    
    
    

       public void emailRestaurantResConfirm(String contactInfo, String customerName, String fbRName,Date attendDate, String selectPeriod ) {
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
                    InternetAddress.parse(contactInfo));
            message.setSubject("Initial Password");
            message.setText("Dear "+customerName + " :"
                     + "\n"
                    + "\nThank you for your reservation.  "
                    + "\nYou have successfully make the reservation for Restaurant " + fbRName +
                    "\n Date:  "+attendDate+
                    "\n Time slot: "+selectPeriod
                    + "\n\nThank you .\n\n"
                    + "\nBest Regards,\nCoral Island.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
       
       
          public void sendMarketingEmail(List<String> arketingEmailList, String marketingTitle,String marketingMsg) {
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

         for (int i=0;i<arketingEmailList.size();i++){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("resortcoral@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(arketingEmailList.get(i)));
            message.setSubject("Coral Island Marketing");
            message.setText("Dear Customer :"
                     + "\n"
                    + "\nThis is an Marketing email from Coral Island Hotel.  "
                    + "\n " + marketingMsg 
                    + "\n\nThank you .\n\n"
                    + "\nBest Regards,\nCoral Island.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
          }
          
          
          
       public void emailAttractionConfirm(String contactInfo, String confirmmessage, String attraction) {
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
                    InternetAddress.parse(contactInfo));
            message.setSubject("Initial Password");
            message.setText("Dear Customer"  
                     + "\n"
                    + "\nThank you for your reservation.  "
                    + "\nYou have successfully make the reservation for Attraction " + attraction 
                     
                    + "\n\nThank you .\n\n"
                    + "\nBest Regards,\nCoral Island.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
       
       
       
       
       
          public void emailShowConfirm(String contactInfo, String confirmmessage, String show) {
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
                    InternetAddress.parse(contactInfo));
            message.setSubject("Initial Password");
            message.setText("Dear Customer"  
                     + "\n"
                    + "\nThank you for your reservation.  "
                    + "\nYou have successfully make the reservation for show:  " + show 
                     
                    + "\n\nThank you .\n\n"
                    + "\nBest Regards,\nCoral Island.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
       
       
          
          
          
       
}
