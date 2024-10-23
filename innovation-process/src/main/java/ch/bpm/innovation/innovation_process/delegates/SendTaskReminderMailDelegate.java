package ch.bpm.innovation.innovation_process.delegates;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

public class SendTaskReminderMailDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Get the task service and identity service
        Task task = execution.getProcessEngineServices().getTaskService().createTaskQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .list().get(0);

        String assignee = task.getAssignee();


        // Get the identity service
        IdentityService identityService = execution.getProcessEngineServices().getIdentityService();
        User user = identityService.createUserQuery().userId(assignee).singleResult();

        // Retrieve email address of the assignee
        String taskOwnerMail = user.getEmail();
        String taskId = task.getId();
        String taskUrl = "http://localhost:8085/camunda/app/tasklist/default/#/task=" + taskId;

        // Email content
        String subject = "Task Reminder " + taskId;
        String body = "Dear Employee,\n\nThis is a reminder to complete your task. \n" + taskUrl + "\n\nBest regards,\nInnovation Process";

        // Send email
        sendEmail(taskOwnerMail, subject, body);
    }

    private void sendEmail(String to, String subject, String body) throws MessagingException {
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
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