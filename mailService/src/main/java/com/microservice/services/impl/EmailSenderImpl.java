package com.microservice.services.impl;

import com.microservice.exceptions.MailException;
import com.microservice.services.EmailSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Set;

/**
 * Email sender service. Contains methods responsible for sending messages via e-mail.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    @Getter
    @Setter
    private String mailFrom;

    @Override
    public void sendMultiple(final Set<String> emailList, final String subject, final String body) {
        log.info("Messaging started");
        if (emailList.isEmpty()) {
            log.info("Message with subject: {} body: {} doesn't have recipients", subject, body);
            return;
        }
        final String[] recipients = emailList.toArray(new String[0]);
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message);
        log.info("Creating email for recipient(s): {} with subject: {}", recipients, subject);
        try {
            helper.setFrom(mailFrom);
            helper.setTo(recipients);
            helper.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error in creating email for recipients(s): {} with subject: {} body: {}", recipients, subject, body, e);
            throw new MailException(e);
        } catch (MailException mailException) {
            log.error("Error in sending email for recipient(s): {} with subject: {} body: {}", recipients, subject, body, mailException);
            throw new MailException(mailException);
        }
        log.info("Message for recipient: {} with subject: {} sent.", recipients, subject);
    }
}
