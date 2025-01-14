package ru.clevertec.core.ports.in;

import ru.clevertec.core.ports.in.command.DeleteCommentCommand;

import java.util.UUID;

public interface DeleteCommentUseCase {
    void deleteComment(DeleteCommentCommand deleteCommentCommand);

    void deleteCommentsByNewsId(UUID uuid);
}
