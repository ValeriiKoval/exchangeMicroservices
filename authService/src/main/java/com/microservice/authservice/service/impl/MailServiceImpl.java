package com.microservice.authservice.service.impl;

import com.microservice.authservice.config.property.RabbitMQProperties;
import com.microservice.authservice.model.Message;
import com.microservice.authservice.service.MailService;
import com.microservice.authservice.service.TemplateService;
import com.microservice.authservice.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Base implementation of {@link MailService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final TemplateService templateService;
    private final RabbitMQMessageProducer producer;
    private final RabbitMQProperties rabbitMQProperties;

    @Override
    public void sendMail(final String email, final Message message) {
        final String body = defineMessageBody(message);
        final MailDto mailDto = MailDto.builder()
                .to(Set.of(email))
                .subject(message.getSubject())
                .body(body)
                .build();
        producer.publish(mailDto,
                rabbitMQProperties.getExchanges().getMail(),
                rabbitMQProperties.getRoutingKeys().getSend());
    }

    private String defineMessageBody(final Message message) {
        return templateService.defineBody(message.getTemplate(), message.getArgs());
    }
}
