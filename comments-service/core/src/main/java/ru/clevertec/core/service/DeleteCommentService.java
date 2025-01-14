package ru.clevertec.core.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.DeleteCommentUseCase;
import ru.clevertec.core.ports.in.command.DeleteCommentCommand;
import ru.clevertec.core.ports.out.DeleteCommentPort;
import ru.clevertec.core.ports.out.ReadCommentPort;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCommentService implements DeleteCommentUseCase {
    private final DeleteCommentPort deleteCommentPort;
    private final ReadCommentPort readCommentPort;
    private final CommandToDomainMapper commandToDomainMapper;

    @Override
    public void deleteComment(DeleteCommentCommand deleteCommentCommand) {
        deleteCommentPort.deleteComment(deleteCommentCommand.id());
    }

    @Override
    @Transactional
    public void deleteCommentsByNewsId(UUID uuid) {
        List<Comment> comments = readCommentPort.findByNewsId(uuid);
        if (!comments.isEmpty()) {
            comments.stream().forEach(comment ->
                    deleteCommentPort.deleteComment(comment.getId()));
        }
    }
}
