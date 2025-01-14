package ru.clevertec.exceptionsstarter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.exceptions.handler")
public class ExceptionsStarterProperties {
    private boolean enabled;
}
