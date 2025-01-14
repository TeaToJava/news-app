package ru.clevertec.exceptionsstarter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.clevertec.exceptionsstarter.advice.ErrorHandler;

@AutoConfiguration
@EnableConfigurationProperties(ExceptionsStarterProperties.class)
@ConditionalOnProperty(prefix = "app.exceptions.handler", name = "enabled", havingValue = "true")
public class ExceptionsStarterAutoConfiguration {

    @Bean
    public ErrorHandler errorHandler() {
        return new ErrorHandler();
    }
}
