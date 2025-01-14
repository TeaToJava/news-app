package ru.clevertec.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.exceptions.ResourceType;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.out.ReadCommentPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReadCommentServiceTest {

    @Mock
    ReadCommentPort readCommentPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;

    @InjectMocks
    ReadCommentService readCommentService;

    @Test
    void shouldFindCommentById() {
        UUID id = UUID.randomUUID();
        Comment comment = Comment.builder().id(id).build();
        given(readCommentPort.getCommentById(id)).willReturn(Optional.ofNullable(comment));
        Comment actualComment = readCommentService.findById(id);
        assertEquals(comment.getId(), actualComment.getId());
    }

    @Test
    void shouldThrowExceptionsWhenCommentNotExist() {
        UUID id = UUID.randomUUID();
        ResourceException resourceException = new ResourceException(ResourceType.COMMENT, id.toString());
        given(readCommentPort.getCommentById(id)).willThrow(resourceException);
        assertThrows(ResourceException.class, () -> readCommentService.findById(id));
    }

    @Test
    void shouldFindCommentByNewsId() {
        UUID newsId = UUID.randomUUID();
        Comment comment = Comment.builder().id(UUID.randomUUID()).newsId(newsId).build();
        given(readCommentPort.findByNewsId(newsId)).willReturn(List.of(comment));
        List<Comment> comments = readCommentService.findByNewsId(newsId);
        assertTrue(comments.stream().allMatch(comm -> comm.getNewsId().equals(newsId)));
    }

    @Test
    void shouldFindAllCommentByParams() {
        UUID newsId = UUID.randomUUID();
        Optional<LocalDate> date = Optional.of(LocalDate.now());
        Optional<LocalDateTime> time = Optional.of(LocalDateTime.of(date.get(), LocalTime.MIN));
        Optional<String> username = Optional.of("User");
        int listSize = 5;
        List<Comment> comments = TestUtils.buildComments(newsId, username, listSize);
        given(readCommentPort.findByParams(newsId, time, username)).willReturn(comments);
        List<Comment> commentsExpected = readCommentService.findAllCommentByParams(newsId, date, username);
        assertTrue(commentsExpected.stream().allMatch(comment -> comment.getNewsId().equals(newsId)));
        assertTrue(commentsExpected.stream().allMatch(comment -> comment.getTime().isAfter(time.get())));
        assertTrue(commentsExpected.stream().allMatch(comment -> comment.getUsername().equals(username.get())));
    }

    @Test
    void shouldThrowErrorWhenFindAllCommentByParamsAndNewsIdNotExist() {
        UUID newsId = UUID.randomUUID();
        Optional<LocalDate> date = Optional.of(LocalDate.now());
        Optional<LocalDateTime> time = Optional.of(LocalDateTime.of(date.get(), LocalTime.MIN));
        Optional<String> username = Optional.of("User");

        ResourceException resourceException = new ResourceException(ResourceType.NEWS, newsId.toString());
        given(readCommentPort.findByParams(newsId, time, username)).willThrow(resourceException);
        assertThrows(ResourceException.class,
                () -> readCommentService.findAllCommentByParams(newsId, date, username));

    }

    @Test
    void shouldFindByNewsIdPageable() {
        UUID newsId = UUID.randomUUID();
        int page = 0;
        int size = 10;
        List<Comment> commentsExpected = TestUtils.buildComments(newsId, Optional.of("User"), 5);
        given(readCommentPort.findByNewsIdAndPage(newsId, page, size)).willReturn(commentsExpected);
        List<Comment> commentsActual = readCommentService.findByNewsIdPageable(newsId, page, size);
        assertTrue(commentsActual.stream().allMatch(comm -> comm.getNewsId().equals(newsId)));
    }
}