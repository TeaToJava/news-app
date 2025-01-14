package ru.clevertec.loggingstarter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {
    private boolean enabled;
}