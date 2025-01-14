package ru.clevertec.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "News Service",
        version = "1.0", description = "Service for news"))
public class SwaggerConfiguration {
}
