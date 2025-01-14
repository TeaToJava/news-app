package ru.clevertec.core.ports.out;

import org.hibernate.annotations.Comments;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.model.News;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadNewsPort {
    Optional<News> getNewsById(UUID id);

    List<Comment> getCommentsByPageAndNewsId(UUID id, int page, int size);

    List<News> getAllNews();

    List<News> getAllNews(int offset, int limit);

    List<News> findByTitleAndDate(Optional<LocalDateTime> date, Optional<String> title);

    List<News> findByTitle(Optional<String> title);

    Comment findCommentForNews(UUID uuid);
}
