package ru.clevertec.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.UpdateNewsUseCase;
import ru.clevertec.core.ports.in.command.UpdateNewsCommand;
import ru.clevertec.core.ports.out.ReadNewsPort;
import ru.clevertec.core.ports.out.UpdateNewsPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNewsService implements UpdateNewsUseCase {
    private final UpdateNewsPort updateNewsPort;
    private final CommandToDomainMapper commandToDomainMapper;
    private final ReadNewsPort readNewsPort;

    @Override
    @Transactional
    public News updateNews(UUID id, UpdateNewsCommand updateNewsCommand) {
        News news = readNewsPort.getNewsById(id)
                .orElseThrow(() -> new ResourceException(id.toString()));
        news.setTitle(updateNewsCommand.getTitle());
        news.setText(updateNewsCommand.getText());
        return updateNewsPort.updateNews(news);
    }
}
