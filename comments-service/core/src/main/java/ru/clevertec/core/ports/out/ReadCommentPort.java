package ru.clevertec.core.ports.out;

import ru.clevertec.core.model.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadCommentPort {
    Optional<Comment> getCommentById(UUID id);

    List<Comment> findByNewsId(UUID newsId);

    List<Comment> findByParams(UUID newsId, Optional<LocalDateTime> date, Optional<String> username);

    List<Comment> findByNewsIdAndPage(UUID newsId, int page, int size);
}
