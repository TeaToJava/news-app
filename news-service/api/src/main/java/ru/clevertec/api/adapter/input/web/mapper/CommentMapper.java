package ru.clevertec.api.adapter.input.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.api.adapter.input.web.response.CommentResponse;
import ru.clevertec.core.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponse toCommentResponse(Comment comment);
}
