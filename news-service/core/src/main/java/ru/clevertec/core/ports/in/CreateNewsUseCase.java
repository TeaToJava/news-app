package ru.clevertec.core.ports.in;

import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;

public interface CreateNewsUseCase {
    void createNews(CreateNewsCommand createNewsCommand);
}
