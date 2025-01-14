package ru.clevertec.api.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;
import ru.clevertec.core.model.News;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsEntityToDomainMapper {
    NewsEntity toEntity(News news);

    News toDomain(NewsEntity news);

    List<NewsEntity> toEntities(List<News> news);

    List<News> toDomains(List<NewsEntity> news);
}