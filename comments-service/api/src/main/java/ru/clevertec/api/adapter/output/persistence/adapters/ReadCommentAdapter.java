package ru.clevertec.api.adapter.output.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.CommentEntity;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository;
import ru.clevertec.api.adapter.output.persistence.mapper.CommentEntityToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.out.ReadCommentPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byDateGreaterThan;
import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byNewsId;
import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byUsername;

@Component
@RequiredArgsConstructor
public class ReadCommentAdapter implements ReadCommentPort {

    private final CommentRepository commentRepository;
    private final CommentEntityToDomainMapper commentEntityToDomainMapper;

    @Override
    @Cacheable(value = "comment")
    public Optional<Comment> getCommentById(UUID id) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        return Optional.of(commentEntityToDomainMapper.toDomain(commentEntity.get()));
    }


    @Override
    public List<Comment> findByNewsId(UUID newsId) {
        return commentEntityToDomainMapper.toDomains(commentRepository.findByNewsId(newsId));
    }

    @Override
    public List<Comment> findByParams(UUID newsId, Optional<LocalDateTime> date, Optional<String> username) {
        List<CommentEntity> comments = commentRepository.findAll(byNewsId(newsId)
                .and(byDateGreaterThan(date))
                .and(byUsername(username)));
        return commentEntityToDomainMapper.toDomains(comments);
    }

    @Override
    public List<Comment> findByNewsIdAndPage(UUID newsId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> pageEntites = commentRepository.findAllByNewsId(newsId, pageable);
        List<CommentEntity> comments = pageEntites.get().collect(Collectors.toList());
        return commentEntityToDomainMapper.toDomains(comments);
    }
}
