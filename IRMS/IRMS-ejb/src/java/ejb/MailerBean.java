/*
 * Copyright 2013 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Customer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
@Stateless
public class MailerBean {

    private static final Logger logger = Logger.getLogger(
            "MailerBean");
    @Resource(name = "mail/myExampleSession")
    private Session session;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    protected MemberSessionBeanLocal msb;

    @Asynchronous
    public Future<String> sendMessage(String email) {
        String status;
        String password;

        try {
            Message message = new MimeMessage(session);

            StringBuilder sb = new StringBuilder(8);
            for (int i = 0; i < 8; i++) {
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
            }
            password = sb.toString();
//newly added code
            msb.resetPassword(password, email);
            
            message.setFrom();
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            message.setSubject("Password Reset");
            message.setHeader("X-Mailer", "JavaMail");

            DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                    DateFormat.LONG,
                    DateFormat.SHORT);
            Date timeStamp = new Date();
            String messageBody = "Dear customer,\n"
                    + "Your new password is " + password + ".\nPlease log into your account using this password.\n"
                    + dateFormatter.format(timeStamp) + ".";
            message.setText(messageBody);
            message.setSentDate(timeStamp);
            Transport.send(message);
            status = "Sent";
            logger.log(Level.INFO, "Mail sent to {0}", email);
        } catch (MessagingException ex) {
            logger.severe("Error in sending message.");
            status = "Encountered an error: " + ex.getMessage();
            logger.severe(ex.getMessage());
        }

        return new AsyncResult<String>(status);
    }


    public void sendConfirmationMessage(String email) {
        System.out.println("nima");
        
        boolean flag = false;
        long customerId=0;
        System.out.println("confirmation");
        Query q = em.createQuery("SELECT c FROM Customer c");
        while (!flag) {
            for (Object o : q.getResultList()) {
                System.out.println("query entered");
                Customer cus = (Customer) o;
                if (cus.getEmail().equals(email)) {
                    flag = true;
                    customerId = cus.getCustomerId();
                }
            }
        }

        try {
            Message message = new MimeMessage(session);

            message.setFrom();
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            message.setSubject("Registration confirmation");
            message.setHeader("X-Mailer", "JavaMail");

            DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                    DateFormat.LONG,
                    DateFormat.SHORT);
            Date timeStamp = new Date();
            String messageBody = "Dear customer,\n"
                    + "Your account is registered successfully!\n"
                    + "Your membership ID is " + customerId + ".\n"
                    + dateFormatter.format(timeStamp) + ".";
            message.setText(messageBody);
            message.setSentDate(timeStamp);
            Transport.send(message);
            logger.log(Level.INFO, "Mail sent to {0}", email);
        } catch (MessagingException ex) {
            logger.severe("Error in sending message.");
            logger.severe(ex.getMessage());
        }
    }
}
