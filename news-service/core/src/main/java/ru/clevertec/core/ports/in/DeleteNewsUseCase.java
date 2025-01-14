package ru.clevertec.core.ports.in;

import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.in.command.DeleteNewsCommand;

public interface DeleteNewsUseCase {
    void deleteNews(DeleteNewsCommand deleteNewsCommand);
}
