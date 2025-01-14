package ru.clevertec.core.ports.in;

import ru.clevertec.core.ports.in.command.CreateCommentCommand;

public interface CreateCommentUseCase {
    void createComment(CreateCommentCommand createCommentCommand);
}
