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
}