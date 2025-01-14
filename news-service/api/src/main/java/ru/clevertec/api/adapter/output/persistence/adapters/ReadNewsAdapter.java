package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.NewsRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.NewsEntityToDomainMapper;
import ru.clevertec.api.feignclient.CommentApiResponseMapper;
import ru.clevertec.api.feignclient.CommentsFeignClient;
import ru.clevertec.api.feignclient.model.CommentApiResponse;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.out.ReadNewsPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.clevertec.api.adapter.output.persistence.jpa.specs.NewsSpecification.hasDateGreaterThan;
import static ru.clevertec.api.adapter.output.persistence.jpa.specs.NewsSpecification.hasTitle;

@Component
@AllArgsConstructor
public class ReadNewsAdapter implements ReadNewsPort {

    private final NewsRepository newsRepository;
    private final CommentsFeignClient commentsFeignClient;
    private final NewsEntityToDomainMapper newsEntityToDomainMapper;
    private final CommentApiResponseMapper commentApiResponseMapper;

    @Override
    @Cacheable(value = "news")
    public Optional<News> getNewsById(UUID id) {
        Optional<NewsEntity> newsEntity = newsRepository.findById(id);
        return Optional.of(newsEntityToDomainMapper.toDomain(newsEntity.get()));
    }

    @Override
    public List<Comment> getCommentsByPageAndNewsId(UUID id, int page, int size) {
        List<CommentApiResponse> comments = commentsFeignClient.getCommentsByNewsId(id, page, size);
        return commentApiResponseMapper.toComments(comments);
    }


    @Override
    public List<News> getAllNews() {
        return newsEntityToDomainMapper.toDomains(newsRepository.findAll());
    }

    @Override
    public List<News> getAllNews(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<NewsEntity> newsEntities = newsRepository.findAll(pageable).getContent();
        return newsEntityToDomainMapper.toDomains(newsEntities);
    }

    @Override
    public List<News> findByTitleAndDate(Optional<LocalDateTime> date, Optional<String> title) {
        List<NewsEntity> newsEntities = newsRepository
                .findAll(where(hasDateGreaterThan(date))
                        .and(hasTitle(title)));
        return newsEntityToDomainMapper.toDomains(newsEntities);
    }

    @Override
    public List<News> findByTitle(Optional<String> title) {
        List<NewsEntity> newsEntities = newsRepository
                .findAll(where((hasTitle(title))));
        return newsEntityToDomainMapper.toDomains(newsEntities);
    }

    @Override
    public Comment findCommentForNews(UUID commentId) {
        return commentApiResponseMapper.toComment(commentsFeignClient.findComment(commentId));
    }
}
