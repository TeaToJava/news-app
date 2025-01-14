package ru.clevertec.core.ports.out;

import ru.clevertec.core.model.Comment;

public interface UpdateCommentPort {
    Comment updateComment(Comment comment);
}
