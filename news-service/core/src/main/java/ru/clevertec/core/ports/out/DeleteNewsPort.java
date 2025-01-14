package ru.clevertec.core.ports.out;

import java.util.UUID;

public interface DeleteNewsPort {
    void deleteNews(UUID id);

    void deleteNewsComments(UUID id);
}
