package ru.clevertec.core.ports.in;

import ru.clevertec.core.model.Comment;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.ReadNewsCommand;
import ru.clevertec.core.ports.in.command.ReadNewsCommandWithAllComments;
import ru.clevertec.core.ports.in.command.ReadNewsCommandWithAllComments;
import ru.clevertec.core.ports.in.command.ReadNewsCommentCommand;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReadNewsUseCase {
    News findNewsById(ReadNewsCommand readNewsCommand);

    List<News> findAllNews();

    News findNewsByIdWithComments(ReadNewsCommandWithAllComments readNewsCommand);

    Comment findNewsComment(ReadNewsCommentCommand readNewsCommentCommand);
    List<News> findAllNewsByPage(int offset, int limit);

    List<News> findByTitleAndDate(Optional<LocalDate> date, Optional<String> title);
}
