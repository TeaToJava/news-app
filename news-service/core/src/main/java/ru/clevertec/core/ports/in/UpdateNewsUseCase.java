package ru.clevertec.core.ports.in;

import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.in.command.UpdateNewsCommand;

import java.util.UUID;

public interface UpdateNewsUseCase {
    News updateNews(UUID id, UpdateNewsCommand updateNewsCommand);
}
