package com.microservice.authservice.service.impl;

import com.microservice.authservice.exception.MessageException;
import com.microservice.authservice.model.TemplateEnum;
import com.microservice.authservice.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private static final String MERGING_TEMPLATE_EXCEPTION_MESSAGE = "Merging the template with the data-model failed.";

    private final Configuration configuration;

    @Override
    public String defineBody(final TemplateEnum templateEnum, final Map<String, Object> args) {
        try {
            final Template template = getTemplate(templateEnum);
            final StringWriter writer = getStringWriter(args, template);
            return writer.getBuffer().toString();
        } catch (TemplateException | IOException e) {
            throw new MessageException(MERGING_TEMPLATE_EXCEPTION_MESSAGE);
        }
    }

    private StringWriter getStringWriter(final Map<String, Object> args, final Template template)
            throws TemplateException, IOException {
        final StringWriter writer = new StringWriter();
        template.process(args, writer);
        return writer;
    }

    private Template getTemplate(final TemplateEnum templateEnum) throws IOException {
        try {
            return configuration.getTemplate(templateEnum.getName());
        } catch (IOException e) {
            log.error("Template with name {} not found.", templateEnum.getName(), e);
            throw e;
        }
    }

}
