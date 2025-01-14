package ru.clevertec.api.adapter.input.web.controller;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.api.adapter.input.web.mapper.CommentMapper;
import ru.clevertec.api.adapter.input.web.request.CreateCommentRequest;
import ru.clevertec.api.adapter.input.web.request.DeleteCommentRequest;
import ru.clevertec.api.adapter.input.web.request.UpdateCommentRequest;
import ru.clevertec.api.adapter.input.web.response.CommentApiResponse;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.CreateCommentUseCase;
import ru.clevertec.core.ports.in.DeleteCommentUseCase;
import ru.clevertec.core.ports.in.ReadCommentUseCase;
import ru.clevertec.core.ports.in.UpdateCommentUseCase;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.in.command.DeleteCommentCommand;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentsController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CreateCommentUseCase createCommentService;
    @MockitoBean
    private ReadCommentUseCase readCommentService;
    @MockitoBean
    private UpdateCommentUseCase updateCommentService;
    @MockitoBean
    private DeleteCommentUseCase deleteCommentService;
    @MockitoBean
    private CommentMapper commentMapper;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    @SneakyThrows
    @Test
    void shouldFindComment() {
        String uuid = "8d10be5a-8b7b-43e7-b7a0-dd6b92619452";
        UUID commentId = UUID.fromString(uuid);
        Comment comment = getComments().getFirst();
        CommentApiResponse commentApiResponse = getCommentApiResponses().getFirst();
        given(readCommentService.findById(commentId)).willReturn(comment);
        given(commentMapper.toCommentApiResponse(getComments().getFirst()))
                .willReturn(commentApiResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comments/{id}",
                        commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid));
    }

    @SneakyThrows
    @Test
    void shouldCreateComment() {
        String uuid = "8793e0de-a4b9-4b1c-804e-6bb893aed9f4";
        UUID newsId = UUID.fromString(uuid);
        Comment comment = getComments().getFirst();
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(comment.getText(),
                comment.getUsername(), newsId);
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(comment.getText(),
                comment.getUsername(), newsId);

        given(commentMapper.toCreateCommentCommand(createCommentRequest))
                .willReturn(createCommentCommand);
        doNothing().when(createCommentService).createComment(createCommentCommand);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "      \"text\": \"Good comment\",\n" +
                                "      \"newsId\": \"8793e0de-a4b9-4b1c-804e-6bb893aed9f4\",\n" +
                                "      \"username\": \"User\"}"))
                .andExpect(status().isOk());

    }

    @SneakyThrows
    @Test
    void shouldDeleteComment() {
        UUID id = UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452");
        DeleteCommentRequest deleteCommentRequest =
                new DeleteCommentRequest(id);
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(id);
        given(commentMapper.toDeleteCommentCommand(deleteCommentRequest))
                .willReturn(new DeleteCommentCommand(id));
        doNothing().when(deleteCommentService).deleteComment(deleteCommentCommand);
    }

    @SneakyThrows
    @Test
    void shouldUpdateComment() {
        UUID commentId = UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452");
        String text = "text";
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(text);
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(text);
        given(commentMapper.toUpdateCommentCommand(updateCommentRequest))
                .willReturn(updateCommentCommand);
        CommentApiResponse commentApiResponse = getCommentApiResponses().getFirst();
        Comment comment = getComments().getFirst();

        given(updateCommentService.updateComment(commentId, updateCommentCommand))
                .willReturn(comment);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"Good comment\"}"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void shouldReturnErrorWhenUpdateComment() {
        UUID commentId = UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void shouldReturnCommentsByNewsId() {
        UUID commentId = UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452");
        UUID newsId = UUID.fromString("8793e0de-a4b9-4b1c-804e-6bb893aed9f4");
        int page = 2;
        int size = 5;
        List<Comment> comments = getComments();
        given(readCommentService.findByNewsIdPageable(newsId, page, size))
                .willReturn(comments);
        List<CommentApiResponse> commentApiResponses = getCommentApiResponses();
        given(commentMapper.toCommentApiResponses(comments))
                .willReturn(commentApiResponses);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comments")
                        .param("newsId", String.valueOf(newsId))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void shouldReturnCommentsByFilter() {
        UUID commentId = UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452");
        UUID newsId = UUID.fromString("8793e0de-a4b9-4b1c-804e-6bb893aed9f4");
        LocalDate date = DATE_TIME.toLocalDate();
        List<Comment> comments = getComments();
        String username = getComments().getFirst().getUsername();

        given(readCommentService.findAllCommentByParams(newsId,
                Optional.ofNullable(date), Optional.ofNullable(username)))
                .willReturn(comments);
        List<CommentApiResponse> commentApiResponses = getCommentApiResponses();
        given(commentMapper.toCommentApiResponses(comments))
                .willReturn(commentApiResponses);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comments/filter")
                        .param("newsId", String.valueOf(newsId))
                        .param("date", String.valueOf(date))
                        .param("username", String.valueOf(username)))
                .andExpect(status().isOk());
    }

    private List<Comment> getComments() {
        Comment comment = Comment.builder()
                .id(UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452"))
                .newsId(UUID.fromString("8793e0de-a4b9-4b1c-804e-6bb893aed9f4"))
                .time(DATE_TIME)
                .username("User")
                .text("Good comment").build();
        Comment commentTwo = Comment.builder()
                .id(UUID.fromString("f5ad450b-5d92-4cbf-8393-96a3ee4a3f00"))
                .newsId(UUID.fromString("4f55416f-a2cb-4352-8d4c-1b0d84872921"))
                .time(DATE_TIME)
                .username("User 2")
                .text("Best").build();
        return List.of(comment, commentTwo);
    }

    private List<CommentApiResponse> getCommentApiResponses() {
        CommentApiResponse first = CommentApiResponse.builder()
                .id(UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452"))
                .time(DATE_TIME)
                .username("User")
                .text("Good comment").build();
        CommentApiResponse second = CommentApiResponse.builder()
                .id(UUID.fromString("f5ad450b-5d92-4cbf-8393-96a3ee4a3f00"))
                .time(DATE_TIME)
                .username("User 2")
                .text("Best").build();
        return List.of(first, second);
    }
}