package ru.clevertec.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.exceptions.ResourceType;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.ReadCommentUseCase;
import ru.clevertec.core.ports.out.ReadCommentPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadCommentService implements ReadCommentUseCase {
    private final ReadCommentPort readCommentPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    public Comment findById(UUID commentId) {
        return readCommentPort.getCommentById(commentId)
                .orElseThrow(() -> new ResourceException(ResourceType.COMMENT, commentId.toString()));
    }

    @Override
    public List<Comment> findByNewsId(UUID newsId) {
        return readCommentPort.findByNewsId(newsId);
    }

    @Override
    public List<Comment> findAllCommentByParams(UUID newsId, Optional<LocalDate> date,
                                                Optional<String> username) {
        if (newsId == null) {
            throw new ResourceException(ResourceType.NEWS, newsId.toString());
        }
        Optional<LocalDateTime> localDateTime;
        if (!date.isEmpty()) {
            localDateTime = Optional.of(LocalDateTime.of(date.get(), LocalTime.MIN));
        } else {
            localDateTime = Optional.empty();
        }
        return readCommentPort.findByParams(newsId, localDateTime, username);
    }

    @Override
    public List<Comment> findByNewsIdPageable(UUID newsId, int page, int size) {
        return readCommentPort.findByNewsIdAndPage(newsId, page, size);
    }
}
