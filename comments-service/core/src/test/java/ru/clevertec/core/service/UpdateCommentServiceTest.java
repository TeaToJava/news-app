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
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;
import ru.clevertec.core.ports.out.ReadCommentPort;
import ru.clevertec.core.ports.out.UpdateCommentPort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateCommentServiceTest {
    @Mock
    UpdateCommentPort updateCommentPort;
    @Mock
    ReadCommentPort readCommentPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;
    @InjectMocks
    UpdateCommentService updateCommentService;

    @Test
    void shouldUpdateComment() {
        UUID commentId = UUID.randomUUID();
        UUID newsId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        String text = "Comment text";
        String newText = "New text";
        String username = "User 1";

        UpdateCommentCommand updateCommand = new UpdateCommentCommand(newText);
        Comment comment = TestUtils.buildComment(newsId, commentId, text, time, username);
        Comment expected = TestUtils.buildComment(newsId, commentId, newText, time, username);
        given(readCommentPort.getCommentById(commentId)).willReturn(Optional.ofNullable(comment));
        given(updateCommentPort.updateComment(comment))
                .willReturn(expected);

        Comment actual = updateCommentService.updateComment(commentId, updateCommand);
        assertTrue(comment.getId().equals(actual.getId()));
        assertTrue(newText.equals(actual.getText()));
    }

    @Test
    void shouldThrowErrorWhenUpdateComment() {
        UUID commentId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        String text = "Comment text";
        String newText = "New text";
        String username = "User 1";

        UpdateCommentCommand updateCommand = new UpdateCommentCommand(newText);
        ResourceException resourceException = new ResourceException(ResourceType.COMMENT, commentId.toString());
        given(readCommentPort.getCommentById(commentId)).willThrow(resourceException);

        assertThrows(ResourceException.class, () -> updateCommentService.updateComment(commentId, updateCommand));
    }
}