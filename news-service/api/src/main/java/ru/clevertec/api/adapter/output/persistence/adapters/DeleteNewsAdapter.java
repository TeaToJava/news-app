package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.NewsRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.NewsEntityToDomainMapper;
import ru.clevertec.api.feignclient.CommentsFeignClient;
import ru.clevertec.api.feignclient.model.DeleteCommentsRequest;
import ru.clevertec.core.ports.out.DeleteNewsPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteNewsAdapter implements DeleteNewsPort {

    private final CommentsFeignClient commentsFeignClient;
    private final NewsRepository newsRepository;
    private final NewsEntityToDomainMapper newsEntityToDomainMapper;

    @Override
    @CacheEvict("news")
    public void deleteNews(UUID id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void deleteNewsComments(UUID id) {
        commentsFeignClient.deleteCommentsByNewsId(new DeleteCommentsRequest(id));
    }
}
