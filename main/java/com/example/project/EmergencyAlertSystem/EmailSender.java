package com.example.project.EmergencyAlertSystem;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    // method to send an email with the specified recipient, subject, and message body
    public static void sendEmail(String to, String subject, String messageText) {

        // email address of the sender (must be a valid Gmail address)
        final String fromEmail = "";

        // app-specific password for the sender's Gmail account (not the regular password)
        final String appPassword = "";

        // smtp server configuration properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS for secure communication
        props.put("mail.smtp.host", "smtp.gmail.com"); // smtp server address for Gmail
        props.put("mail.smtp.port", "587"); // smtp port for Gmail

        // create a session with the smtp server using the provided properties and authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            // override the getPasswordAuthentication method to provide the sender's credentials
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            // create a new email message
            Message message = new MimeMessage(session);

            // set the sender's email address
            message.setFrom(new InternetAddress(fromEmail));

            // set the recipient's email address (can handle multiple recipients if needed)
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );

            // set the subject of the email
            message.setSubject(subject);

            // set the body text of the email
            message.setText(messageText);

            // send the email using the Transport class
            Transport.send(message);

            // log a success message to the console
            System.out.println("✅ Email sent successfully to " + to);
        } catch (MessagingException e) {
            // print the stack trace if an error occurs while sending the email
            e.printStackTrace();
        }
    }
}
