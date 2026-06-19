package com.example.demo_Electricity_App.Service;

import com.example.demo_Electricity_App.Exception.EmailSendingFailureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String onBoardInvitation(String emailId) {

        String invitationToken = UUID.randomUUID().toString();

        String invitationMessage =
                """
                We invite you to register for the Electricity Management Platform.

                Please use the below invitation token while registering.

                Invitation Token:
                """
                        + invitationToken;

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailId);
            mailMessage.setSubject("Tenant User Invitation");
            mailMessage.setText(invitationMessage);

            mailSender.send(mailMessage);

        } catch (MailException ex) {

            throw new EmailSendingFailureException(
                    "Unable to send invitation email"
            );
        }

        return invitationToken;
    }

    //Sends the customer their enrollment confirmation once CRM creates
    public void sendEnrollmentConfirmation(String emailId, String customerName, String tenantName) {
        String message =
                "Hi " + customerName + ",\n\n"
                        + "You have successfully enrolled for an electricity connection with "
                        + tenantName + ".\n"
                        + "You can download our app to track your connection status, meter "
                        + "readings, bills, and raise complaints.\n\n"
                        + "Thank you for choosing us.";

        sendPlainEmail(emailId, "Connection Enrollment Confirmation", message);
    }

    //Sends the generated bill to the customer's registered email as soon
    public void sendBillGeneratedEmail(
            String emailId, String customerName, Double amount, java.time.LocalDate dueDate) {

        String message =
                "Hi " + customerName + ",\n\n"
                        + "Your latest electricity bill has been generated.\n"
                        + "Amount due: " + amount + "\n"
                        + "Due date: " + dueDate + "\n\n"
                        + "Please log in to the app to view the detailed bill and make payment.";

        sendPlainEmail(emailId, "Your Electricity Bill is Ready", message);
    }

    private void sendPlainEmail(String emailId, String subject, String body) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailId);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);
            mailSender.send(mailMessage);
        } catch (MailException ex) {
            throw new EmailSendingFailureException("Unable to send email: " + subject);
        }
    }
}