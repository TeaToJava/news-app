package ru.clevertec.api.feignclient;

import org.mapstruct.Mapper;
import ru.clevertec.api.feignclient.model.CommentApiResponse;
import ru.clevertec.core.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentApiResponseMapper {
    List<Comment> toComments(List<CommentApiResponse> commentApiResponses);

    Comment toComment(CommentApiResponse commentApiResponses);
}
