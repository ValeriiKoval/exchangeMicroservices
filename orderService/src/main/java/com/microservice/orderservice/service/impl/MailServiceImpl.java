package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.config.property.RabbitMQProperties;
import com.microservice.orderservice.model.Message;
import com.microservice.orderservice.service.MailService;
import com.microservice.orderservice.service.TemplateService;
import com.microservice.orderservice.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
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
