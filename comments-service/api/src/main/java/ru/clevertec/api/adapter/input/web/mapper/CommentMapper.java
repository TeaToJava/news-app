package ru.clevertec.api.adapter.input.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.api.adapter.input.web.request.CreateCommentRequest;
import ru.clevertec.api.adapter.input.web.request.DeleteCommentRequest;
import ru.clevertec.api.adapter.input.web.request.UpdateCommentRequest;
import ru.clevertec.api.adapter.input.web.response.CommentApiResponse;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.in.command.DeleteCommentCommand;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CreateCommentCommand toCreateCommentCommand(CreateCommentRequest createCommentRequest);

    UpdateCommentCommand toUpdateCommentCommand(UpdateCommentRequest updateCommentRequest);

    DeleteCommentCommand toDeleteCommentCommand(DeleteCommentRequest deleteCommentRequest);

    CommentApiResponse toCommentApiResponse(Comment comment);

    List<CommentApiResponse> toCommentApiResponses(List<Comment> comments);
}
