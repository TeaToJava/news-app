package ru.clevertec.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.ReadNewsUseCase;
import ru.clevertec.core.ports.in.command.ReadNewsCommand;
import ru.clevertec.core.ports.in.command.ReadNewsCommandWithAllComments;
import ru.clevertec.core.ports.in.command.ReadNewsCommentCommand;
import ru.clevertec.core.ports.out.ReadNewsPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadNewsService implements ReadNewsUseCase {
    private final ReadNewsPort readNewsPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    public News findNewsById(ReadNewsCommand readNewsCommand) {
        UUID id = readNewsCommand.id();
        return readNewsPort.getNewsById(id).orElseThrow(() -> new ResourceException(id.toString()));
    }

    @Override
    public List<News> findAllNews() {
        return readNewsPort.getAllNews();
    }

    @Override
    @Transactional
    public News findNewsByIdWithComments(ReadNewsCommandWithAllComments readNewsCommand) {
        UUID id = readNewsCommand.id();
        News news = readNewsPort.getNewsById(id).orElseThrow(() -> new ResourceException(id.toString()));
        List<Comment> comments = readNewsPort.getCommentsByPageAndNewsId(id, readNewsCommand.page(), readNewsCommand.size());
        news.setComments(comments);
        return news;
    }

    @Override
    public Comment findNewsComment(ReadNewsCommentCommand readNewsCommentCommand) {
        UUID id = readNewsCommentCommand.newsId();
        News news = readNewsPort.getNewsById(id).orElseThrow(() -> new ResourceException(id.toString()));
        return readNewsPort.findCommentForNews(readNewsCommentCommand.commentId());
    }


    @Override
    public List<News> findAllNewsByPage(int offset, int limit) {
        return readNewsPort.getAllNews(offset, limit);
    }

    @Override
    public List<News> findByTitleAndDate(Optional<LocalDate> date, Optional<String> title) {
        if (!date.isEmpty()) {
            LocalDateTime localDateTime = LocalDateTime.of(date.get(), LocalTime.MIN);
            return readNewsPort.findByTitleAndDate(Optional.of(localDateTime), title);
        } else {
            return readNewsPort.findByTitle(title);
        }
    }

}
