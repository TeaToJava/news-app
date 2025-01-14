package ru.clevertec.core.ports.out;

import ru.clevertec.core.model.Comment;

public interface CreateCommentPort {
    void saveComment(Comment comment);
}
