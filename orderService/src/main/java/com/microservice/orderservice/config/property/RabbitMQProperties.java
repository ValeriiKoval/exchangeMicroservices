package com.microservice.orderservice.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Getter
@Setter
public class RabbitMQProperties {

    private Exchanges exchanges;
    private RoutingKeys routingKeys;

    @Getter
    @Setter
    public static class Exchanges {
        private String mail;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String send;
    }
}
