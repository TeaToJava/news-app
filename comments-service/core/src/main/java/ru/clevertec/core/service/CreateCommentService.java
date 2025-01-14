package ru.clevertec.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.in.CreateCommentUseCase;
import ru.clevertec.core.ports.out.CreateCommentPort;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUseCase {
    private final CreateCommentPort createCommentPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    public void createComment(CreateCommentCommand createCommentCommand) {
        Comment comment = commandToDomainMapper.createCommentsCommandToDomain(createCommentCommand);
        comment.setTime(LocalDateTime.now());
        createCommentPort.saveComment(comment);
    }
}