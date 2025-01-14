package ru.clevertec.core.ports.out;

import ru.clevertec.core.model.News;

public interface CreateNewsPort {
    void saveNews(News news);
}
