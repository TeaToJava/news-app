package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.CommentEntityToDomainMapper;
import ru.clevertec.core.ports.out.DeleteCommentPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteCommentAdapter implements DeleteCommentPort {
    private final CommentRepository commentRepository;
    private final CommentEntityToDomainMapper commentEntityToDomainMapper;

    @Override
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }

}
