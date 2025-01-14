package ru.clevertec.core.service;

import ru.clevertec.core.model.Comment;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class TestUtils {

    public static Comment buildComment(UUID newsId, UUID commentId, String text, LocalDateTime time,
                                       String username) {
        return Comment.builder()
                .newsId(newsId)
                .id(commentId)
                .text(text)
                .time(time)
                .username(username)
                .build();
    }

    public static CreateCommentCommand buildCreateCommentCommand(UUID newsId,
                                                                 String text,
                                                                 String username) {
        return CreateCommentCommand.builder()
                .newsId(newsId)
                .text(text)
                .username(username).build();
    }

    public static List<Comment> buildComments(UUID newsId,
                                              Optional<String> username, int count) {
        List<Comment> comments = new ArrayList<>();
        int i = 0;
        while (i < count) {
            comments.add(buildComment(newsId, UUID.randomUUID(), "", LocalDateTime.now(),
                    username.orElse("Default user")));
            i++;
        }
        return comments;
    }
}
