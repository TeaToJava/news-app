package ru.clevertec.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.api.feignclient.CommentsFeignClient;

@SpringBootApplication(scanBasePackages = {"ru.clevertec.api", "ru.clevertec.core"})
@EntityScan(basePackages = {"ru.clevertec.api.adapter.output.persistence.jpa.entity"})
@EnableFeignClients(clients = CommentsFeignClient.class)

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
