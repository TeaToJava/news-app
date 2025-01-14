package ru.clevertec.api.adapter.input.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.api.adapter.input.web.request.CreateCommentRequest;
import ru.clevertec.api.adapter.input.web.request.DeleteCommentRequest;
import ru.clevertec.api.adapter.input.web.request.DeleteCommentsByNewsIdRequest;
import ru.clevertec.api.adapter.input.web.request.UpdateCommentRequest;
import ru.clevertec.api.adapter.input.web.response.CommentApiResponse;
import ru.clevertec.api.adapter.input.web.mapper.CommentMapper;
import ru.clevertec.core.ports.in.CreateCommentUseCase;
import ru.clevertec.core.ports.in.DeleteCommentUseCase;
import ru.clevertec.core.ports.in.ReadCommentUseCase;
import ru.clevertec.core.ports.in.UpdateCommentUseCase;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.in.command.DeleteCommentCommand;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
public class CommentsController {
    private final CreateCommentUseCase createCommentUseCase;
    private final ReadCommentUseCase readCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final CommentMapper commentMapper;

    @GetMapping("/{commentId}")
    public CommentApiResponse findComment(@PathVariable @NotNull UUID commentId) {
        return commentMapper.toCommentApiResponse(readCommentUseCase.findById(commentId));
    }

    @PostMapping()
    public void createComment(@RequestBody @Valid CreateCommentRequest createCommentRequest) {
        CreateCommentCommand createCommentCommand = commentMapper.toCreateCommentCommand(createCommentRequest);
        createCommentUseCase.createComment(createCommentCommand);
    }

    @DeleteMapping()
    public void deleteComment(@RequestBody @Valid DeleteCommentRequest deleteCommentRequest) {
        DeleteCommentCommand deleteCommentCommand = commentMapper.toDeleteCommentCommand(deleteCommentRequest);
        deleteCommentUseCase.deleteComment(deleteCommentCommand);
    }

    @PutMapping("/{commentId}")
    public CommentApiResponse updateComment(@PathVariable("commentId") @NotNull UUID commentId,
                                            @RequestBody @Valid UpdateCommentRequest updateCommentRequest) {
        UpdateCommentCommand updateCommentCommand = commentMapper.toUpdateCommentCommand(updateCommentRequest);
        return commentMapper.toCommentApiResponse(updateCommentUseCase.updateComment(commentId, updateCommentCommand));
    }

    @GetMapping()
    public List<CommentApiResponse> getCommentsByNewsId(@RequestParam("newsId") @NotNull UUID newsId,
                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        return commentMapper.toCommentApiResponses(readCommentUseCase.findByNewsIdPageable(newsId, page, size));
    }

    @PostMapping("/delete")
    public void deleteCommentsByNewsId(@RequestBody @Valid DeleteCommentsByNewsIdRequest deleteCommentsRequest) {
        deleteCommentUseCase.deleteCommentsByNewsId(deleteCommentsRequest.newsId());
    }

    @GetMapping("/filter")
    public List<CommentApiResponse> findComments(
            @RequestParam("newsId") @NotNull UUID newsId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date,
            @RequestParam("username") Optional<String> username) {
        return commentMapper.toCommentApiResponses(readCommentUseCase.findAllCommentByParams(newsId, date, username));

    }
}
