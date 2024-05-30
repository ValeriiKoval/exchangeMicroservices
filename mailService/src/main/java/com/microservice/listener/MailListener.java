package com.microservice.listener;

import com.microservice.model.Mail;
import com.microservice.services.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailListener {

    private final EmailSender sender;

    @RabbitListener(queues = "${spring.rabbitmq.queues.send}")
    public void consume(final Mail request) {
        try {
            log.info("Consumed {} from queue", request);
            sender.sendMultiple(request.getTo(), request.getSubject(), request.getBody());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
