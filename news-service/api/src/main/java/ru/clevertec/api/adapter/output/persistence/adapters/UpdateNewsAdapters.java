package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.NewsRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.NewsEntityToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.out.UpdateNewsPort;

@Component
@AllArgsConstructor
public class UpdateNewsAdapters implements UpdateNewsPort {

    private final NewsRepository newsRepository;
    private final NewsEntityToDomainMapper newsEntityToDomainMapper;

    @Override
    @CachePut("news")
    public News updateNews(News news) {
        NewsEntity newsEntity = newsRepository.save(newsEntityToDomainMapper.toEntity(news));
        return newsEntityToDomainMapper.toDomain(newsEntity);
    }
}
