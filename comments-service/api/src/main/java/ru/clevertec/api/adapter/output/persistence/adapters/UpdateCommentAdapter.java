package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.CommentEntity;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.CommentEntityToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.out.UpdateCommentPort;

@Component
@AllArgsConstructor
public class UpdateCommentAdapter implements UpdateCommentPort {

    private final CommentRepository commentRepository;
    private final CommentEntityToDomainMapper commentEntityToDomainMapper;

    @Override
    public Comment updateComment(Comment comment) {
        CommentEntity commentEntity = commentEntityToDomainMapper.toEntity(comment);
        return commentEntityToDomainMapper.toDomain(commentRepository.save(commentEntity));
    }
}
