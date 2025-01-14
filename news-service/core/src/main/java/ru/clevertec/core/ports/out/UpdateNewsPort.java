package ru.clevertec.core.ports.out;

import ru.clevertec.core.model.News;

public interface UpdateNewsPort {
    News updateNews(News news);
}
