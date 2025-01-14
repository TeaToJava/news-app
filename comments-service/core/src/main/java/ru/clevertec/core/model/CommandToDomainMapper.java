package ru.clevertec.core.model;

import org.mapstruct.Mapper;
import ru.clevertec.core.ports.in.command.CreateCommentCommand;
import ru.clevertec.core.ports.in.command.UpdateCommentCommand;


@Mapper(componentModel = "spring")
public interface CommandToDomainMapper {
    Comment createCommentsCommandToDomain(CreateCommentCommand createCommentsCommand);

    Comment updateCommentsCommandToDomain(UpdateCommentCommand updateCommentsCommand);
}
