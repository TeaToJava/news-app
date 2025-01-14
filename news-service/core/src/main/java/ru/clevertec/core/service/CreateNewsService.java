package ru.clevertec.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.in.CreateNewsUseCase;
import ru.clevertec.core.ports.out.CreateNewsPort;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateNewsService implements CreateNewsUseCase {
    private final CreateNewsPort createNewsPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    public void createNews(CreateNewsCommand createNewsCommand) {
        News news = commandToDomainMapper.createNewsCommandToDomain(createNewsCommand);
        news.setTime(LocalDateTime.now());
        createNewsPort.saveNews(news);
    }
}