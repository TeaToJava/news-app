package ru.clevertec.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.ports.in.command.DeleteCommentCommand;
import ru.clevertec.core.ports.out.DeleteCommentPort;
import ru.clevertec.core.ports.out.ReadCommentPort;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCommentServiceTest {
    @Mock
    DeleteCommentPort deleteCommentPort;
    @Mock
    ReadCommentPort readCommentPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;
    @InjectMocks
    DeleteCommentService deleteCommentService;

    @Test
    void shouldDeleteComment() {
        UUID commentId = UUID.randomUUID();
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId);

        deleteCommentService.deleteComment(deleteCommentCommand);
        verify(deleteCommentPort).deleteComment(deleteCommentCommand.id());
    }

}