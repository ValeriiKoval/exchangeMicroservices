package com.microservice.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateEnum {

    VERIFICATION_EMAIL("verificationEmail.ftl");

    private final String name;
}