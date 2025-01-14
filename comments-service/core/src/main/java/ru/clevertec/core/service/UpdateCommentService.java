package ru.clevertec.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.exceptions.ResourceType;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.UpdateCommentUseCase;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;
import ru.clevertec.core.ports.out.ReadCommentPort;
import ru.clevertec.core.ports.out.UpdateCommentPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCommentService implements UpdateCommentUseCase {
    private final UpdateCommentPort updateCommentPort;
    private final CommandToDomainMapper commandToDomainMapper;
    private final ReadCommentPort readCommentPort;

    @Override
    @Transactional
    public Comment updateComment(UUID id, UpdateCommentCommand updateCommentCommand) {
        Comment comment = readCommentPort.getCommentById(id)
                .orElseThrow(() -> new ResourceException(ResourceType.COMMENT, id.toString()));
        comment.setText(updateCommentCommand.getText());
        return updateCommentPort.updateComment(comment);
    }
}
