package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.CommentEntityToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.out.CreateCommentPort;

@Component
@AllArgsConstructor
public class CreateCommentAdapter implements CreateCommentPort {
    private final CommentRepository newsRepository;
    private final CommentEntityToDomainMapper newsEntityToDomainMapper;

    @Override
    public void saveComment(Comment comment) {
        newsRepository.save(newsEntityToDomainMapper.toEntity(comment));
    }
}
