package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.NewsRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.NewsEntityToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.out.CreateNewsPort;

@Component
@AllArgsConstructor
public class CreateNewsAdapter implements CreateNewsPort {
    private final NewsRepository newsRepository;
    private final NewsEntityToDomainMapper newsEntityToDomainMapper;

    @Override
    @Cacheable("news")
    public void saveNews(News news) {
        newsRepository.save(newsEntityToDomainMapper.toEntity(news));
    }
}
