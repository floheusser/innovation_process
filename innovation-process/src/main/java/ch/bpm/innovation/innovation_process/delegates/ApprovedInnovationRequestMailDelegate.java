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

        // Email content
        String subject = "Innovation Request Approved";
        String body = "Dear Employee and Team Lead,\n\nYour innovation request has been approved.\n\nBest regards,\nInnovation Board";

        // Send email
        sendEmail(employeeEmail, teamLeadEmail, subject, body);
    }

    private void sendEmail(String to1, String to2, String subject, String body) throws MessagingException {
        // Email configuration
        String from = "your-email@example.com"; // Replace with your email
        String host = "smtp.example.com"; // Replace with your SMTP server

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

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
