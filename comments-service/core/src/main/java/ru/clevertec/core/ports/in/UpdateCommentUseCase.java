package ru.clevertec.core.ports.in;

import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;

import java.util.UUID;

public interface UpdateCommentUseCase {
    Comment updateComment(UUID id, UpdateCommentCommand updateCommentCommand);

}
