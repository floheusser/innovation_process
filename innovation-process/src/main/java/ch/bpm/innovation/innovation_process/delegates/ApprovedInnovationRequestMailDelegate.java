package ch.bpm.innovation.innovation_process.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ApprovedInnovationRequestMailDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Retrieve email addresses from process variables
        String employeeEmail = (String) execution.getVariable("employeeEmail");
        String teamLeadEmail = (String) execution.getVariable("teamLeadEmail");
        String innovationTitle = (String) execution.getVariable("title");

        // Email content
        String subject = "Innovation Request Approved";
        String body = "Dear Employee and Team Lead,\n\nYour innovation ( " + innovationTitle
                + ") request has been approved.\n\nBest regards,\nInnovation Board";

        // Send email
        sendEmail(employeeEmail, teamLeadEmail, subject, body);
    }

    private void sendEmail(String to1, String to2, String subject, String body) throws MessagingException {
        // Email configuration
        String from = "innovation.process@gmx.ch";
        String username = "innovation.process@gmx.ch";
        String password = "NoFear-11";
        String host = "mail.gmx.net";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); 

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to1));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to2));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            throw mex;
        }
    }

}
