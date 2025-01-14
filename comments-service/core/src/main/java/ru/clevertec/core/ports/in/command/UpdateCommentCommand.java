package ru.clevertec.core.ports.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommentCommand {
    private String text;
}