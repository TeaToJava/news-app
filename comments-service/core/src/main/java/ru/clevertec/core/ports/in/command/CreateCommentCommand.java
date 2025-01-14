package ru.clevertec.core.ports.in.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateCommentCommand {
    private String text;
    private String username;
    private UUID newsId;
}
