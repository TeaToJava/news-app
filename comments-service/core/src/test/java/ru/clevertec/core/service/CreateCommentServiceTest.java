package ru.clevertec.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.out.CreateCommentPort;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateCommentServiceTest {
    @Mock
    CreateCommentPort createCommentPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;
    @InjectMocks
    CreateCommentService createCommentService;

    @Test
    void shouldCreateComment() {
        UUID commentId = UUID.randomUUID();
        UUID newsId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        String text = "Comment text";
        String username = "User 1";

        CreateCommentCommand createCommand = TestUtils.buildCreateCommentCommand(newsId, text, username);
        Comment comment = TestUtils.buildComment(newsId, commentId, text, time, username);
        given(commandToDomainMapper.createCommentsCommandToDomain(createCommand))
                .willReturn(comment);
        doNothing().when(createCommentPort).saveComment(comment);
        createCommentService.createComment(createCommand);
        verify(createCommentPort).saveComment(comment);
    }

}