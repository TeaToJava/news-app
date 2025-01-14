package ru.clevertec.core.ports.in.command;

import java.util.UUID;

public record ReadNewsCommentCommand( UUID newsId, UUID commentId) {

}
