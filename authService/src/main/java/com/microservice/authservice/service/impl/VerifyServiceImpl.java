package com.microservice.authservice.service.impl;

import com.microservice.authservice.model.Message;
import com.microservice.authservice.model.TemplateEnum;
import com.microservice.authservice.model.User;
import com.microservice.authservice.service.MailService;
import com.microservice.authservice.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    private final MailService mailService;

    @Value("${app.gatewayUrl}")
    private String gatewayUrl;

    @Override
    public void sendVerificationEmail(final User user) {
        final String token = user.getVerifyToken();
        final Map<String, Object> args = new HashMap<>();
        args.put("gatewayUrl", gatewayUrl);
        args.put("token", token);
        final Message message = Message.builder()
                .subject("Verification email")
                .template(TemplateEnum.VERIFICATION_EMAIL)
                .args(args)
                .build();
        mailService.sendMail(user.getEmail(), message);
    }

    @Override
    public boolean checkCode(int code, User user) {
        return false;
    }
}
