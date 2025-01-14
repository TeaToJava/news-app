package ru.clevertec.core.ports.in;

import ru.clevertec.core.model.Comment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadCommentUseCase {

    Comment findById(UUID commentId);
    List<Comment> findByNewsId(UUID newsId);

    List<Comment> findAllCommentByParams(UUID newsId, Optional<LocalDate> date, Optional<String> username);

    List<Comment> findByNewsIdPageable(UUID newsId, int page, int size);
}
