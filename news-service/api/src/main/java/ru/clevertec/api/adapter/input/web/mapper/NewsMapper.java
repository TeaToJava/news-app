package ru.clevertec.api.adapter.input.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.api.adapter.input.web.request.CreateNewsRequest;
import ru.clevertec.api.adapter.input.web.request.UpdateNewsRequest;
import ru.clevertec.api.adapter.input.web.response.NewsResponse;
import ru.clevertec.api.adapter.input.web.response.NewsWithCommentsResponse;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.in.command.UpdateNewsCommand;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    CreateNewsCommand toCreateNewsCommand(CreateNewsRequest createNewsRequest);
    UpdateNewsCommand toUpdateNewsCommand(UpdateNewsRequest updateNewsRequest);

    NewsResponse toNewsResponse(News news);
    List<NewsResponse> toNewsResponses(List<News> news);

    NewsWithCommentsResponse toNewsWithCommentsResponse(News news);
}
