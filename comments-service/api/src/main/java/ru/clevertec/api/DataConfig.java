package ru.clevertec.api;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan(basePackages = {"ru.clevertec.api.adapter.output.persistence.jpa.entity"})
public class DataConfig {
}
